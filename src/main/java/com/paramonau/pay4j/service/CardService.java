package com.paramonau.pay4j.service;

import com.paramonau.pay4j.entity.Card;
import com.paramonau.pay4j.exception.ServiceException;

import java.util.List;

public interface CardService {

    List<Card> findAllByAccountId(Integer accountId) throws ServiceException;

    List<Card> findAllByUserId(Integer userId) throws ServiceException;

    Card findById(Integer id) throws ServiceException;

    void create(Card card) throws ServiceException;

    void updateStatus(Integer id, Integer status) throws ServiceException;
}
