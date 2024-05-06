package com.paramonau.pay4j.command.impl.admin.impl.go;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.admin.AdminCommand;
import com.paramonau.pay4j.util.RegexpPropertiesUtil;

import javax.servlet.http.HttpServletRequest;

public class GoToAddOrgCommand extends AdminCommand {

    private static final String REGEXP_PROP_ORG_NAME = "regexp.org_name";

    @Override
    protected Router process(HttpServletRequest req) {
        RegexpPropertiesUtil regexpPropertyUtil = RegexpPropertiesUtil.getInstance();
        final String REGEXP_ORG_NAME = regexpPropertyUtil.get(REGEXP_PROP_ORG_NAME);

        req.setAttribute(RequestAttribute.ADMIN_FRAGMENT, RequestAttribute.FRAGMENT_ADMIN_ADD_ORG);
        req.setAttribute(RequestAttribute.REGEXP_ORG_NAME, REGEXP_ORG_NAME);
        return new Router(PagePath.ADMIN_PAGE, RouterType.FORWARD);
    }
}
