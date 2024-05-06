package com.paramonau.pay4j.command.impl.admin.impl.go;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.admin.AdminCommand;

import javax.servlet.http.HttpServletRequest;

import com.paramonau.pay4j.entity.Organization;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.OrganizationService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;

import java.util.List;


public class GoToAdminOrgsCommand extends AdminCommand {

    private static final Logger logger = Logger.getLogger(GoToAdminAccountsCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAdminOrgCommand";


    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        String searchOrgName = req.getParameter(RequestParameter.ORG_SEARCH_NAME);

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final OrganizationService organizationService = serviceProvider.getOrganizationService();
        List<Organization> orgList = null;

        try {
            if (searchOrgName != null) {
                searchOrgName = searchOrgName.trim();
                orgList = organizationService.findAllByName(searchOrgName);

                req.setAttribute(RequestAttribute.ORG_SEARCH_NAME, searchOrgName);
            } else {
                orgList = organizationService.findAll();
            }
        } catch (ServiceException e){
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        req.setAttribute(RequestAttribute.ORG_LIST, orgList);
        req.setAttribute(RequestAttribute.ADMIN_FRAGMENT, RequestAttribute.FRAGMENT_ADMIN_ORGS);

        router = new Router(PagePath.ADMIN_PAGE, RouterType.FORWARD);
        return router;
    }

}
