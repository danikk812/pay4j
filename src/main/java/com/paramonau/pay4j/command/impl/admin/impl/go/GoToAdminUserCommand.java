package com.paramonau.pay4j.command.impl.admin.impl.go;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.admin.AdminCommand;

import javax.servlet.http.HttpServletRequest;

import com.paramonau.pay4j.entity.User;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.UserService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;


public class GoToAdminUserCommand extends AdminCommand {

    private static final Logger logger = Logger.getLogger(GoToAdminAccountCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAdminUserCommand";

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final Integer userId = Integer.valueOf(req.getParameter(RequestParameter.USER_ID));
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();
        User user = null;

        try {
            user = userService.findById(userId);

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        req.setAttribute(RequestAttribute.USER, user);
        req.setAttribute(RequestAttribute.ADMIN_FRAGMENT, RequestAttribute.FRAGMENT_ADMIN_USER);

        router = new Router(PagePath.ADMIN_PAGE, RouterType.FORWARD);
        return router;
    }
}
