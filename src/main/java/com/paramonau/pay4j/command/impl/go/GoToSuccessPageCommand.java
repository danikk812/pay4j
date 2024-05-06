package com.paramonau.pay4j.command.impl.go;

import com.paramonau.pay4j.command.Command;
import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;

import javax.servlet.http.HttpServletRequest;

public class GoToSuccessPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req) {
        req.setAttribute(RequestAttribute.PAYMENT_FRAGMENT, RequestAttribute.FRAGMENT_SUCCESS);
        return new Router(PagePath.PAYMENT_PAGE, RouterType.FORWARD);
    }
}
