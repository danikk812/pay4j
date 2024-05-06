package com.paramonau.pay4j.dao;

import com.paramonau.pay4j.entity.Card;
import com.paramonau.pay4j.exception.DAOException;

import java.util.List;

public interface CardDAO {

    List<Card> findAllByAccountId(Integer accountId) throws DAOException;

    List<Card> findAllByUserId(Integer userId) throws DAOException;

    Card findById(Integer id) throws DAOException;

    void create(Card card) throws DAOException;

    void updateStatus(Integer id, Integer status) throws DAOException;
}
