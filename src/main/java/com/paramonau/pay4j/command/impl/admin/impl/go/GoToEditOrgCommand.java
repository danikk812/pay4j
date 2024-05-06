package com.paramonau.pay4j.command.impl.admin.impl.go;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.admin.AdminCommand;
import com.paramonau.pay4j.entity.Organization;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.OrganizationService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class GoToEditOrgCommand extends AdminCommand {

    private static final Logger logger = Logger.getLogger(GoToEditOrgCommand.class);

    private static final String ERROR_MESSAGE = "Error at AddOrgCommand";

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final Integer orgId = Integer.valueOf(req.getParameter(RequestParameter.ORGANIZATION_ID));

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        OrganizationService organizationService = serviceProvider.getOrganizationService();
        Organization organization = null;

        try {
            organization = organizationService.findById(orgId);
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        req.setAttribute(RequestAttribute.ORGANIZATION_ID, orgId);
        req.setAttribute(RequestAttribute.ACCOUNT_ID, organization.getAccount());
        req.setAttribute(RequestAttribute.ORG_NAME, organization.getName());
        req.setAttribute(RequestAttribute.ADMIN_FRAGMENT, RequestAttribute.FRAGMENT_ADMIN_EDIT_ORG);

        router = new Router(PagePath.ADMIN_PAGE, RouterType.FORWARD);
        return router;
    }
}
