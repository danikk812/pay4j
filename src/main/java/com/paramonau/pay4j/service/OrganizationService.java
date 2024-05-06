package com.paramonau.pay4j.service;

import com.paramonau.pay4j.entity.Organization;
import com.paramonau.pay4j.exception.ServiceException;

import java.util.List;

public interface OrganizationService {

    List<Organization> findAll() throws ServiceException;

    List<Organization> findAllByName(String name) throws ServiceException;

    List<Organization> findAllActiveOrgs() throws ServiceException;

    List<Organization> findAllActiveOrgsByName(String name) throws ServiceException;

    Organization findById(Integer id) throws ServiceException;

    void create(String name, Integer accountId) throws ServiceException;

    void update(Integer id, String name, Integer accountId) throws ServiceException;

    void updateStatus(Integer id, Integer status) throws ServiceException;
}
