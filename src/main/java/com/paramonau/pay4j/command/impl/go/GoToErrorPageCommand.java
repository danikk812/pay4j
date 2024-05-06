package com.paramonau.pay4j.command.impl.go;

import com.paramonau.pay4j.command.Command;
import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;

import javax.servlet.http.HttpServletRequest;

public class GoToErrorPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req) {
        Throwable throwable = (Throwable) req.getAttribute(RequestAttribute.EXCEPTION_CLASS);

        if (throwable != null && req.getAttribute(RequestAttribute.EXCEPTION) != null) {
            req.setAttribute(RequestAttribute.EXCEPTION, throwable);
        }

        return new Router(PagePath.ERROR_PAGE, RouterType.FORWARD);
    }
}
