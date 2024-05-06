package com.paramonau.pay4j.service.impl;

import com.paramonau.pay4j.dao.CardDAO;
import com.paramonau.pay4j.dao.provider.DAOProvider;
import com.paramonau.pay4j.entity.Card;
import com.paramonau.pay4j.exception.DAOException;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.CardService;
import com.paramonau.pay4j.validator.CardValidator;

import java.util.List;

public class CardServiceImpl implements CardService {

    private static final DAOProvider daoProvider = DAOProvider.getInstance();
    private static final CardDAO cardDAO = daoProvider.getCardDAO();

    private static final String MESSAGE_FIND_ALL_CARDS_BY_ACCOUNT_ID_PROBLEM = "Can't handle findAllByAccountId request at CardService";
    private static final String MESSAGE_FIND_ALL_CARDS_BY_USER_ID_PROBLEM = "Can't handle findAllByUserId request at CardService";
    private static final String MESSAGE_FIND_CARD_BY_ID_PROBLEM = "Can't handle findById request at CardService";
    private static final String MESSAGE_CARD_VALIDATION_IN_CREATE_PROBLEM = "Card data didn't passed validation in create at CardService";
    private static final String MESSAGE_CREATE_CARD_PROBLEM = "Can't handle create request at CardService";
    private static final String MESSAGE_UPDATE_STATUS_BY_ID_PROBLEM = "Can't handle updateStatus request at CardService";


    @Override
    public List<Card> findAllByAccountId(Integer accountId) throws ServiceException {
        try {
            return cardDAO.findAllByAccountId(accountId);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_CARDS_BY_ACCOUNT_ID_PROBLEM, e);
        }
    }

    @Override
    public List<Card> findAllByUserId(Integer userId) throws ServiceException {
        try {
            return cardDAO.findAllByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_CARDS_BY_USER_ID_PROBLEM, e);
        }
    }

    @Override
    public Card findById(Integer id) throws ServiceException {
        try {
            return cardDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_CARD_BY_ID_PROBLEM, e);
        }
    }

    @Override
    public void create(Card card) throws ServiceException {
        if (!CardValidator.validate(card)) {
            throw new ServiceException(MESSAGE_CARD_VALIDATION_IN_CREATE_PROBLEM);
        }

        try {
            cardDAO.create(card);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_CREATE_CARD_PROBLEM, e);
        }
    }

    @Override
    public void updateStatus(Integer id, Integer status) throws ServiceException {
        try {
            cardDAO.updateStatus(id, status);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_UPDATE_STATUS_BY_ID_PROBLEM, e);
        }
    }
}
