package com.paramonau.pay4j.command.impl;

import com.paramonau.pay4j.command.*;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.dto.user.SignInData;
import com.paramonau.pay4j.entity.User;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.UserService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SignInCommand implements Command {

    private static final Logger logger = Logger.getLogger(SignInCommand.class);

    private static final String ERROR_MESSAGE = "Error at SignInCommand";

    @Override
    public Router execute(HttpServletRequest req) {
        Router router;
        String login = req.getParameter(RequestParameter.LOGIN);
        String password = req.getParameter(RequestParameter.PASSWORD);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();

        SignInData signInData = new SignInData();
        signInData.setLogin(login);
        signInData.setPassword(password);

        try {
            User user = userService.signIn(signInData);

            if (user == null) {
                req.setAttribute(RequestAttribute.MESSAGE, RequestAttribute.MESSAGE_WRONG_LOGIN_OR_PASSWORD_LOCALE);
                router = new Router(PagePath.SIGN_IN_PAGE, RouterType.FORWARD);
            } else {
                req.getSession().setAttribute(RequestAttribute.USER, user);
                router = new Router(PagePath.GO_TO_ACCOUNTS_COMMAND, RouterType.REDIRECT);
            }

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        return router;
    }
}
