package com.paramonau.pay4j.command.impl.auth;

import com.paramonau.pay4j.command.Command;
import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;

import javax.servlet.http.HttpServletRequest;

public abstract class AuthCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req) {
        return checkAuthAndProcess(req);
    }

    private Router checkAuthAndProcess(HttpServletRequest req) {
        if (req.getSession().getAttribute(RequestAttribute.USER) == null) {
            req.setAttribute(RequestAttribute.MESSAGE, RequestAttribute.MESSAGE_LOG_IN_TO_CONTINUE_LOCALE);
            return new Router(PagePath.GO_TO_SIGN_IN_COMMAND, RouterType.FORWARD);
        } else {
            return process(req);
        }
    }

    protected abstract Router process(HttpServletRequest req);
}
