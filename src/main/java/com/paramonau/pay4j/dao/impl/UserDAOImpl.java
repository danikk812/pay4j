package com.paramonau.pay4j.dao.impl;

import com.paramonau.pay4j.dao.ResultCode;
import com.paramonau.pay4j.dao.UserDAO;
import com.paramonau.pay4j.datasource.connection.ConnectionPool;
import com.paramonau.pay4j.entity.SignInData;
import com.paramonau.pay4j.entity.SignUpData;
import com.paramonau.pay4j.entity.Status;
import com.paramonau.pay4j.entity.User;
import com.paramonau.pay4j.exception.DAOException;
import com.paramonau.pay4j.util.StringEncryptor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final UserDAOImpl instance = new UserDAOImpl();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final StringEncryptor stringEncryptor = StringEncryptor.getInstance();

    /** Query for database to sign up new user */
    private static final String SIGNUP_SQL = "INSERT INTO users(login,password,status,name,surname,patronymic,birthdate,phone) VALUES (?,?,?,?,?,?,?,?)";

    /** Query for database to update user data by user ID */
    private static final String UPDATE_USER_BY_ID_SQL = "UPDATE users SET login = ?, name = ?, surname = ?, patronymic = ?, birthdate = ?, phone = ? WHERE id = ?";

    /** Query for database to update image path by user ID */
    private static final String UPDATE_IMAGE_BY_ID_SQL = "UPDATE users SET image_path = ? WHERE id = ?";

    /** Query for database to update password by user ID */
    private static final String UPDATE_PASSWORD_BY_ID_SQL = "UPDATE users SET password = ? WHERE id = ?";

    /** Query for database to update status by user ID */
    private static final String UPDATE_STATUS_BY_ID_SQL = "UPDATE users SET status = ? WHERE id = ?";

    /** Query for database to find user by login */
    private static final String FIND_USER_BY_LOGIN_SQL = "SELECT users.id,login,password,status,user_status.name as user_status_name,users.name as name,surname,patronymic,birthdate,phone,image_path FROM users " +
            "JOIN user_status ON users.status = user_status.id " +
            "WHERE (login = ?)";

    /** Query for database to find user by user ID */
    private static final String FIND_USER_BY_ID_SQL = "SELECT users.id,login,password,status,user_status.name as user_status_name,users.name as name,surname,patronymic,birthdate,phone,image_path FROM users " +
            "JOIN user_status ON users.status = user_status.id " +
            "WHERE (users.id = ?)";

    /** Query for database to find all users like fullName */
    private static final String FIND_ALL_USERS_LIKE_FULLNAME_SQL = "SELECT users.id,login,password,status,user_status.name as user_status_name,users.name as name,surname,patronymic,birthdate,phone,image_path FROM users " +
            "JOIN user_status ON users.status = user_status.id " +
            "WHERE (users.name ~* ? OR surname ~* ? OR patronymic ~* ?)";

    /** Message, that is put in Exception if there is sign in problem */
    private static final String MESSAGE_SIGN_IN_PROBLEM = "Can't handle UserDAO.signIn request";

    /** Message, that is put in Exception if there is sign up problem */
    private static final String MESSAGE_SIGN_UP_PROBLEM = "Can't handle UserDAO.signUp request";

    /** Message, that is put in Exception if there is update user problem */
    private static final String MESSAGE_UPDATE_USER_PROBLEM = "Can't handle UserDAO.update request";

    /** Message, that is put in Exception if there is update image path problem */
    private static final String MESSAGE_UPDATE_IMAGE_PATH_PROBLEM = "Can't handle UserDAO.updateImage request";

    /** Message, that is put in Exception if there is update password problem */
    private static final String MESSAGE_UPDATE_PASSWORD_PROBLEM = "Can't handle UserDAO.updatePassword request";

    /** Message, that is put in Exception if there is update status problem */
    private static final String MESSAGE_UPDATE_STATUS_PROBLEM = "Can't handle UserDAO.updateStatus request";

    /** Message, that is put in Exception if there is login availability check problem */
    private static final String MESSAGE_IS_LOGIN_AVAILABLE_PROBLEM = "Can't handle UserDAO.isLoginAvailable request";

    /** Message, that is put in Exception if there is find all users like fullName problem */
    private static final String MESSAGE_FIND_ALL_USERS_LIKE_FULLNAME_PROBLEM = "Cant handle UserDAO.findAllByFullName request";

    /** Message, that is put in Exception if there is find user by id problem */
    private static final String MESSAGE_GET_USER_BY_ID_PROBLEM = "Cant handle UserDAO.findUserById request";

    private UserDAOImpl() {
    }

    public static UserDAOImpl getInstance() {
        return instance;
    }


    @Override
    public ResultCode signUp(SignUpData signUpData) throws DAOException {
        final int STATUS_USER = 1;
        final String DUPLICATE_LOGIN_ERROR_CODE = "23505"; // postgres unique violation error code

        try (Connection connection  = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SIGNUP_SQL)) {
            ps.setString(SignDataIndex.LOGIN, signUpData.getLogin());
            ps.setString(SignDataIndex.PASSWORD, stringEncryptor.getHash(signUpData.getPassword()));
            ps.setInt(SignDataIndex.STATUS, STATUS_USER);
            ps.setString(SignDataIndex.NAME, signUpData.getName());
            ps.setString(SignDataIndex.SURNAME, signUpData.getSurname());
            ps.setString(SignDataIndex.PATRONYMIC, signUpData.getPatronymic());
            ps.setDate(SignDataIndex.BIRTH_DATE, Date.valueOf(signUpData.getBirthDate()));
            ps.setString(SignDataIndex.PHONE, signUpData.getPhone());
            ps.execute();

            return ResultCode.RESULT_SUCCESS;

        } catch (SQLException e) {
            if (DUPLICATE_LOGIN_ERROR_CODE.equals(e.getSQLState())) {
                return ResultCode.RESULT_ERROR_DUPLICATE_LOGIN;
            } else {
                throw new DAOException(MESSAGE_SIGN_UP_PROBLEM, e);
            }
        }

    }

    @Override
    public User signIn(SignInData signInData) throws DAOException {
        User user = null;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_LOGIN_SQL)) {
            ps.setString(SignDataIndex.LOGIN, signInData.getLogin());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String encryptedPassword = rs.getString(ColumnName.PASSWORD);

                if (!stringEncryptor.checkHash(signInData.getPassword(), encryptedPassword)) {
                    return null;
                }

                user = new User();
                Status status = new Status();
                status.setId(rs.getInt(ColumnName.STATUS_ID));
                status.setName(rs.getString(ColumnName.STATUS_NAME));

                user.setId(rs.getInt(ColumnName.ID));
                user.setLogin(rs.getString(ColumnName.LOGIN));
                user.setStatus(status);
                user.setName(rs.getString(ColumnName.NAME));
                user.setSurname(rs.getString(ColumnName.SURNAME));
                user.setPatronymic(rs.getString(ColumnName.PATRONYMIC));
                user.setBirthDate(rs.getDate(ColumnName.BIRTHDATE).toLocalDate());
                user.setPhone(rs.getString(ColumnName.PHONE));
                user.setImagePath(rs.getString(ColumnName.IMAGE_PATH));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_SIGN_IN_PROBLEM, e);
        }

        return user;
    }

    @Override
    public ResultCode update(User user) throws DAOException {
        final String DUPLICATE_LOGIN_ERROR_CODE = "23505";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_USER_BY_ID_SQL)) {
            ps.setString(UpdateUserIndex.LOGIN, user.getLogin());
            ps.setString(UpdateUserIndex.NAME, user.getName());
            ps.setString(UpdateUserIndex.SURNAME, user.getSurname());
            ps.setString(UpdateUserIndex.PATRONYMIC, user.getPatronymic());
            ps.setDate(UpdateUserIndex.BIRTH_DATE, Date.valueOf(user.getBirthDate()));
            ps.setString(UpdateUserIndex.PHONE, user.getPhone());
            ps.setInt(UpdateUserIndex.ID, user.getId());
            ps.execute();

            return ResultCode.RESULT_SUCCESS;

        } catch (SQLException e) {
            if (DUPLICATE_LOGIN_ERROR_CODE.equals(e.getSQLState())) {
                return ResultCode.RESULT_ERROR_DUPLICATE_LOGIN;
            } else {
                throw new DAOException(MESSAGE_UPDATE_USER_PROBLEM, e);
            }
        }
    }

    @Override
    public User findById(Integer id) throws DAOException {
        User user = null;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_ID_SQL)) {
            ps.setInt(FindUserIndex.ID, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                Status status = new Status();
                status.setId(rs.getInt(ColumnName.STATUS_ID));
                status.setName(rs.getString(ColumnName.STATUS_NAME));

                user.setId(rs.getInt(ColumnName.ID));
                user.setLogin(rs.getString(ColumnName.LOGIN));
                user.setStatus(status);
                user.setName(rs.getString(ColumnName.NAME));
                user.setSurname(rs.getString(ColumnName.SURNAME));
                user.setPatronymic(rs.getString(ColumnName.PATRONYMIC));
                user.setBirthDate(rs.getDate(ColumnName.BIRTHDATE).toLocalDate());
                user.setPhone(rs.getString(ColumnName.PHONE));
                user.setPhone(rs.getString(ColumnName.IMAGE_PATH));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_GET_USER_BY_ID_PROBLEM, e);
        }

        return user;
    }

    @Override
    public List<User> findAllByFullName(String fullName) throws DAOException {
        final String SPACE_SYMBOL = " ";
        final String DELIMITER_SYMBOL = "|";
        fullName = fullName.replace(SPACE_SYMBOL, DELIMITER_SYMBOL);

        final List<User> users = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_USERS_LIKE_FULLNAME_SQL)) {
            ps.setString(FindAllUsersByFullNameIndex.NAME, fullName);
            ps.setString(FindAllUsersByFullNameIndex.SURNAME, fullName);
            ps.setString(FindAllUsersByFullNameIndex.PATRONYMIC, fullName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                Status status = new Status();
                status.setId(rs.getInt(ColumnName.STATUS_ID));
                status.setName(rs.getString(ColumnName.STATUS_NAME));

                user.setId(rs.getInt(ColumnName.ID));
                user.setLogin(rs.getString(ColumnName.LOGIN));
                user.setStatus(status);
                user.setName(rs.getString(ColumnName.NAME));
                user.setSurname(rs.getString(ColumnName.SURNAME));
                user.setPatronymic(rs.getString(ColumnName.PATRONYMIC));
                user.setBirthDate(rs.getDate(ColumnName.BIRTHDATE).toLocalDate());
                user.setPhone(rs.getString(ColumnName.PHONE));
                user.setImagePath(rs.getString(ColumnName.IMAGE_PATH));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_FIND_ALL_USERS_LIKE_FULLNAME_PROBLEM, e);
        }

        return users;
    }

    @Override
    public boolean isLoginAvailable(String login) throws DAOException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_LOGIN_SQL)) {
            ps.setString(SignDataIndex.LOGIN, login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return false;
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_IS_LOGIN_AVAILABLE_PROBLEM, e);
        }

        return true;
    }

    @Override
    public void updateImage(Integer id, String imagePath) throws DAOException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_IMAGE_BY_ID_SQL)) {
            ps.setString(UpdateImageIndex.IMAGE_PATH, imagePath);
            ps.setInt(UpdateImageIndex.ID, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_UPDATE_IMAGE_PATH_PROBLEM, e);
        }
    }

    @Override
    public void updateStatus(Integer id, int status) throws DAOException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_STATUS_BY_ID_SQL)) {
            ps.setInt(UpdateStatusIndex.STATUS, status);
            ps.setInt(UpdateStatusIndex.ID, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_UPDATE_STATUS_PROBLEM, e);
        }
    }

    @Override
    public void updatePassword(Integer id, String password) throws DAOException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_PASSWORD_BY_ID_SQL)) {
            ps.setString(UpdatePasswordIndex.PASSWORD, stringEncryptor.getHash(password));
            ps.setInt(UpdatePasswordIndex.ID, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_UPDATE_PASSWORD_PROBLEM, e);
        }
    }

    private static class ColumnName {
        private static final String ID = "id";
        private static final String LOGIN = "login";
        private static final String PASSWORD = "password";
        private static final String STATUS_ID = "status";
        private static final String STATUS_NAME = "user_status_name";
        private static final String NAME = "name";
        private static final String SURNAME = "surname";
        private static final String PATRONYMIC = "patronymic";
        private static final String BIRTHDATE = "birthdate";
        private static final String PHONE = "phone";
        private static final String IMAGE_PATH = "image_path";
    }

    private static class SignDataIndex {
        private static final int LOGIN = 1;
        private static final int PASSWORD = 2;
        private static final int STATUS = 3;
        private static final int NAME = 4;
        private static final int SURNAME = 5;
        private static final int PATRONYMIC = 6;
        private static final int BIRTH_DATE = 7;
        private static final int PHONE = 8;
    }

    private static class UpdateUserIndex {
        private static final int LOGIN = 1;
        private static final int NAME = 2;
        private static final int SURNAME = 3;
        private static final int PATRONYMIC = 4;
        private static final int BIRTH_DATE = 5;
        private static final int PHONE = 6;
        private static final int ID = 7;
    }

    private static class FindUserIndex {
        private static final int ID = 1;
    }

    private static class FindAllUsersByFullNameIndex {
        private static final int NAME = 1;
        private static final int SURNAME = 2;
        private static final int PATRONYMIC = 3;
    }

    private static class UpdateImageIndex {
        private static final int IMAGE_PATH = 1;
        private static final int ID = 2;
    }

    private static class UpdateStatusIndex {
        private static final int STATUS = 1;
        private static final int ID = 2;
    }

    private static class UpdatePasswordIndex {
        private static final int PASSWORD = 1;
        private static final int ID = 2;
    }
}
