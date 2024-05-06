package com.paramonau.pay4j.command.impl.admin.impl;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.admin.AdminCommand;

import javax.servlet.http.HttpServletRequest;

import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.UserService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;


public class RevokeAdminRightsCommand extends AdminCommand {

    private static final Logger logger = Logger.getLogger(RevokeAdminRightsCommand.class);

    private static final String ERROR_MESSAGE = "Error at RevokeAdminRightsCommand";
    private static final int USER_STATUS = 1;


    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final Integer userId = Integer.valueOf(req.getParameter(RequestParameter.USER_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();

        try {
            userService.updateStatus(userId, USER_STATUS);
            router = new Router(PagePath.GO_TO_SUCCESS_PAGE_COMMAND, RouterType.REDIRECT);
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        return router;
    }
}
