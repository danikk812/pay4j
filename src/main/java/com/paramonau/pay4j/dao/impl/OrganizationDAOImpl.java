package com.paramonau.pay4j.dao.impl;

import com.paramonau.pay4j.dao.OrganizationDAO;
import com.paramonau.pay4j.datasource.connection.ConnectionPool;
import com.paramonau.pay4j.entity.Organization;
import com.paramonau.pay4j.entity.Status;
import com.paramonau.pay4j.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDAOImpl implements OrganizationDAO {

    private static final OrganizationDAOImpl instance = new OrganizationDAOImpl();

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Query for database to create organization
     */
    private static final String CREATE_ORGANIZATION_SQL = "INSERT INTO organization(name,account,status) VALUES (?,?,?)";

    /**
     * Query for database to update organization data by organization ID
     */
    private static final String UPDATE_ORGANIZATION_SQL = "UPDATE organization SET name = ?, account = ?, status = ? WHERE id = ?";

    /**
     * Query for database to update status of organization by organization ID
     */
    private static final String UPDATE_STATUS_BY_ID_SQL = "UPDATE organization SET status = ? where id = ?";

    /**
     * Query for database to find all organizations by account linked owned by user ID
     */
    private static final String FIND_ALL_ORGANIZATIONS_BY_USER_ID_SQL = "SELECT org.id,org.name,account,org.status,statuses.name as status_name FROM organization org " +
            "JOIN organization_status statuses ON org.status = statuses.id " +
            "JOIN account acc ON org.account = acc.id " +
            "WHERE acc.user = ?";

    /**
     * Query for database to find organization data by organization ID
     */
    private static final String FIND_ORGANIZATION_BY_ID = "SELECT org.id,org.name,account,org.status,statuses.name as status_name FROM organization org " +
            "JOIN organization_status statuses ON org.status = statuses.id " +
            "WHERE org.id = ? " +
            "ORDER BY org.name";

    /**
     * Query for database to find all organizations
     */
    private static final String FIND_ALL_ORGANIZATIONS = "SELECT org.id,org.name,account,org.status,statuses.name as status_name FROM organization org " +
            "JOIN organization_status statuses ON org.status = statuses.id " +
            "WHERE org.status != ? " +
            "ORDER BY org.name";

    /**
     * Query for database to find organization like name
     */
    private static final String FIND_ALL_ORGANIZATIONS_LIKE_NAME = "SELECT org.id,org.name,account,org.status,statuses.name as status_name FROM organization org " +
            "JOIN organization_status statuses ON org.status = statuses.id " +
            "WHERE (org.name like ? AND org.status != ?) " +
            "ORDER BY org.name";

    /**
     * Query for database to find all active organizations
     */
    private static final String FIND_ALL_ACTIVE_ORGANIZATIONS = "SELECT org.id,org.name,account,org.status,statuses.name as status_name FROM organization org " +
            "JOIN organization_status statuses ON org.status = statuses.id " +
            "WHERE org.status = ? " +
            "ORDER BY org.name";
    /**
     * Query for database to find all active organizations like name
     */
    private static final String FIND_ALL_ACTIVE_ORGANIZATIONS_LIKE_NAME = "SELECT org.id,org.name,account,org.status,statuses.name as status_name FROM organization org " +
            "JOIN organization_status statuses ON org.status = statuses.id " +
            "WHERE (org.name like ? AND org.status = ?) " +
            "ORDER BY org.name";

    /**
     * Message, that is put in Exception if there is find all orgs by user ID problem
     */
    private static final String MESSAGE_FIND_ALL_ORGS_BY_USER_ID_PROBLEM = "Cant handle OrgDAO.findAllByUserId request";

    /**
     * Message, that is put in Exception if there is find org by ID problem
     */
    private static final String MESSAGE_FIND_ORG_BY_ID_PROBLEM = "Cant handle OrgDAO.findById request";

    /**
     * Message, that is put in Exception if there is find all orgs like name problem
     */
    private static final String MESSAGE_FIND_ALL_ORGS_LIKE_NAME_PROBLEM = "Cant handle OrgDAO.findAllLikeName request";

    /**
     * Message, that is put in Exception if there is find all active orgs like name problem
     */
    private static final String MESSAGE_FIND_ALL_ACTIVE_ORGS_LIKE_NAME_PROBLEM = "Cant handle OrgDAO.findAllActiveOrgsLikeName request";

    /**
     * Message, that is put in Exception if there is find all orgs problem
     */
    private static final String MESSAGE_FIND_ALL_ORGS_PROBLEM = "Cant handle OrgDAO.findAll request";

    /**
     * Message, that is put in Exception if there is find all active orgs problem
     */
    private static final String MESSAGE_FIND_ALL_ACTIVE_ORGS_PROBLEM = "Cant handle OrgDAO.findAllActiveOrgs request";

    /**
     * Message, that is put in Exception if there is update organization rollback problem
     */
    private static final String MESSAGE_UPDATE_ORGANIZATION_ROLLBACK_PROBLEM = "Can't handle OrgDAO.update.rollback request";

    /**
     * Message, that is put in Exception if there is update organization problem
     */
    private static final String MESSAGE_UPDATE_ORGANIZATION_PROBLEM = "Can't handle OrgDAO.update request";

    /**
     * Message, that is put in Exception if there is crate organization problem
     */
    private static final String MESSAGE_CREATE_ORGANIZATION_PROBLEM = "Can't handle OrgDAO.create request";

    /**
     * Message, that is put in Exception if there is update status by ID problem
     */
    private static final String MESSAGE_UPDATE_STATUS_BY_ID_PROBLEM = "Can't handle OrgDAO.updateStatus request";

    private OrganizationDAOImpl() {
    }

    public static OrganizationDAOImpl getInstance() {
        return instance;
    }


    @Override
    public void create(String name, Integer accountId) throws DAOException {
        final int STATUS_ACTIVE = 1;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_ORGANIZATION_SQL)) {

            ps.setString(CreateOrgIndex.NAME, name);
            ps.setInt(CreateOrgIndex.ACCOUNT, accountId);
            ps.setInt(CreateOrgIndex.STATUS, STATUS_ACTIVE);
            ps.execute();

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_CREATE_ORGANIZATION_PROBLEM, e);
        }
    }

    @Override
    public void updateStatus(Integer id, Integer status) throws DAOException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_STATUS_BY_ID_SQL)) {

            ps.setInt(UpdateStatusByIdIndex.STATUS, status);
            ps.setInt(UpdateStatusByIdIndex.ID, id);
            ps.execute();

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_UPDATE_STATUS_BY_ID_PROBLEM, e);
        }
    }

    @Override
    public void update(Integer id, String name, Integer accountId) throws DAOException {
        final int STATUS_ACTIVE = 1;

        try (Connection connection = connectionPool.getConnection()) {
            boolean initialAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_ORGANIZATION_SQL)) {
                ps.setString(UpdateOrgIndex.NAME, name);
                ps.setInt(UpdateOrgIndex.ACCOUNT, accountId);
                ps.setInt(UpdateOrgIndex.STATUS, STATUS_ACTIVE);
                ps.setInt(UpdateOrgIndex.ID, id);
                ps.execute();

                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new DAOException(MESSAGE_UPDATE_ORGANIZATION_ROLLBACK_PROBLEM, ex);
                }
                throw new DAOException(MESSAGE_UPDATE_ORGANIZATION_PROBLEM, e);
            } finally {
                connection.setAutoCommit(initialAutoCommit);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_UPDATE_ORGANIZATION_PROBLEM, e);
        }
    }

    @Override
    public Organization findById(Integer id) throws DAOException {
        Organization organization = null;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ORGANIZATION_BY_ID)) {
            ps.setInt(FindOrgByIdIndex.ID, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                organization = extractOrganization(rs);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ORG_BY_ID_PROBLEM, e);
        }

        return organization;
    }

    private Organization extractOrganization(ResultSet rs) throws SQLException {
        Organization organization = new Organization();
        Status status = new Status();
        status.setId(rs.getInt(ColumnName.STATUS_ID));
        status.setName(rs.getString(ColumnName.STATUS_NAME));

        organization.setId(rs.getInt(ColumnName.ID));
        organization.setName(rs.getString(ColumnName.NAME));
        organization.setAccount(rs.getInt(ColumnName.ACCOUNT));
        organization.setStatus(status);

        return organization;
    }

    @Override
    public List<Organization> findAll() throws DAOException {
        final int STATUS_DELETED = 3;
        final List<Organization> orgs = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_ORGANIZATIONS)) {
            ps.setInt(FindAllOrgsIndex.STATUS, STATUS_DELETED);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = extractOrganization(rs);
                orgs.add(organization);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ALL_ORGS_PROBLEM, e);
        }

        return orgs;
    }

    @Override
    public List<Organization> findAllLikeName(String name) throws DAOException {
        final String ANY_SYMBOLS_WILDCARD = "%";
        final int STATUS_DELETED = 3;
        final List<Organization> orgs = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_ORGANIZATIONS_LIKE_NAME)) {
            ps.setString(FindAllOrgsLikeNameIndex.NAME, ANY_SYMBOLS_WILDCARD + name + ANY_SYMBOLS_WILDCARD);
            ps.setInt(FindAllOrgsLikeNameIndex.STATUS, STATUS_DELETED);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = extractOrganization(rs);
                orgs.add(organization);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ALL_ORGS_LIKE_NAME_PROBLEM, e);
        }

        return orgs;
    }

    @Override
    public List<Organization> findAllActiveOrgs() throws DAOException {
        final int STATUS_ACTIVE = 1;
        final List<Organization> orgs = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_ACTIVE_ORGANIZATIONS)) {
            ps.setInt(FindAllOrgsIndex.STATUS, STATUS_ACTIVE);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = extractOrganization(rs);
                orgs.add(organization);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ALL_ACTIVE_ORGS_PROBLEM, e);
        }

        return orgs;
    }

    @Override
    public List<Organization> findAllActiveOrgsLikeName(String name) throws DAOException {
        final String ANY_SYMBOLS_WILDCARD = "%";
        final int STATUS_ACTIVE = 1;
        final List<Organization> orgs = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_ACTIVE_ORGANIZATIONS_LIKE_NAME)) {
            ps.setString(FindAllOrgsLikeNameIndex.NAME, ANY_SYMBOLS_WILDCARD + name + ANY_SYMBOLS_WILDCARD);
            ps.setInt(FindAllOrgsLikeNameIndex.STATUS, STATUS_ACTIVE);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = extractOrganization(rs);
                orgs.add(organization);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ALL_ACTIVE_ORGS_LIKE_NAME_PROBLEM, e);
        }

        return orgs;
    }

    @Override
    public List<Organization> findAllByUserId(Integer userId) throws DAOException {
        final List<Organization> orgs = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_ORGANIZATIONS_BY_USER_ID_SQL)) {
            ps.setInt(FindAllOrgsByUserIdIndex.USER_ID, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = extractOrganization(rs);
                orgs.add(organization);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ALL_ORGS_BY_USER_ID_PROBLEM, e);
        }

        return orgs;
    }

    private static class ColumnName {
        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String ACCOUNT = "account";
        private static final String STATUS_ID = "status";
        private static final String STATUS_NAME = "status_name";
    }

    private static class FindAllOrgsLikeNameIndex {
        public static final int NAME = 1;
        public static final int STATUS = 2;
    }

    private static class FindOrgByIdIndex {
        public static final int ID = 1;
    }

    private static class FindAllOrgsByUserIdIndex {
        public static final int USER_ID = 1;
    }

    private static class FindAllOrgsIndex {
        public static final int STATUS = 1;
    }

    private static class CreateOrgIndex {
        private static final int NAME = 1;
        private static final int ACCOUNT = 2;
        private static final int STATUS = 3;
    }

    private static class UpdateOrgIndex {
        private static final int NAME = 1;
        private static final int ACCOUNT = 2;
        private static final int STATUS = 3;
        private static final int ID = 4;
    }

    private static class UpdateStatusByIdIndex {
        private static final int STATUS = 1;
        private static final int ID = 2;
    }
}
