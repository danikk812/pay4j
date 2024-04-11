package com.paramonau.pay4j.dao;

import com.paramonau.pay4j.entity.SignInData;
import com.paramonau.pay4j.entity.SignUpData;
import com.paramonau.pay4j.entity.User;
import com.paramonau.pay4j.exception.DAOException;

import java.util.List;

public interface UserDAO {


    ResultCode signUp(SignUpData signUpData) throws DAOException;


    User signIn(SignInData signInData) throws DAOException;


    ResultCode update(User user) throws DAOException;


    User findById(Integer id) throws DAOException;


    List<User> findAllByFullName(String fullName) throws DAOException;


    boolean isLoginAvailable(String login) throws DAOException;


    void updateImage(Integer id, String imagePath) throws DAOException;


    void updateStatus(Integer id, int status) throws DAOException;


    void updatePassword(Integer id, String password) throws DAOException;
}
