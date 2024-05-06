package com.paramonau.pay4j.dao.impl;

import com.paramonau.pay4j.dao.CardDAO;
import com.paramonau.pay4j.datasource.connection.ConnectionPool;
import com.paramonau.pay4j.entity.Card;
import com.paramonau.pay4j.entity.Status;
import com.paramonau.pay4j.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardDAOImpl implements CardDAO {

    private static final CardDAOImpl instance = new CardDAOImpl();

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /** Query for database to create card */
    private static final String CREATE_CARD_SQL = "INSERT INTO card(account,number,owner_name,expiration_date,cvv,status) VALUES (?,?,?,?,?,?)";

    /** Query for database to update card status by card ID */
    private static final String UPDATE_STATUS_BY_ID_SQL = "UPDATE card SET status = ? WHERE id = ?";

    /** Query for database to find card data by card ID */
    private static final String FIND_CARD_BY_CARD_ID_SQL = "SELECT cards.id, account, number, owner_name, expiration_date, cvv, cards.status, cardstatus.name as status_name FROM card cards " +
            "JOIN card_status cardstatus ON cards.status = cardstatus.id " +
            "WHERE (cards.id = ?)";

    /** Query for database to find all cards that linked to an account ID */
    private static final String FIND_ALL_CARDS_BY_ACCOUNT_ID_SQL = "SELECT cards.id, account, number, owner_name, expiration_date, cvv, cards.status, cardstatus.name as status_name FROM card cards " +
            "JOIN card_status cardstatus ON cards.status = cardstatus.id " +
            "WHERE (account = ? AND cards.status = ?)";

    /** Query for database to find all cards that linked to accounts owned by user ID */
    private static final String FIND_ALL_CARDS_BY_USER_ID_SQL = "SELECT cards.id, account, number, owner_name, expiration_date, cvv, cards.status, cardstatus.name as status_name FROM card cards " +
            "JOIN account accounts ON cards.account = accounts.id " +
            "JOIN card_status cardstatus ON cards.status = cardstatus.id " +
            "WHERE (accounts.user = ? AND cards.status = ?)";

    /** Message, that is put in Exception if there is find card by ID problem */
    private static final String MESSAGE_FIND_CARD_BY_ID_PROBLEM = "Cant handle CardDAO.findById request";

    /** Message, that is put in Exception if there is find all cards that linked to an account ID problem */
    private static final String MESSAGE_FIND_ALL_CARDS_BY_ACCOUNT_ID_PROBLEM = "Cant handle CardDAO.findAllByAccountId request";

    /** Message, that is put in Exception if there is find all cards that linked to accounts owned by user ID problem */
    private static final String MESSAGE_FIND_ALL_CARDS_BY_USER_ID_PROBLEM = "Cant handle CardDAO.findAllByUserId request";

    /** Message, that is put in Exception if there is create card problem */
    private static final String MESSAGE_CREATE_CARD_PROBLEM = "Can't handle CardDAO.create request";

    /** Message, that is put in Exception if there is set status by ID problem */
    private static final String MESSAGE_UPDATE_STATUS_BY_ID_PROBLEM = "Can't handle CardDAO.updateStatus request";

    private CardDAOImpl() {
    }

    public static CardDAOImpl getInstance() {
        return instance;
    }


    @Override
    public List<Card> findAllByAccountId(Integer accountId) throws DAOException {
        return findAllByProvidedId(accountId, FIND_ALL_CARDS_BY_ACCOUNT_ID_SQL, MESSAGE_FIND_ALL_CARDS_BY_ACCOUNT_ID_PROBLEM);
    }

    @Override
    public List<Card> findAllByUserId(Integer userId) throws DAOException {
        return findAllByProvidedId(userId, FIND_ALL_CARDS_BY_USER_ID_SQL, MESSAGE_FIND_ALL_CARDS_BY_USER_ID_PROBLEM);
    }

    @Override
    public Card findById(Integer id) throws DAOException {
        Card card = null;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_CARD_BY_CARD_ID_SQL)) {
            ps.setInt(FindCardByIdIndex.ID, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                card = extractCard(rs);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_CARD_BY_ID_PROBLEM, e);
        }

        return card;
    }

    private List<Card> findAllByProvidedId(Integer id, String sql, String exception) throws DAOException {
        final int STATUS_ACTIVE = 1;
        List<Card> cards = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(FindCardByIdIndex.ID, id);
            ps.setInt(FindCardByIdIndex.STATUS, STATUS_ACTIVE);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Card card = extractCard(rs);
                cards.add(card);
            }

        } catch (SQLException e) {
            throw new DAOException(exception, e);
        }

        return cards;
    }

    private Card extractCard(ResultSet rs) throws SQLException {
        Card card = new Card();
        Status status = new Status();
        status.setId(rs.getInt(ColumnName.STATUS_ID));
        status.setName(rs.getString(ColumnName.STATUS_NAME));

        card.setId(rs.getInt(ColumnName.ID));
        card.setAccount(rs.getInt(ColumnName.ACCOUNT));
        card.setNumber(rs.getString(ColumnName.NUMBER));
        card.setOwnerName(rs.getString(ColumnName.OWNER_NAME));
        card.setExpirationDate(rs.getDate(ColumnName.EXP_DATE).toLocalDate());
        card.setCvv(rs.getInt(ColumnName.CVV));
        card.setStatus(status);

        return card;
    }

    @Override
    public void create(Card card) throws DAOException {
        final int STATUS_ACTIVE = 1;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_CARD_SQL)) {

            ps.setInt(CreateCardIndex.ACCOUNT, card.getAccount());
            ps.setString(CreateCardIndex.NUMBER, card.getNumber());
            ps.setString(CreateCardIndex.OWNER_NAME, card.getOwnerName());
            ps.setDate(CreateCardIndex.EXP_DATE, Date.valueOf(card.getExpirationDate()));
            ps.setInt(CreateCardIndex.CVV, card.getCvv());
            ps.setInt(CreateCardIndex.STATUS, STATUS_ACTIVE);

            ps.execute();

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_CREATE_CARD_PROBLEM, e);
        }
    }

    @Override
    public void updateStatus(Integer id, Integer status) throws DAOException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_STATUS_BY_ID_SQL)) {
            
            ps.setInt(UpdateStatusByIdIndex.STATUS, status);
            ps.setInt(UpdateStatusByIdIndex.ID, id);
            
            ps.execute();
            
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_UPDATE_STATUS_BY_ID_PROBLEM, e);
        }
    }

    private static class ColumnName {
        private static final String ID = "id";
        private static final String ACCOUNT = "account";
        private static final String NUMBER = "number";
        private static final String OWNER_NAME = "owner_name";
        private static final String EXP_DATE = "expiration_date";
        private static final String CVV = "cvv";
        private static final String STATUS_ID = "status";
        private static final String STATUS_NAME = "status_name";
    }

    private static class CreateCardIndex {
        public static final int ACCOUNT = 1;
        private static final int NUMBER = 2;
        public static final int OWNER_NAME = 3;
        public static final int EXP_DATE = 4;
        public static final int CVV = 5;
        public static final int STATUS = 6;
    }

    private static class FindCardByIdIndex {
        private static final int ID = 1;
        private static final int STATUS = 2;
    }

    private static class UpdateStatusByIdIndex {
        private static final int STATUS = 1;
        private static final int ID = 2;
    }
}
