package com.paramonau.pay4j.dao;

import com.paramonau.pay4j.dto.account.AccountInfo;
import com.paramonau.pay4j.entity.Account;
import com.paramonau.pay4j.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDAO {

    Account findById(Integer id) throws DAOException;

    List<Account> findAllByUserId(Integer userId) throws DAOException;

    List<Account> findAllActiveAccountsByUserId(Integer userId) throws DAOException;

    AccountInfo findAccountInfoById(Integer id) throws DAOException;

    List<AccountInfo> findAllAccountsInfoLikeId(Integer id) throws DAOException;

    List<AccountInfo> findAllActiveAccountsInfoLikeId(Integer id) throws DAOException;

    void create(Integer userId) throws DAOException;

    void topUp(Integer id, BigDecimal amount) throws DAOException;

    void updateStatus(Integer id, Integer status) throws DAOException;

}
