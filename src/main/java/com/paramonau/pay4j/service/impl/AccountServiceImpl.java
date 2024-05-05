package com.paramonau.pay4j.service.impl;

import com.paramonau.pay4j.dao.AccountDAO;
import com.paramonau.pay4j.dao.provider.DAOProvider;
import com.paramonau.pay4j.dto.account.AccountInfo;
import com.paramonau.pay4j.entity.Account;
import com.paramonau.pay4j.exception.DAOException;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.AccountService;

import java.math.BigDecimal;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private static final DAOProvider daoProvider = DAOProvider.getInstance();
    private static final AccountDAO accountDAO = daoProvider.getAccountDAO();

    private static final String MESSAGE_CREATE_ACCOUNT_PROBLEM = "Can't handle create request at AccountService";
    private static final String MESSAGE_FIND_ACCOUNT_INFO_BY_ID_PROBLEM = "Can't handle findAccountInfoById request at AccountService";
    private static final String MESSAGE_FIND_ALL_ACCOUNTS_INFO_BY_ID_PROBLEM = "Can't handle findAllAccountsInfoById request at AccountService";
    private static final String MESSAGE_FIND_ALL_ACTIVE_ACCOUNTS_INFO_BY_ID_PROBLEM = "Can't handle findAllActiveAccountsInfoById request at AccountService";
    private static final String MESSAGE_FIND_ALL_ACCOUNTS_BY_USER_ID_PROBLEM = "Can't handle findAllByUserId request at AccountService";
    private static final String MESSAGE_FIND_ALL_ACTIVE_ACCOUNTS_BY_USER_ID_PROBLEM = "Can't handle findAllActiveAccountsByUserId request at AccountService";
    private static final String MESSAGE_TOP_UP_ACCOUNT_PROBLEM = "Can't handle topUp request at AccountService";
    private static final String MESSAGE_FIND_ACCOUNT_BY_ID_PROBLEM = "Can't handle findById request at AccountService";
    private static final String MESSAGE_UPDATE_STATUS_BY_ACCOUNT_ID_PROBLEM = "Can't handle updateStatus request at AccountService";

    @Override
    public void create(Integer userId) throws ServiceException {
        try {
            accountDAO.create(userId);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_CREATE_ACCOUNT_PROBLEM, e);
        }
    }

    @Override
    public AccountInfo findAccountInfoById(Integer id) throws ServiceException {
        try {
            return accountDAO.findAccountInfoById(id);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ACCOUNT_INFO_BY_ID_PROBLEM, e);
        }
    }

    @Override
    public List<AccountInfo> findAllAccountsInfoById(Integer id) throws ServiceException {
        try {
            return accountDAO.findAllAccountsInfoLikeId(id);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_ACCOUNTS_INFO_BY_ID_PROBLEM, e);
        }
    }

    @Override
    public List<AccountInfo> findAllActiveAccountsInfoById(Integer id) throws ServiceException {
        try {
            return accountDAO.findAllActiveAccountsInfoLikeId(id);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_ACTIVE_ACCOUNTS_INFO_BY_ID_PROBLEM, e);
        }
    }

    @Override
    public List<Account> findAllByUserId(Integer userId) throws ServiceException {
        try {
            return accountDAO.findAllByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_ACCOUNTS_BY_USER_ID_PROBLEM, e);
        }
    }

    @Override
    public List<Account> findAllActiveAccountsByUserId(Integer userId) throws ServiceException {
        try {
            return accountDAO.findAllActiveAccountsByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_ACTIVE_ACCOUNTS_BY_USER_ID_PROBLEM, e);
        }
    }

    @Override
    public void topUp(Integer accountId, BigDecimal amount) throws ServiceException {
        try {
            accountDAO.topUp(accountId, amount);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_TOP_UP_ACCOUNT_PROBLEM, e);
        }
    }

    @Override
    public Account findById(Integer id) throws ServiceException {
        try {
            return accountDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ACCOUNT_BY_ID_PROBLEM, e);
        }
    }

    @Override
    public void updateStatus(Integer id, Integer status) throws ServiceException {
        try {
            accountDAO.updateStatus(id, status);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_UPDATE_STATUS_BY_ACCOUNT_ID_PROBLEM, e);
        }
    }
}
