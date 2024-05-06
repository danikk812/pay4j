package com.paramonau.pay4j.command.impl.admin.impl.go;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.auth.AuthCommand;
import com.paramonau.pay4j.dto.account.AccountInfo;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.AccountService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class GoToEditOrgConfirmCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(GoToEditOrgConfirmCommand.class);

    private static final String ERROR_MESSAGE = "Error at goToEditOrgConfirmCommand";
    private static final int ACTIVE_STATUS = 1;

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final Integer accountId = Integer.valueOf(req.getParameter(RequestParameter.ACCOUNT_ID));
        final Integer orgId = Integer.valueOf(req.getParameter(RequestParameter.ORGANIZATION_ID));
        final String orgName = req.getParameter(RequestParameter.ORG_NAME);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountService = serviceProvider.getAccountService();
        AccountInfo accountInfo = null;

        try {
            accountInfo = accountService.findAccountInfoById(accountId);
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        if (accountInfo == null || accountInfo.getStatus().getId() != ACTIVE_STATUS) {
            req.setAttribute(RequestAttribute.MESSAGE, RequestAttribute.MESSAGE_WRONG_ACCOUNT_NUMBER_LOCALE);
            router = new Router(PagePath.GO_TO_EDIT_ORG_COMMAND, RouterType.REDIRECT);
        } else {
            req.setAttribute(RequestAttribute.ACCOUNT_INFO, accountInfo);
            req.setAttribute(RequestAttribute.ORG_NAME, orgName);
            req.setAttribute(RequestAttribute.ORGANIZATION_ID, orgId);
            req.setAttribute(RequestAttribute.ADMIN_FRAGMENT, RequestAttribute.FRAGMENT_EDIT_ORG_CONFIRM);
            router = new Router(PagePath.ADMIN_PAGE, RouterType.FORWARD);
        }

        return router;
    }
}
