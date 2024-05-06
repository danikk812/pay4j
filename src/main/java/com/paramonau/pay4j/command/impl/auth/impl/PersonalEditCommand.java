package com.paramonau.pay4j.command.impl.auth.impl;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.auth.AuthCommand;
import com.paramonau.pay4j.dao.ResultCode;
import com.paramonau.pay4j.dto.user.SignInData;
import com.paramonau.pay4j.dto.user.SignUpData;
import com.paramonau.pay4j.entity.User;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.UserService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class PersonalEditCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(PersonalEditCommand.class);

    private static final String ERROR_MESSAGE = "Error at PersonalEditCommand";

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final User user = (User) req.getSession().getAttribute(RequestAttribute.USER);
        final String login = req.getParameter(RequestParameter.LOGIN);
        final String password = req.getParameter(RequestParameter.PASSWORD);
        final String name = req.getParameter(RequestParameter.NAME);
        final String surname = req.getParameter(RequestParameter.SURNAME);
        final String patronymic = req.getParameter(RequestParameter.PATRONYMIC);
        final String phone = req.getParameter(RequestParameter.PHONE);
        final String birthdate = req.getParameter(RequestParameter.BIRTHDATE);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();

        final SignInData signInData = new SignUpData();
        signInData.setLogin(login);
        signInData.setPassword(password);

        user.setLogin(login);
        user.setName(name);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setPhone(phone);

        try {
            ResultCode resultCode = userService.update(signInData, user);

            switch (resultCode) {
                case RESULT_WRONG_PASSWORD -> {
                    req.setAttribute(RequestAttribute.MESSAGE, RequestAttribute.MESSAGE_WRONG_LOGIN_OR_PASSWORD_LOCALE);
                    router = new Router(PagePath.PERSONAL_EDIT_PAGE, RouterType.FORWARD);
                }
                case RESULT_ERROR_DUPLICATE_LOGIN -> {
                    req.setAttribute(RequestAttribute.MESSAGE, RequestAttribute.MESSAGE_DUPLICATE_LOGIN_LOCALE);
                    router = new Router(PagePath.PERSONAL_EDIT_PAGE, RouterType.FORWARD);
                }
                case RESULT_SUCCESS -> {
                    req.getSession().setAttribute(RequestAttribute.USER, user);
                    router = new Router(PagePath.PERSONAL_PAGE, RouterType.REDIRECT);
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
