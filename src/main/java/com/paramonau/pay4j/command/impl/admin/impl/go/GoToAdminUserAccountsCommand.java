package com.paramonau.pay4j.command.impl.admin.impl.go;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.auth.AuthCommand;
import com.paramonau.pay4j.entity.Account;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.AccountService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GoToAdminUserAccountsCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToAdminUserAccountsCommand.class);


    private static final String ERROR_MESSAGE = "Error at GoToAdminUserAccountsCommand";

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final Integer userId = Integer.valueOf(req.getParameter(RequestParameter.USER_ID));
        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();
        List<Account> accountList = null;

        try {
            accountList = accountService.findAllByUserId(userId);

            req.setAttribute(RequestAttribute.ACCOUNTS, accountList);
            req.setAttribute(RequestAttribute.ADMIN_FRAGMENT, RequestAttribute.FRAGMENT_ADMIN_USER_ACCOUNTS);
            router = new Router(PagePath.ADMIN_PAGE, RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        return router;
    }
}
