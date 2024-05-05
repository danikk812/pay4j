package com.paramonau.pay4j.service.impl;

import com.paramonau.pay4j.dao.ResultCode;
import com.paramonau.pay4j.dao.UserDAO;
import com.paramonau.pay4j.dao.provider.DAOProvider;
import com.paramonau.pay4j.dto.user.SignInData;
import com.paramonau.pay4j.dto.user.SignUpData;
import com.paramonau.pay4j.entity.User;
import com.paramonau.pay4j.exception.DAOException;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.UserService;
import com.paramonau.pay4j.validator.UserValidator;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final DAOProvider daoProvider = DAOProvider.getInstance();
    private static final UserDAO userDAO = daoProvider.getUserDAO();

    private static final String MESSAGE_SIGN_IN_PROBLEM = "Can't handle signIn request at UserService";
    private static final String MESSAGE_USER_VALIDATION_IN_UPDATE_PROBLEM = "User data didn't passed validation in update at UserService";
    private static final String MESSAGE_USER_VALIDATION_IN_SIGN_UP_PROBLEM = "User data didn't passed validation in signUp at UserService";
    private static final String MESSAGE_UPDATE_USER_PROBLEM = "Can't handle update user request at UserService";
    private static final String MESSAGE_FIND_USER_BY_ID_PROBLEM = "Can't handle find user by id request at UserService";
    private static final String MESSAGE_FIND_ALL_BY_FULLNAME_PROBLEM = "Can't handle findAllByFullName request at UserService";
    private static final String MESSAGE_UPDATE_IMAGE_BY_USER_ID_PROBLEM = "Can't handle updateImage request at UserService";
    private static final String MESSAGE_SIGN_UP_PROBLEM = "Can't handle signUp request at UserService";
    private static final String MESSAGE_UPDATE_PASSWORD_BY_USER_ID_PROBLEM = "Can't handle updatePassword request at UserService";
    private static final String MESSAGE_IS_LOGIN_AVAILABLE_PROBLEM = "Can't handle isLoginAvailable request at UserService";
    private static final String MESSAGE_UPDATE_STATUS_BY_USER_ID_PROBLEM = "Can't handle updateStatus request at UserService";


    @Override
    public User signIn(SignInData signInData) throws ServiceException {
        try {
            return userDAO.signIn(signInData);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_SIGN_IN_PROBLEM, e);
        }
    }

    @Override
    public ResultCode update(SignInData signInData, User updatedUser) throws ServiceException {
        if (signIn(signInData) == null) {
            return ResultCode.RESULT_WRONG_PASSWORD;
        }

        if (!UserValidator.validate(updatedUser)) {
            throw new ServiceException(MESSAGE_USER_VALIDATION_IN_UPDATE_PROBLEM);
        }
        try {
            return userDAO.update(updatedUser);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_UPDATE_USER_PROBLEM, e);
        }
    }

    @Override
    public User findById(Integer id) throws ServiceException {
        try {
            return userDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_USER_BY_ID_PROBLEM, e);
        }
    }

    @Override
    public List<User> findAllByFullName(String fullName) throws ServiceException {
        try {
            return userDAO.findAllByFullName(fullName);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_FIND_ALL_BY_FULLNAME_PROBLEM, e);
        }
    }

    @Override
    public void updateImage(Integer id, String imagePath) throws ServiceException {
        try {
            userDAO.updateImage(id, imagePath);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_UPDATE_IMAGE_BY_USER_ID_PROBLEM, e);
        }
    }

    @Override
    public ResultCode signUp(SignUpData signUpData) throws ServiceException {
        if (!UserValidator.validate(signUpData)) {
            throw new ServiceException(MESSAGE_USER_VALIDATION_IN_SIGN_UP_PROBLEM);
        }

        try {
            return userDAO.signUp(signUpData);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_SIGN_UP_PROBLEM, e);
        }
    }

    @Override
    public boolean updatePassword(String newPassword, SignInData signInData) throws ServiceException {
        User user = signIn(signInData);
        if (user == null) {
            return false;
        }

        try {
            userDAO.updatePassword(user.getId(), newPassword);
            return true;
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_UPDATE_PASSWORD_BY_USER_ID_PROBLEM, e);
        }
    }

    @Override
    public boolean isLoginAvailable(String login) throws ServiceException {
        try {
            return userDAO.isLoginAvailable(login);
        } catch (DAOException e) {
            throw new ServiceException(MESSAGE_IS_LOGIN_AVAILABLE_PROBLEM, e);
        }
    }

    @Override
    public void updateStatus(Integer id, Integer status) throws ServiceException {
        try {
            userDAO.updateStatus(id, status);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
