package com.paramonau.pay4j.command.impl.go;

import com.paramonau.pay4j.command.Command;
import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;

import javax.servlet.http.HttpServletRequest;

public class GoToAboutPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req) {
        return new Router(PagePath.ABOUT_PAGE, RouterType.FORWARD);
    }
}
