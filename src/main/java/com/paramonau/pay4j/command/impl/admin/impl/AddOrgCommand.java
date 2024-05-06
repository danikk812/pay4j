package com.paramonau.pay4j.command.impl.admin.impl;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.auth.AuthCommand;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.OrganizationService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddOrgCommand extends AuthCommand {

    private static final Logger logger = Logger.getLogger(AddOrgCommand.class);

    private static final String ERROR_MESSAGE = "Error at AddOrgCommand";

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final String orgName = req.getParameter(RequestParameter.ORG_NAME);
        final Integer orgAccount = Integer.valueOf(req.getParameter(RequestParameter.ACCOUNT_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrganizationService orgService = serviceProvider.getOrganizationService();

        try {
            orgService.create(orgName, orgAccount);
            router = new Router(PagePath.GO_TO_SUCCESS_PAGE_COMMAND, RouterType.REDIRECT);
        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        return router;
    }
}
