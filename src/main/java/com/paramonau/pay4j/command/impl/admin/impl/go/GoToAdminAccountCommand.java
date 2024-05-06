package com.paramonau.pay4j.command.impl.admin.impl.go;

import com.paramonau.pay4j.command.PagePath;
import com.paramonau.pay4j.command.RequestAttribute;
import com.paramonau.pay4j.command.RequestParameter;
import com.paramonau.pay4j.command.Router;
import com.paramonau.pay4j.command.Router.RouterType;
import com.paramonau.pay4j.command.impl.admin.AdminCommand;
import com.paramonau.pay4j.entity.Account;
import com.paramonau.pay4j.entity.Card;
import com.paramonau.pay4j.exception.ServiceException;
import com.paramonau.pay4j.service.AccountService;
import com.paramonau.pay4j.service.CardService;
import com.paramonau.pay4j.service.provider.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GoToAdminAccountCommand extends AdminCommand {

    private static final Logger logger = Logger.getLogger(GoToAdminAccountCommand.class);

    private static final String ERROR_MESSAGE = "Error at GoToAccountCommand";

    @Override
    protected Router process(HttpServletRequest req) {
        Router router;

        final Integer accountId = Integer.valueOf(req.getParameter(RequestParameter.ACCOUNT_ID));

        final ServiceProvider serviceProvider = ServiceProvider.getInstance();
        final AccountService accountServiceImpl = serviceProvider.getAccountService();
        final CardService cardServiceImpl = serviceProvider.getCardService();
        Account account = null;
        List<Card> cards = null;

        try {
            account = accountServiceImpl.findById(accountId);
            cards = cardServiceImpl.findAllByAccountId(accountId);

        } catch (ServiceException e) {
            logger.error(ERROR_MESSAGE, e);
            req.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.GO_TO_ERROR_PAGE_COMMAND, RouterType.REDIRECT);
        }

        req.setAttribute(RequestAttribute.ACCOUNT, account);
        req.setAttribute(RequestAttribute.CARDS, cards);
        req.setAttribute(RequestAttribute.ADMIN_FRAGMENT, RequestAttribute.FRAGMENT_ADMIN_ACCOUNT);
        router = new Router(PagePath.ADMIN_PAGE, RouterType.FORWARD);

        return router;
    }
}
