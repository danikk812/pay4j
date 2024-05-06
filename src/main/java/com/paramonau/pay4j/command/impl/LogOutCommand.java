package com.paramonau.pay4j.command.impl;

import com.paramonau.pay4j.command.Command;
import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req) {
        req.getSession().invalidate();
        return new Router(PagePath.GO_TO_ABOUT_PAGE_COMMAND, RouterType.REDIRECT);
    }
}
