package com.paramonau.pay4j.command.impl.go;

import com.paramonau.pay4j.command.Command;
import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.util.RegexpPropertiesUtil;

import javax.servlet.http.HttpServletRequest;

public class GoToSignUpCommand implements Command {

    private static final String REGEXP_PROP_LOGIN = "regexp.login";
    private static final String REGEXP_PROP_PASSWORD = "regexp.password";
    private static final String REGEXP_PROP_FULLNAME = "regexp.user_fullname";
    private static final String REGEXP_PROP_PHONE = "regexp.phone";

    @Override
    public Router execute(HttpServletRequest req) {
        RegexpPropertiesUtil regexpPropertyUtil = RegexpPropertiesUtil.getInstance();

        final String REGEXP_LOGIN = regexpPropertyUtil.get(REGEXP_PROP_LOGIN);
        final String REGEXP_PASSWORD = regexpPropertyUtil.get(REGEXP_PROP_PASSWORD);
        final String REGEXP_FULLNAME = regexpPropertyUtil.get(REGEXP_PROP_FULLNAME);
        final String REGEXP_PHONE = regexpPropertyUtil.get(REGEXP_PROP_PHONE);

        req.setAttribute(RequestAttribute.REGEXP_LOGIN, REGEXP_LOGIN);
        req.setAttribute(RequestAttribute.REGEXP_PASSWORD, REGEXP_PASSWORD);
        req.setAttribute(RequestAttribute.REGEXP_FULLNAME, REGEXP_FULLNAME);
        req.setAttribute(RequestAttribute.REGEXP_PHONE, REGEXP_PHONE);

        return new Router(PagePath.SIGN_UP_PAGE, RouterType.FORWARD);
    }
}
