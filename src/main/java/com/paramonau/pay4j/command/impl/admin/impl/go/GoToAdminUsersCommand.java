package com.paramonau.pay4j.command.impl.admin.impl.go;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.admin.AdminCommand;
import com.paramonau.pay4j.entity.User;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.UserService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GoToAdminUsersCommand extends AdminCommand {

    private static final Logger logger = Logger.getLogger(GoToAdminAccountsCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAdminUsersCommand";

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        String searchUserName = req.getParameter(RequestParameter.USER_SEARCH_NAME);
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final UserService userService = serviceProvider.getUserService();
        List<User> userList = null;

        try {
            if (searchUserName != null) {
                searchUserName = searchUserName.trim();
                userList = userService.findAllByFullName(searchUserName);

                req.setAttribute(RequestAttribute.USER_SEARCH_NAME, searchUserName);
            }

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        req.setAttribute(RequestAttribute.USER_LIST, userList);
        req.setAttribute(RequestAttribute.ADMIN_FRAGMENT, RequestAttribute.FRAGMENT_ADMIN_USERS);

        router = new Router(PagePath.ADMIN_PAGE, RouterType.FORWARD);
        return router;
    }
}
