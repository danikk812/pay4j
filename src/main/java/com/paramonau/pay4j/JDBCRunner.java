package com.paramonau.pay4j;

import com.paramonau.pay4j.dao.impl.UserDAOImpl;
import com.paramonau.pay4j.entity.SignUpData;
import com.paramonau.pay4j.entity.User;
import com.paramonau.pay4j.exception.DAOException;

import java.time.LocalDate;
import java.util.List;

public class JDBCRunner {

    public static void main(String[] args) {

        UserDAOImpl userDAO = UserDAOImpl.getInstance();

        SignUpData signUpData = new SignUpData();
        signUpData.setLogin("test_login");
        signUpData.setPassword("test_password");
        signUpData.setName("Danila");
        signUpData.setSurname("Paramonau");
        signUpData.setPatronymic("Sergeevich");
        signUpData.setBirthDate(LocalDate.of(2000, 11, 3));
        signUpData.setPhone("375338764534");

        try {
            userDAO.signUp(signUpData);
            List<User> users = userDAO.findAllByFullName("danila paramonau");
            System.out.println(users);
            System.out.println(users.size());

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }
}
