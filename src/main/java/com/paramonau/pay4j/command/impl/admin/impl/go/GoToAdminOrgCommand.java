package com.paramonau.pay4j.command.impl.admin.impl.go;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.admin.AdminCommand;
import com.paramonau.pay4j.dto.account.AccountInfo;
import com.paramonau.pay4j.entity.Organization;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.AccountService;
import com.paramonau.pay4j.service.OrganizationService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class GoToAdminOrgCommand extends AdminCommand {

    private static final Logger logger = Logger.getLogger(GoToAdminOrgCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAdminOrgCommand";

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final Integer orgId = Integer.valueOf(req.getParameter(RequestParameter.ORGANIZATION_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrganizationService orgService = serviceProvider.getOrganizationService();
        final AccountService accountService = serviceProvider.getAccountService();
        AccountInfo accountInfo = null;
        Organization organization = null;

        try {
            organization = orgService.findById(orgId);
            accountInfo = accountService.findAccountInfoById(organization.getAccount());
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        req.setAttribute(RequestAttribute.ORGANIZATION, organization);
        req.setAttribute(RequestAttribute.ACCOUNT_INFO, accountInfo);
        req.setAttribute(RequestAttribute.ADMIN_FRAGMENT, RequestAttribute.FRAGMENT_ADMIN_ORG);

        router = new Router(PagePath.ADMIN_PAGE, RouterType.FORWARD);
        return router;
    }
}
