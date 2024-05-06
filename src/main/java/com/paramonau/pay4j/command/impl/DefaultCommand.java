package com.paramonau.pay4j.command.impl;

import com.paramonau.pay4j.command.Command;
import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;

import javax.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req) {
        return new Router(PagePath.ERROR_404_PAGE, RouterType.REDIRECT);
    }
}
