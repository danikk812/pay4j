package com.paramonau.pay4j.dao;

import com.paramonau.pay4j.entity.Payment;
import com.paramonau.pay4j.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentDAO {

    List<Payment> findAllInboundPaymentsByAccountId(Integer accountId) throws DAOException;

    List<Payment> findAllOutboundPaymentsByAccountId(Integer accountId) throws DAOException;

    List<Payment> findAllInboundPaymentsByUserId(Integer userId) throws DAOException;

    List<Payment> findAllOutboundPaymentsByUserId(Integer userId) throws DAOException;

    void transferMoney(Integer accountFromId, Integer accountToId, BigDecimal amount, String comment) throws DAOException;

    Payment findById(Integer id) throws DAOException;
}
