package com.paramonau.pay4j.command.impl.go;

import com.paramonau.pay4j.command.Command;
import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.util.RegexpPropertiesUtil;

import javax.servlet.http.HttpServletRequest;

public class GoToSignInCommand implements Command {

    private static final String REGEXP_PROP_LOGIN = "regexp.login";
    private static final String REGEXP_PROP_PASSWORD = "regexp.password";

    @Override
    public Router execute(HttpServletRequest req) {
        RegexpPropertiesUtil regexpPropertyUtil = RegexpPropertiesUtil.getInstance();

        final String REGEXP_LOGIN = regexpPropertyUtil.get(REGEXP_PROP_LOGIN);
        final String REGEXP_PASSWORD = regexpPropertyUtil.get(REGEXP_PROP_PASSWORD);

        req.setAttribute(RequestAttribute.REGEXP_LOGIN, REGEXP_LOGIN);
        req.setAttribute(RequestAttribute.REGEXP_PASSWORD, REGEXP_PASSWORD);

        return new Router(PagePath.SIGN_IN_PAGE, RouterType.FORWARD);
    }
}
