package com.paramonau.pay4j.service;

import com.paramonau.pay4j.dto.account.AccountInfo;
import com.paramonau.pay4j.entity.Account;
import com.paramonau.pay4j.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    void create(Integer userId) throws ServiceException;

    AccountInfo findAccountInfoById(Integer id) throws ServiceException;

    List<AccountInfo> findAllAccountsInfoById(Integer id) throws ServiceException;

    List<AccountInfo> findAllActiveAccountsInfoById(Integer id) throws ServiceException;

    List<Account> findAllByUserId(Integer userId) throws ServiceException;

    List<Account> findAllActiveAccountsByUserId(Integer userId) throws ServiceException;

    void topUp(Integer accountId, BigDecimal amount) throws ServiceException;

    Account findById(Integer id) throws ServiceException;

    void updateStatus(Integer id, Integer status) throws ServiceException;
}
