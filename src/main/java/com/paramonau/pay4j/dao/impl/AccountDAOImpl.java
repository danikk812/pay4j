package com.paramonau.pay4j.dao.impl;

import com.paramonau.pay4j.dao.AccountDAO;
import com.paramonau.pay4j.datasource.connection.ConnectionPool;
import com.paramonau.pay4j.dto.account.AccountInfo;
import com.paramonau.pay4j.entity.Account;
import com.paramonau.pay4j.entity.Status;
import com.paramonau.pay4j.exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    private static final AccountDAOImpl instance = new AccountDAOImpl();

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /** Query for database to set organization status by account ID */
    private static final String UPDATE_ORG_STATUS_BY_ACCOUNT_ID_SQL = "UPDATE organization SET status = ? WHERE account = ?";

    /** Query for database to create an account */
    private static final String CREATE_ACCOUNT_SQL = "INSERT INTO account(\"user\",status,balance,creation_date) values (?,?,?,?)";

    /** Query for database to update account status by account ID */
    private static final String UPDATE_STATUS_BY_ID_SQL = "UPDATE account SET status = ? WHERE id = ?";

    /** Query for database to update account balance by account ID */
    private static final String UPDATE_BALANCE_BY_ID_SQL = "UPDATE account SET balance = ? WHERE (id = ? AND status = ?)";

    /** Query for database to find account balance by account ID */
    private static final String FIND_BALANCE_BY_ID_SQL = "SELECT balance FROM account WHERE (id = ? AND status = ?)";

    /** Query for database to find account data by account ID */
    private static final String FIND_ACCOUNT_BY_ID_SQL = "SELECT acc.id,user,acc.status,statuses.name as status_name,balance,creation_date FROM account acc " +
            "JOIN account_status statuses ON acc.status = statuses.id " +
            "WHERE (acc.id = ?)";

    /** Query for database to find all accounts by user ID */
    private static final String FIND_ALL_ACCOUNTS_BY_USER_ID_SQL = "SELECT acc.id,user,acc.status,statuses.name as status_name,balance,creation_date FROM account acc " +
            "JOIN account_status statuses ON acc.status = statuses.id " +
            "WHERE (user = ? AND acc.status != ?) ORDER BY creation_date DESC";

    /** Query for database to find all active accounts by user ID  */
    private static final String FIND_ALL_ACTIVE_ACCOUNTS_BY_USER_ID_SQL = "SELECT acc.id,user,acc.status,statuses.name as status_name,balance,creation_date FROM account acc " +
            "JOIN account_status statuses ON acc.status = statuses.id " +
            "WHERE (user = ? AND acc.status = ?) ORDER BY creation_date DESC";

    /** Query for database to find public account info by account ID */
    private static final String FIND_ACCOUNT_INFO_BY_ID_SQL = "SELECT acc.id,acc.status,statuses.name as status_name,users.name as user_name,users.surname,users.patronymic FROM account acc " +
            "JOIN users ON acc.user = users.id " +
            "JOIN account_status statuses ON acc.status = statuses.id " +
            "WHERE (acc.id = ?)";

    /** Query for database to find all public accounts info like account ID */
    private static final String FIND_ALL_ACCOUNTS_INFO_LIKE_ID_SQL = "SELECT acc.id,acc.status,statuses.name as status_name,users.name as user_name,users.surname,users.patronymic FROM account acc " +
            "JOIN users ON acc.user = users.id " +
            "JOIN account_status statuses ON acc.status = statuses.id " +
            "WHERE (acc.id like ?)";

    /** Query for database to find all active public account info like account ID */
    private static final String FIND_ALL_ACTIVE_ACCOUNTS_INFO_LIKE_ID_SQL = "SELECT acc.id,acc.status,statuses.name as status_name,users.name as user_name,users.surname,users.patronymic FROM account acc " +
            "JOIN users ON acc.user = users.id " +
            "JOIN account_status statuses ON acc.status = statuses.id " +
            "WHERE (acc.id like ? and acc.status = ?)";

    /** Message, that is put in Exception if there is find all accounts by user ID problem */
    private static final String MESSAGE_FIND_ALL_ACCOUNTS_BY_USER_ID_PROBLEM = "Cant handle AccountDAO.findAllByUserId request";

    /** Message, that is put in Exception if there is find all active accounts by user ID problem */
    private static final String MESSAGE_FIND_ALL_ACTIVE_ACCOUNTS_BY_USER_ID_PROBLEM = "Cant handle AccountDAO.findAllActiveAccountsByUserId request";

    /** Message, that is put in Exception if there is top up account problem */
    private static final String MESSAGE_TOP_UP_ACCOUNT_PROBLEM = "Can't handle AccountDAO.topUp request";

    /** Message, that is put in Exception if there is top up account (find balance) problem */
    private static final String MESSAGE_TOP_UP_ACCOUNT_FIND_BALANCE_PROBLEM = "Can't handle AccountDAO.topUp.findBalance request";

    /** Message, that is put in Exception if there is top up account problem */
    private static final String MESSAGE_ROLLBACK_TOP_UP_ACCOUNT_PROBLEM = "Can't rollback at UserDAO.update request";

    /** Message, that is put in Exception if there is top up account (update balance) problem */
    private static final String MESSAGE_TOP_UP_ACCOUNT_UPDATE_BALANCE_PROBLEM = "Can't handle AccountDAO.topUp.updateBalance request";

    /** Message, that is put in Exception if there is create account problem */
    private static final String MESSAGE_CREATE_ACCOUNT_PROBLEM = "Can't handle AccountDAO.create request";

    /** Message, that is put in Exception if there is find account info by ID problem */
    private static final String MESSAGE_FIND_ACCOUNT_INFO_BY_ID_PROBLEM = "Cant handle AccountDAO.findAccountInfoById request";

    /** Message, that is put in Exception if there is find all accounts info like ID problem */
    private static final String MESSAGE_FIND_ALL_ACCOUNTS_INFO_LIKE_ID_PROBLEM = "Cant handle AccountDAO.findAllAccountsInfoLikeId request";

    /** Message, that is putted in Exception if there are get active account info list problem */
    private static final String MESSAGE_FIND_ALL_ACTIVE_ACCOUNTS_INFO_LIKE_ID_PROBLEM = "Cant handle AccountDAO.findAllActiveAccountsInfoLikeId request";

    /** Message, that is put in Exception if there is find account by ID problem */
    private static final String MESSAGE_FIND_ACCOUNT_BY_ID_PROBLEM = "Cant handle AccountDAO.findById request";

    /** Message, that is put in Exception if there is update status by ID problem */
    private static final String MESSAGE_ROLLBACK_UPDATE_STATUS_BY_ID_PROBLEM = "Can't rollback at UserDAO.updateStatusByID request";

    /** Message, that is put in Exception if there is update status by ID problem */
    private static final String MESSAGE_UPDATE_STATUS_BY_ID_PROBLEM = "Can't handle AccountDAO.updateStatus request";

    private AccountDAOImpl() {
    }

    public static AccountDAOImpl getInstance() {
        return instance;
    }


    @Override
    public Account findById(Integer id) throws DAOException {
        Account account = null;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ACCOUNT_BY_ID_SQL)) {
            ps.setInt(FindAccountByIdIndex.ID, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account = extractAccount(rs);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ACCOUNT_BY_ID_PROBLEM, e);
        }

        return account;
    }

    private Account extractAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        Status status = new Status();

        status.setId(rs.getInt(ColumnName.STATUS_ID));
        status.setName(rs.getString(ColumnName.STATUS_NAME));

        account.setId(rs.getInt(ColumnName.ID));
        account.setUser(rs.getInt(ColumnName.USER));
        account.setStatus(status);
        account.setBalance(rs.getBigDecimal(ColumnName.BALANCE));
        account.setCreationDate(rs.getDate(ColumnName.CREATION_DATE).toLocalDate());

        return account;
    }

    @Override
    public List<Account> findAllByUserId(Integer userId) throws DAOException {
        final int STATUS_DELETED = 3;
        final List<Account> accounts = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_ACCOUNTS_BY_USER_ID_SQL)) {
            ps.setInt(FindAccountByIdIndex.ID, userId);
            ps.setInt(FindAccountByIdIndex.STATUS, STATUS_DELETED);
            ResultSet rs = ps.executeQuery();

            while (!rs.next()) {
                Account account = extractAccount(rs);
                accounts.add(account);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ALL_ACCOUNTS_BY_USER_ID_PROBLEM, e);
        }

        return accounts;
    }

    @Override
    public List<Account> findAllActiveAccountsByUserId(Integer userId) throws DAOException {
        final int STATUS_ACTIVE = 1;
        final List<Account> accounts = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_ACTIVE_ACCOUNTS_BY_USER_ID_SQL)) {
            ps.setInt(FindAccountByIdIndex.ID, userId);
            ps.setInt(FindAccountByIdIndex.STATUS, STATUS_ACTIVE);
            ResultSet rs = ps.executeQuery();

            while (!rs.next()) {
                Account account = extractAccount(rs);
                accounts.add(account);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ALL_ACTIVE_ACCOUNTS_BY_USER_ID_PROBLEM, e);
        }

        return accounts;
    }

    @Override
    public AccountInfo findAccountInfoById(Integer id) throws DAOException {
        AccountInfo accountInfo = null;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ACCOUNT_INFO_BY_ID_SQL)) {
            ps.setInt(FindAccountByIdIndex.ID, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                accountInfo = extractAccountInfo(rs);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ACCOUNT_INFO_BY_ID_PROBLEM, e);
        }

        return accountInfo;
    }

    private AccountInfo extractAccountInfo(ResultSet rs) throws SQLException {
        AccountInfo accountInfo = new AccountInfo();

        Status status = new Status();
        status.setId(rs.getInt(ColumnName.STATUS_ID));
        status.setName(rs.getString(ColumnName.STATUS_NAME));

        accountInfo.setId(rs.getInt(ColumnName.ID));
        accountInfo.setUserName(rs.getString(ColumnName.USER_NAME));
        accountInfo.setUserSurname(rs.getString(ColumnName.USER_SURNAME));
        accountInfo.setUserPatronymic(rs.getString(ColumnName.USER_PATRONYMIC));
        accountInfo.setStatus(status);

        return accountInfo;
    }

    @Override
    public List<AccountInfo> findAllAccountsInfoLikeId(Integer id) throws DAOException {
        final String ANY_SYMBOLS_WILDCARD = "%";
        final List<AccountInfo> accountsInfo = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_ACCOUNTS_INFO_LIKE_ID_SQL)) {
            ps.setString(FindAccountByIdIndex.ID, id + ANY_SYMBOLS_WILDCARD);
            ResultSet rs = ps.executeQuery();

            while (!rs.next()) {
                AccountInfo accountInfo = extractAccountInfo(rs);
                accountsInfo.add(accountInfo);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ALL_ACCOUNTS_INFO_LIKE_ID_PROBLEM, e);
        }

        return accountsInfo;
    }

    @Override
    public List<AccountInfo> findAllActiveAccountsInfoLikeId(Integer id) throws DAOException {
        final int STATUS_ACTIVE = 1;
        final String ANY_SYMBOLS_WILDCARD = "%";
        final List<AccountInfo> accountsInfo = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_ACTIVE_ACCOUNTS_INFO_LIKE_ID_SQL)) {
            ps.setString(FindAccountByIdIndex.ID, id + ANY_SYMBOLS_WILDCARD);
            ps.setInt(FindAccountByIdIndex.STATUS, STATUS_ACTIVE);
            ResultSet rs = ps.executeQuery();

            while (!rs.next()) {
                AccountInfo accountInfo = extractAccountInfo(rs);
                accountsInfo.add(accountInfo);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ALL_ACTIVE_ACCOUNTS_INFO_LIKE_ID_PROBLEM, e);
        }

        return accountsInfo;
    }

    @Override
    public void create(Integer userId) throws DAOException {
        final int STATUS_ACTIVE = 1;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_ACCOUNT_SQL)) {

            ps.setInt(CreateIndex.USER, userId);
            ps.setInt(CreateIndex.STATUS, STATUS_ACTIVE);
            ps.setBigDecimal(CreateIndex.BALANCE, new BigDecimal(0));
            ps.setDate(CreateIndex.CREATION_DATE, Date.valueOf(LocalDate.now()));
            ps.execute();

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_CREATE_ACCOUNT_PROBLEM, e);
        }
    }

    @Override
    public void topUp(Integer id, BigDecimal amount) throws DAOException {
        final int STATUS_ACTIVE = 1;

        try (Connection connection = connectionPool.getConnection()) {
            boolean initialAutoCommit= connection.getAutoCommit();
            connection.setAutoCommit(false);

            try (PreparedStatement psFindBalance = connection.prepareStatement(FIND_BALANCE_BY_ID_SQL);
                 PreparedStatement psUpdateBalance = connection.prepareStatement(UPDATE_BALANCE_BY_ID_SQL)) {
                psFindBalance.setInt(FindBalanceIndex.ID, id);
                psFindBalance.setInt(FindBalanceIndex.STATUS, STATUS_ACTIVE);
                ResultSet rs = psFindBalance.executeQuery();

                if (!rs.next()) {
                    throw new DAOException(MESSAGE_TOP_UP_ACCOUNT_FIND_BALANCE_PROBLEM);
                }

                BigDecimal balance = rs.getBigDecimal(ColumnName.BALANCE);

                psUpdateBalance.setInt(UpdateBalanceIndex.ID, id);
                psUpdateBalance.setBigDecimal(UpdateBalanceIndex.BALANCE, balance.add(amount));
                psUpdateBalance.setInt(UpdateBalanceIndex.STATUS, STATUS_ACTIVE);
                psUpdateBalance.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DAOException(MESSAGE_ROLLBACK_TOP_UP_ACCOUNT_PROBLEM, ex);
                }
                throw new DAOException(MESSAGE_TOP_UP_ACCOUNT_UPDATE_BALANCE_PROBLEM, e);
            }
            finally {
                connection.setAutoCommit(initialAutoCommit);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_TOP_UP_ACCOUNT_PROBLEM, e);
        }
    }

    @Override
    public void updateStatus(Integer id, Integer status) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            boolean initialAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            try (PreparedStatement psUpdateAccountStatus = connection.prepareStatement(UPDATE_STATUS_BY_ID_SQL);
                 PreparedStatement psUpdateOrgStatus = connection.prepareStatement(UPDATE_ORG_STATUS_BY_ACCOUNT_ID_SQL)) {
                psUpdateAccountStatus.setInt(UpdateStatusByIdIndex.STATUS, status);
                psUpdateAccountStatus.setInt(UpdateStatusByIdIndex.ID, id);
                psUpdateAccountStatus.executeUpdate();

                psUpdateOrgStatus.setInt(UpdateStatusByIdIndex.STATUS, status);
                psUpdateOrgStatus.setInt(UpdateStatusByIdIndex.ID, id);
                psUpdateOrgStatus.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DAOException(MESSAGE_ROLLBACK_UPDATE_STATUS_BY_ID_PROBLEM, ex);
                }
                throw new DAOException(MESSAGE_UPDATE_STATUS_BY_ID_PROBLEM, e);
            }
            finally {
                connection.setAutoCommit(initialAutoCommit);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_UPDATE_STATUS_BY_ID_PROBLEM, e);
        }
    }

    private static class ColumnName {
        private static final String USER_NAME = "user_name";
        private static final String USER_SURNAME = "surname";
        private static final String USER_PATRONYMIC = "patronymic";
        private static final String ID = "id";
        private static final String USER = "user";
        private static final String STATUS_ID = "status";
        private static final String STATUS_NAME = "status_name";
        private static final String BALANCE = "balance";
        private static final String CREATION_DATE = "creation_date";
    }

    private static class CreateIndex {
        private static final int USER = 1;
        private static final int STATUS = 2;
        private static final int BALANCE = 3;
        private static final int CREATION_DATE = 4;

    }
    private static class UpdateBalanceIndex {
        private static final int BALANCE = 1;
        private static final int ID = 2;
        private static final int STATUS = 3;

    }
    private static class FindBalanceIndex {
        private static final int ID = 1;
        private static final int STATUS = 2;

    }
    private static class FindAccountByIdIndex {
        private static final int ID = 1;
        private static final int STATUS = 2;

    }
    private static class UpdateStatusByIdIndex {
        private static final int STATUS = 1;
        private static final int ID = 2;

    }
}
