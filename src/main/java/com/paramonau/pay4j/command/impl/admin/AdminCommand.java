package com.paramonau.pay4j.command.impl.admin;

import com.paramonau.pay4j.command.Command;
import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.entity.User;

import javax.servlet.http.HttpServletRequest;

public abstract class AdminCommand implements Command {

    private static final int ADMIN_ROLE = 2;

    @Override
    public Router execute(HttpServletRequest req) {
        return checkAuthAndProcess(req);
    }

    private Router checkAuthAndProcess(HttpServletRequest req) {
        Router router;
        User user = (User) req.getSession().getAttribute(RequestAttribute.USER);

        if (user != null) {
            if (user.getStatus().getId() == ADMIN_ROLE) {
                return process(req);
            } else {
                router = new Router(PagePath.NO_RIGHTS_PAGE, RouterType.FORWARD);
            }
        } else {
            req.setAttribute(RequestAttribute.MESSAGE, RequestAttribute.MESSAGE_LOG_IN_TO_CONTINUE_LOCALE);
            router = new Router(PagePath.GO_TO_SIGN_IN_COMMAND, RouterType.FORWARD);
        }

        return router;
    }

    protected abstract Router process(HttpServletRequest req);
}
