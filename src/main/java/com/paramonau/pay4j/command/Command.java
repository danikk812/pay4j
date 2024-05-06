package com.paramonau.pay4j.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    Router execute(HttpServletRequest req);
}
