package com.paramonau.pay4j.command.impl.admin.impl;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.auth.AuthCommand;
import com.paramonau.pay4j.entity.Status;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.AccountService;
import com.paramonau.pay4j.service.OrganizationService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UnlockOrgCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(UnlockOrgCommand.class);

    private static final String ERROR_MESSAGE = "Error at UnlockOrgCommand";
    private static final int ACTIVE_STATUS = 1;

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final Integer orgId = Integer.valueOf(req.getParameter(RequestParameter.ORGANIZATION_ID));
        final Integer accountId = Integer.valueOf(req.getParameter(RequestParameter.ACCOUNT_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrganizationService orgService = serviceProvider.getOrganizationService();
        final AccountService accountService = serviceProvider.getAccountService();

        try {
            Status accountStatus = accountService.findAccountInfoById(accountId).getStatus();

            if (accountStatus.getId() != ACTIVE_STATUS) {
                req.setAttribute(RequestAttribute.ORGANIZATION_ID, orgId);
                req.setAttribute(RequestAttribute.MESSAGE, RequestAttribute.MESSAGE_ACCOUNT_BLOCKED_LOCALE);
                router = new Router(PagePath.GO_TO_ADMIN_ORG_COMMAND, RouterType.REDIRECT);
            } else {
                orgService.updateStatus(orgId, ACTIVE_STATUS);
                router = new Router(PagePath.GO_TO_SUCCESS_PAGE_COMMAND, RouterType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        return router;
    }
}
