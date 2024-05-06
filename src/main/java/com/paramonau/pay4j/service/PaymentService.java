package com.paramonau.pay4j.service;

import com.paramonau.pay4j.entity.Payment;
import com.paramonau.pay4j.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    List<Payment> findAllInboundPaymentsByAccountId(Integer accountId) throws ServiceException;

    List<Payment> findAllOutboundPaymentsByAccountId(Integer accountId) throws ServiceException;

    List<Payment> findAllInboundPaymentsByUserId(Integer userId) throws ServiceException;

    List<Payment> findAllOutboundPaymentsByUserId(Integer userId) throws ServiceException;

    void transferMoney(Integer accountFromId, Integer accountToId, BigDecimal amount, String comment) throws ServiceException;

}
