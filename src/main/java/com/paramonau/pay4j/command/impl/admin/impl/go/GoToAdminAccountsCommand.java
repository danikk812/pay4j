package com.paramonau.pay4j.command.impl.admin.impl.go;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.admin.AdminCommand;
import com.paramonau.pay4j.dto.account.AccountInfo;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.AccountService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class GoToAdminAccountsCommand extends AdminCommand {

    private static final Logger logger = Logger.getLogger(GoToAdminAccountsCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAdminAccountsCommand";

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        String searchAccountId = req.getParameter(RequestParameter.ACCOUNT_SEARCH_ID);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();
        List<AccountInfo> accountInfoList = null;

        try {
            if (searchAccountId != null) {
                searchAccountId = searchAccountId.trim();
                Integer searchId = Integer.valueOf(searchAccountId);

                accountInfoList = accountService.findAllAccountsInfoById(searchId);
                req.setAttribute(RequestAttribute.ACCOUNT_SEARCH_ID, searchAccountId);
            }
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        req.setAttribute(RequestAttribute.ACCOUNT_INFO_LIST, accountInfoList);
        req.setAttribute(RequestAttribute.ADMIN_FRAGMENT, RequestAttribute.FRAGMENT_ADMIN_ACCOUNTS);

        router = new Router(PagePath.ADMIN_PAGE, RouterType.FORWARD);
        return router;
    }
}
