package com.paramonau.pay4j.service.impl;

import com.paramonau.pay4j.dao.PaymentDAO;
import com.paramonau.pay4j.dao.provider.DAOProvider;
import com.paramonau.pay4j.entity.Payment;
import com.paramonau.pay4j.exception.DAOException;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.PaymentService;
import com.paramonau.pay4j.validator.PaymentValidator;

import java.math.BigDecimal;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private static final DAOProvider daoProvider = DAOProvider.getInstance();
    private static final PaymentDAO paymentDAO = daoProvider.getPaymentDAO();

    private static final String MESSAGE_FIND_ALL_INBOUND_PAYMENTS_BY_ACCOUNT_ID_PROBLEM = "Can't handle findAllInboundPaymentsByAccountId request at PaymentService";
    private static final String MESSAGE_FIND_ALL_OUTBOUND_PAYMENTS_BY_ACCOUNT_ID_PROBLEM = "Can't handle findAllOutboundPaymentsByAccountId request at PaymentService";
    private static final String MESSAGE_FIND_ALL_INBOUND_PAYMENTS_BY_USER_ID_PROBLEM = "Can't handle findAllInboundPaymentsByUserId request at PaymentService";
    private static final String MESSAGE_FIND_ALL_OUTBOUND_PAYMENTS_BY_USER_ID_PROBLEM = "Can't handle findAllOutboundPaymentsByUserId request at PaymentService";
    private static final String MESSAGE_TRANSFER_MONEY_PROBLEM = "Can't handle transferMoney request at PaymentService";
    private static final String MESSAGE_PAYMENT_VALIDATION_IN_TRANSFER_MONEY_PROBLEM = "Payment data didn't passed validation in transferMoney at CardService";



    @Override
    public List<Payment> findAllInboundPaymentsByAccountId(Integer accountId) throws ServiceException {
        try {
            return paymentDAO.findAllInboundPaymentsByAccountId(accountId);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_INBOUND_PAYMENTS_BY_ACCOUNT_ID_PROBLEM, e);
        }
    }

    @Override
    public List<Payment> findAllOutboundPaymentsByAccountId(Integer accountId) throws ServiceException {
        try {
            return paymentDAO.findAllOutboundPaymentsByAccountId(accountId);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_OUTBOUND_PAYMENTS_BY_ACCOUNT_ID_PROBLEM, e);
        }
    }

    @Override
    public List<Payment> findAllInboundPaymentsByUserId(Integer userId) throws ServiceException {
        try {
            return paymentDAO.findAllInboundPaymentsByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_INBOUND_PAYMENTS_BY_USER_ID_PROBLEM, e);
        }
    }

    @Override
    public List<Payment> findAllOutboundPaymentsByUserId(Integer userId) throws ServiceException {
        try {
            return paymentDAO.findAllOutboundPaymentsByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_OUTBOUND_PAYMENTS_BY_USER_ID_PROBLEM, e);
        }
    }

    @Override
    public void transferMoney(Integer accountFromId, Integer accountToId, BigDecimal amount, String comment) throws ServiceException {
        if (!PaymentValidator.validateAmount(amount)) {
            throw new ServiceException(MESSAGE_PAYMENT_VALIDATION_IN_TRANSFER_MONEY_PROBLEM);
        }

        try {
            paymentDAO.transferMoney(accountFromId, accountToId, amount, comment);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_TRANSFER_MONEY_PROBLEM, e);
        }
    }
}
