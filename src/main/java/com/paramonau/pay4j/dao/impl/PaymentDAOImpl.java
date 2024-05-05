package com.paramonau.pay4j.dao.impl;

import com.paramonau.pay4j.dao.PaymentDAO;
import com.paramonau.pay4j.datasource.connection.ConnectionPool;
import com.paramonau.pay4j.entity.Payment;
import com.paramonau.pay4j.exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class PaymentDAOImpl implements PaymentDAO {

    private static final PaymentDAOImpl instance = new PaymentDAOImpl();

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /** Query for database to update account balance by account ID */
    private static final String UPDATE_ACC_BALANCE_BY_ID_SQL = "UPDATE account SET balance = ? WHERE (id = ? AND status = ?)";

    /** Query for database to find account balance by account ID */
    private static final String FIND_ACC_BALANCE_SQL = "SELECT balance FROM account where (id = ? AND status = ?)"; //FOR UPDATE

    /** Query for database to create payment */
    private static final String CREATE_PAYMENT_SQL = "INSERT INTO payment(account_from,account_to,amount,datetime,comment) VALUES (?,?,?,?,?)";

    /** Query for database to find payment data by payment ID */
    private static final String FIND_PAYMENT_BY_ID_SQL = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM payment payments WHERE payments.id = ?";

    /** Query for database to find all outbound payments from account ID */
    private static final String FIND_ALL_OUTBOUND_PAYMENTS_BY_ACCOUNT_ID = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM payment payments WHERE account_from = ?";

    /** Query for database to find all inbound payments to account ID */
    private static final String FIND_ALL_INBOUND_PAYMENTS_BY_ACCOUNT_ID = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM payment payments WHERE account_to = ?";

    /** Query for database to find all outbound payments by accounts that owned by user ID */
    private static final String FIND_ALL_OUTBOUND_PAYMENTS_BY_USER_ID = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM payment payments " +
            "JOIN account accounts ON payments.account_from = accounts.id " +
            "WHERE accounts.user = ?";

    /** Query for database to find all inbound payments by accounts that owned by user ID */
    private static final String FIND_ALL_INBOUND_PAYMENTS_BY_USER_ID = "SELECT payments.id, account_from, account_to, amount, datetime, comment FROM payment payments " +
            "JOIN account accounts ON payments.account_to = accounts.id " +
            "WHERE accounts.user = ?";


    /** Message, that is put in Exception if there is find all outbound payments from account ID */
    private static final String MESSAGE_FIND_ALL_OUTBOUND_PAYMENTS_BY_ACCOUNT_ID_PROBLEM = "Cant handle PaymentDAO.findAllOutboundPaymentsByAccountId request";

    /** Message, that is put in Exception if there is find all inbound payments to account ID */
    private static final String MESSAGE_FIND_ALL_INBOUND_PAYMENTS_BY_ACCOUNT_ID_PROBLEM = "Cant handle PaymentDAO.findAllInboundPaymentsByAccountId request";

    /** Message, that is put in Exception if there is find all outbound payments by accounts that owned by user ID */
    private static final String MESSAGE_FIND_ALL_OUTBOUND_PAYMENTS_BY_USER_ID_PROBLEM = "Cant handle PaymentDAO.findAllOutboundPaymentsByUserId request";

    /** Message, that is put in Exception if there is find all inbound payments by accounts that owned by user ID */
    private static final String MESSAGE_FIND_ALL_INBOUND_PAYMENTS_BY_USER_ID_PROBLEM = "Cant handle PaymentDAO.findAllInboundPaymentsByUserId request";

    /** Message, that is put in Exception if there is find payment by ID problem */
    private static final String MESSAGE_FIND_PAYMENT_BY_ID_PROBLEM = "Can't handle PaymentDAO.findById request";

    /** Message, that is put in Exception if there is find accountFrom balance problem */
    private static final String MESSAGE_FIND_ACCOUNT_FROM_BALANCE_PROBLEM = "Can't find accountFrom balance";

    /** Message, that is put in Exception if there is not enough balance problem */
    private static final String MESSAGE_NOT_ENOUGH_BALANCE_PROBLEM = "Not enough balance to make a transfer";

    /** Message, that is put in Exception if there is find accountTo balance problem */
    private static final String MESSAGE_FIND_ACCOUNT_TO_BALANCE_PROBLEM = "Can't find accountTo balance";

    /** Message, that is put in Exception if there is transfer money rollback problem */
    private static final String MESSAGE_TRANSFER_MONEY_ROLLBACK_PROBLEM = "Can't handle PaymentDAO.transferMoney.rollback request";

    /** Message, that is put in Exception if there is transfer money problem */
    private static final String MESSAGE_TRANSFER_MONEY_PROBLEM = "Can't handle PaymentDAO.transferMoney request";

    private PaymentDAOImpl() {
    }

    public static PaymentDAOImpl getInstance() {
        return instance;
    }

    @Override
    public List<Payment> findAllInboundPaymentsByAccountId(Integer accountId) throws DAOException {
        return findAllByProvidedId(accountId, FIND_ALL_INBOUND_PAYMENTS_BY_ACCOUNT_ID, MESSAGE_FIND_ALL_INBOUND_PAYMENTS_BY_ACCOUNT_ID_PROBLEM);
    }

    @Override
    public List<Payment> findAllOutboundPaymentsByAccountId(Integer accountId) throws DAOException {
        return findAllByProvidedId(accountId, FIND_ALL_OUTBOUND_PAYMENTS_BY_ACCOUNT_ID, MESSAGE_FIND_ALL_OUTBOUND_PAYMENTS_BY_ACCOUNT_ID_PROBLEM);
    }

    @Override
    public List<Payment> findAllInboundPaymentsByUserId(Integer userId) throws DAOException {
        return findAllByProvidedId(userId, FIND_ALL_INBOUND_PAYMENTS_BY_USER_ID, MESSAGE_FIND_ALL_INBOUND_PAYMENTS_BY_USER_ID_PROBLEM);
    }

    @Override
    public List<Payment> findAllOutboundPaymentsByUserId(Integer userId) throws DAOException {
        return findAllByProvidedId(userId, FIND_ALL_OUTBOUND_PAYMENTS_BY_USER_ID, MESSAGE_FIND_ALL_OUTBOUND_PAYMENTS_BY_USER_ID_PROBLEM);
    }

    private List<Payment> findAllByProvidedId(Integer id, String sql, String exception) throws DAOException {
        List<Payment> payments = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(FindAllPaymentsByIdIndex.ID, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Payment payment = extractPayment(rs);
                payments.add(payment);
            }


        } catch (SQLException e) {
            throw new DAOException(exception, e);
        }

        return payments;
    }

    @Override
    public void transferMoney(Integer accountFromId, Integer accountToId, BigDecimal amount, String comment) throws DAOException {
        final int ACCOUNT_STATUS_ACTIVE = 1;
        BigDecimal balanceFrom;
        BigDecimal balanceTo;

        try (Connection connection = connectionPool.getConnection()) {
            boolean initialAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            try (PreparedStatement psFindBalanceFrom = connection.prepareStatement(FIND_ACC_BALANCE_SQL);
                 PreparedStatement psUpdateBalanceFrom = connection.prepareStatement(UPDATE_ACC_BALANCE_BY_ID_SQL);
                 PreparedStatement psFindBalanceTo = connection.prepareStatement(FIND_ACC_BALANCE_SQL);
                 PreparedStatement psUpdateBalanceTo = connection.prepareStatement(UPDATE_ACC_BALANCE_BY_ID_SQL);
                 PreparedStatement psCreatePayment = connection.prepareStatement(CREATE_PAYMENT_SQL)) {

                psFindBalanceFrom.setInt(FindAccBalanceIndex.ID, accountFromId);
                psFindBalanceFrom.setInt(FindAccBalanceIndex.STATUS, ACCOUNT_STATUS_ACTIVE);
                ResultSet rs = psFindBalanceFrom.executeQuery();
                if(!rs.next()) {
                    throw new DAOException(MESSAGE_FIND_ACCOUNT_FROM_BALANCE_PROBLEM);
                }
                balanceFrom = rs.getBigDecimal(AccColumnName.BALANCE);

                final boolean ENOUGH_MONEY = balanceFrom.compareTo(amount) > 0;
                if (!ENOUGH_MONEY) {
                    throw new DAOException(MESSAGE_NOT_ENOUGH_BALANCE_PROBLEM);
                }

                psUpdateBalanceFrom.setBigDecimal(UpdateAccBalanceIndex.BALANCE, balanceFrom.subtract(amount));
                psUpdateBalanceFrom.setInt(UpdateAccBalanceIndex.ID, accountFromId);
                psUpdateBalanceFrom.setInt(UpdateAccBalanceIndex.STATUS, ACCOUNT_STATUS_ACTIVE);
                psUpdateBalanceFrom.executeUpdate();

                psFindBalanceTo.setInt(FindAccBalanceIndex.ID, accountToId);
                psFindBalanceTo.setInt(FindAccBalanceIndex.STATUS, ACCOUNT_STATUS_ACTIVE);
                rs = psFindBalanceTo.executeQuery();
                if (!rs.next()) {
                    throw new DAOException(MESSAGE_FIND_ACCOUNT_TO_BALANCE_PROBLEM);
                }
                balanceTo = rs.getBigDecimal(AccColumnName.BALANCE);

                psUpdateBalanceTo.setBigDecimal(UpdateAccBalanceIndex.BALANCE, balanceTo.add(amount));
                psUpdateBalanceTo.setInt(UpdateAccBalanceIndex.ID, accountToId);
                psUpdateBalanceTo.setInt(UpdateAccBalanceIndex.STATUS, ACCOUNT_STATUS_ACTIVE);
                psUpdateBalanceTo.executeUpdate();

                psCreatePayment.setInt(CreatePaymentIndex.CARD_FROM, accountFromId);
                psCreatePayment.setInt(CreatePaymentIndex.ACCOUNT_TO, accountToId);
                psCreatePayment.setBigDecimal(CreatePaymentIndex.AMOUNT, amount);
                psCreatePayment.setTimestamp(CreatePaymentIndex.DATETIME, new Timestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
                psCreatePayment.setString(CreatePaymentIndex.COMMENT, comment);
                psCreatePayment.execute();

                connection.commit();

            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DAOException(MESSAGE_TRANSFER_MONEY_ROLLBACK_PROBLEM, ex);
                }
                throw new DAOException(MESSAGE_TRANSFER_MONEY_PROBLEM, e);
            }
            finally {
                connection.setAutoCommit(initialAutoCommit);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_TRANSFER_MONEY_PROBLEM, e);
        }

    }

    @Override
    public Payment findById(Integer id) throws DAOException {
        Payment payment = null;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_PAYMENT_BY_ID_SQL)) {
            ps.setInt(FindPaymentByIdIndex.ID, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                payment = extractPayment(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_PAYMENT_BY_ID_PROBLEM, e);
        }

        return payment;
    }

    private Payment extractPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getInt(ColumnName.ID));
        payment.setAccountFrom(rs.getInt(ColumnName.ACCOUNT_FROM));
        payment.setAccountTo(rs.getInt(ColumnName.ACCOUNT_TO));
        payment.setAmount(rs.getBigDecimal(ColumnName.AMOUNT));
        payment.setDateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(rs.getTimestamp(ColumnName.DATETIME).getTime()), ZoneOffset.UTC));
        payment.setComment(rs.getString(ColumnName.COMMENT));

        return payment;
    }

    private static class AccColumnName {
        private static final String BALANCE = "balance";
    }

    private static class ColumnName {
        private static final String ID = "id";
        private static final String ACCOUNT_FROM = "account_from";
        private static final String ACCOUNT_TO = "account_to";
        private static final String AMOUNT = "amount";
        private static final String DATETIME = "datetime";
        private static final String COMMENT = "comment";
    }

    private static class FindPaymentByIdIndex {
        private static final int ID = 1;
    }

    private static class FindAllPaymentsByIdIndex {
        private static final int ID = 1;
    }

    private static class CreatePaymentIndex {
        public static final int CARD_FROM = 1;
        private static final int ACCOUNT_TO = 2;
        public static final int AMOUNT = 3;
        public static final int DATETIME = 4;
        public static final int COMMENT = 5;
    }

    private static class UpdateAccBalanceIndex {
        private static final int BALANCE = 1;
        private static final int ID = 2;
        private static final int STATUS = 3;
    }

    private static class FindAccBalanceIndex {
        private static final int ID = 1;
        private static final int STATUS = 2;
    }
}
