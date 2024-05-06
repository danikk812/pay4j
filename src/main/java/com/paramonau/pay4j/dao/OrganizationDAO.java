package com.paramonau.pay4j.dao;

import com.paramonau.pay4j.entity.Organization;
import com.paramonau.pay4j.exception.DAOException;

import java.util.List;

public interface OrganizationDAO {

    void create(String name, Integer accountId) throws DAOException;

    void updateStatus(Integer id, Integer status) throws DAOException;

    void update(Integer id, String name, Integer accountId) throws DAOException;

    Organization findById(Integer id) throws DAOException;

    List<Organization> findAll() throws DAOException;

    List<Organization> findAllLikeName(String name) throws DAOException;

    List<Organization> findAllActiveOrgs() throws DAOException;

    List<Organization> findAllActiveOrgsLikeName(String name) throws DAOException;

    List<Organization> findAllByUserId(Integer userId) throws DAOException;

}
