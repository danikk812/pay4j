package com.paramonau.pay4j.dao.provider;

import com.paramonau.pay4j.dao.*;
import com.paramonau.pay4j.dao.impl.*;

public final class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();
    private static final UserDAO userDAO = UserDAOImpl.getInstance();
    private static final CardDAO cardDAO = CardDAOImpl.getInstance();
    private static final PaymentDAO paymentDAO = PaymentDAOImpl.getInstance();
    private static final OrganizationDAO organizationDAO = OrganizationDAOImpl.getInstance();
    private static final AccountDAO accountDAO = AccountDAOImpl.getInstance();

    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public CardDAO getCardDAO() {
        return cardDAO;
    }

    public PaymentDAO getPaymentDAO() {
        return paymentDAO;
    }

    public OrganizationDAO getOrganizationDAO() {
        return organizationDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }
}
