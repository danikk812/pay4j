package com.paramonau.pay4j.validator;

import com.paramonau.pay4j.dto.user.SignUpData;
import com.paramonau.pay4j.entity.User;
import com.paramonau.pay4j.util.RegexpPropertiesUtil;

public final class UserValidator {

    private static final String REGEXP_PHONE = "regexp.phone";
    private static final String REGEXP_USER_FULLNAME = "regexp.user_fullname";
    private static final String REGEXP_LOGIN = "regexp.login";

    private static final RegexpPropertiesUtil regexpPropertyUtil = RegexpPropertiesUtil.getInstance();

    private UserValidator() {
    }

    public static boolean validate(SignUpData signUpData) {
        return validateData(signUpData.getLogin(), signUpData.getName(), signUpData.getSurname(), signUpData.getPatronymic(), signUpData.getPhone());
    }

    public static boolean validate(User user) {
        return validateData(user.getLogin(), user.getName(), user.getSurname(), user.getPatronymic(), user.getPhone());
    }

    private static boolean validateData(String login, String name, String surname, String patronymic, String phone) {
        return validateLogin(login) && validateFullName(name) && validateFullName(surname) && validateFullName(patronymic) && validatePhone(phone);
    }

    private static boolean validateLogin(String login) {
        return matches(login, regexpPropertyUtil.get(REGEXP_LOGIN));
    }

    private static boolean validateFullName(String fullName) {
        return matches(fullName, regexpPropertyUtil.get(REGEXP_USER_FULLNAME));
    }

    private static boolean validatePhone(String phone) {
        return matches(phone, regexpPropertyUtil.get(REGEXP_PHONE));
    }

    private static boolean matches(String text, String regex) {
        return text != null && text.matches(regex);
    }
}
