package com.paramonau.pay4j.command.impl;

import com.paramonau.pay4j.command.Command;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLocaleCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req) {
        final HttpSession SESSION = req.getSession();
        final String LOCALE = req.getParameter(RequestParameter.LOCALE);
        final String PREVIOUS_REQUEST = (String) SESSION.getAttribute(RequestAttribute.PREV_REQUEST);
        SESSION.setAttribute(RequestAttribute.LOCALE, LOCALE);
        return new Router(PREVIOUS_REQUEST, RouterType.REDIRECT);
    }
}
