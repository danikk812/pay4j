package com.paramonau.pay4j.service.impl;

import com.paramonau.pay4j.dao.OrganizationDAO;
import com.paramonau.pay4j.dao.provider.DAOProvider;
import com.paramonau.pay4j.entity.Organization;
import com.paramonau.pay4j.exception.DAOException;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.OrganizationService;

import java.util.List;

public class OrganizationServiceImpl implements OrganizationService {

    private static final DAOProvider daoProvider = DAOProvider.getInstance();
    private static final OrganizationDAO organizationDAO = daoProvider.getOrganizationDAO();

    private static final String MESSAGE_FIND_ALL_ORGS_PROBLEM = "Can't handle findAll request at OrganizationService";
    private static final String MESSAGE_FIND_ALL_ORGS_BY_NAME_PROBLEM = "Can't handle findAllByName request at OrganizationService";
    private static final String MESSAGE_FIND_ALL_ACTIVE_ORGS_PROBLEM = "Can't handle findAllActiveOrgs request at OrganizationService";
    private static final String MESSAGE_FIND_ALL_ACTIVE_ORGS_BY_NAME_PROBLEM = "Can't handle findAllActiveOrgsByName request at OrganizationService";
    private static final String MESSAGE_FIND_ORG_BY_ID_PROBLEM = "Can't handle findById request at OrganizationService";
    private static final String MESSAGE_CREATE_ORG_PROBLEM = "Can't handle create request at OrganizationService";
    private static final String MESSAGE_UPDATE_ORG_PROBLEM = "Can't handle update request at OrganizationService";
    private static final String MESSAGE_UPDATE_STATUS_BY_ORG_ID_PROBLEM = "Can't handle updateStatus request at OrganizationService";

    @Override
    public List<Organization> findAll() throws ServiceException {
        try {
            return organizationDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_ORGS_PROBLEM, e);
        }
    }

    @Override
    public List<Organization> findAllByName(String name) throws ServiceException {
        try {
            return organizationDAO.findAllLikeName(name);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_ORGS_BY_NAME_PROBLEM, e);
        }
    }

    @Override
    public List<Organization> findAllActiveOrgs() throws ServiceException {
        try {
            return organizationDAO.findAllActiveOrgs();
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_ACTIVE_ORGS_PROBLEM, e);
        }
    }

    @Override
    public List<Organization> findAllActiveOrgsByName(String name) throws ServiceException {
        try {
            return organizationDAO.findAllActiveOrgsLikeName(name);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_ACTIVE_ORGS_BY_NAME_PROBLEM, e);
        }
    }

    @Override
    public Organization findById(Integer id) throws ServiceException {
        try {
            return organizationDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ORG_BY_ID_PROBLEM, e);
        }
    }

    @Override
    public void create(String name, Integer accountId) throws ServiceException {
        try {
            organizationDAO.create(name, accountId);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_CREATE_ORG_PROBLEM, e);
        }
    }

    @Override
    public void update(Integer id, String name, Integer accountId) throws ServiceException {
        try {
            organizationDAO.update(id, name, accountId);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_UPDATE_ORG_PROBLEM, e);
        }
    }

    @Override
    public void updateStatus(Integer id, Integer status) throws ServiceException {
        try {
            organizationDAO.updateStatus(id, status);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_UPDATE_STATUS_BY_ORG_ID_PROBLEM, e);
        }
    }
}
