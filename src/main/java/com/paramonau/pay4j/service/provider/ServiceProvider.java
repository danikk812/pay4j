package com.paramonau.pay4j.service.provider;

import com.paramonau.pay4j.service.*;
import com.paramonau.pay4j.service.impl.*;

public final class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private final UserService userService = new UserServiceImpl();
    private final CardService cardService = new CardServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();
    private final OrganizationService organizationService = new OrganizationServiceImpl();
    private final AccountService accountService = new AccountServiceImpl();

    private ServiceProvider() {}

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public CardService getCardService() {
        return cardService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public AccountService getAccountService() {
        return accountService;
    }
}
