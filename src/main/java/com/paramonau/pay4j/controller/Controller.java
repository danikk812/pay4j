package com.paramonau.pay4j.controller;

import com.paramonau.pay4j.command.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);
    private final CommandProvider COMMAND_PROVIDER = CommandProvider.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(RequestParameter.COMMAND);
        Command command = COMMAND_PROVIDER.getCommand(commandName);
        Router router = command.execute(req);

        switch (router.getRouterType()) {
            case REDIRECT -> resp.sendRedirect(router.getPagePath());
            case FORWARD -> {
                RequestDispatcher dispatcher = req.getRequestDispatcher(router.getPagePath());
                dispatcher.forward(req, resp);
            }
            default -> {
                logger.error("Incorrect route type " + router.getRouterType());
                resp.sendRedirect(PagePath.ERROR_404_PAGE);
            }
        }
    }
}
