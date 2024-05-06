package com.paramonau.pay4j.service;

import com.paramonau.pay4j.dao.ResultCode;
import com.paramonau.pay4j.dto.user.SignInData;
import com.paramonau.pay4j.dto.user.SignUpData;
import com.paramonau.pay4j.entity.User;
import com.paramonau.pay4j.exception.ServiceException;

import java.util.List;

public interface UserService {

    User signIn(SignInData signInData) throws ServiceException;

    ResultCode update(SignInData signInData, User updatedUser) throws ServiceException;

    User findById(Integer id) throws ServiceException;

    List<User> findAllByFullName(String fullName) throws ServiceException;

    void updateImage(Integer id, String imagePath) throws ServiceException;

    ResultCode signUp(SignUpData signUpData) throws ServiceException;

    boolean updatePassword(String newPassword, SignInData signInData) throws ServiceException;

    boolean isLoginAvailable(String login) throws ServiceException;

    void updateStatus(Integer id, Integer status) throws ServiceException;
}
