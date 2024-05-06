package com.paramonau.pay4j.command.impl;

import com.paramonau.pay4j.command.*;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.dao.ResultCode;
import com.paramonau.pay4j.dto.user.SignUpData;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.UserService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public class SignUpCommand implements Command {

    private static final Logger logger = Logger.getLogger(SignUpCommand.class);

    private static final String ERROR_MESSAGE = "Error at SignUpCommand";

    @Override
    public Router execute(HttpServletRequest req) {
        Router router;
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();

        final String login = req.getParameter(RequestParameter.LOGIN);
        final String password = req.getParameter(RequestParameter.PASSWORD);
        final String name = req.getParameter(RequestParameter.NAME);
        final String surname = req.getParameter(RequestParameter.SURNAME);
        final String patronymic = req.getParameter(RequestParameter.PATRONYMIC);
        final String phone = req.getParameter(RequestParameter.PHONE);
        final String birthrate = req.getParameter(RequestParameter.BIRTHDATE);

        SignUpData signUpData = new SignUpData();
        signUpData.setLogin(login);
        signUpData.setPassword(password);
        signUpData.setName(name);
        signUpData.setSurname(surname);
        signUpData.setPatronymic(patronymic);
        signUpData.setPhone(phone);
        signUpData.setBirthDate(LocalDate.parse(birthrate));

        try {
            ResultCode resultCode = userService.signUp(signUpData);

            switch (resultCode) {
                case RESULT_ERROR_DUPLICATE_LOGIN -> {
                    req.setAttribute(RequestAttribute.MESSAGE, RequestAttribute.MESSAGE_DUPLICATE_LOGIN_LOCALE);
                    router = new Router(PagePath.SIGN_UP_PAGE, RouterType.FORWARD);
                }
                case RESULT_SUCCESS -> {
                    router = new Router(PagePath.GO_TO_SUCCESS_PAGE_COMMAND, RouterType.REDIRECT);
                }
                default -> router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        return router;
    }
}
