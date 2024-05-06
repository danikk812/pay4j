package com.paramonau.pay4j.command;

public final class RequestAttribute {

    public static final String CONTROLLER_URL = "Controller?";
    public static final String LOCALE = "locale";
    public static final String PREV_REQUEST = "prev_request";
    public static final String MESSAGE = "message";
    public static final String MESSAGE_WRONG_LOGIN_OR_PASSWORD_LOCALE = "wrong_login_or_password";
    public static final String USER = "user";
    public static final String EXCEPTION = "exception";
    public static final String EXCEPTION_CLASS = "javax.servlet.error.exception";
    public static final String MESSAGE_DUPLICATE_LOGIN_LOCALE = "login_already_taken";
    public static final String REGEXP_LOGIN = "attribute_regexp_login";
    public static final String REGEXP_PASSWORD = "attribute_regexp_password";
    public static final String REGEXP_FULLNAME = "attribute_regexp_fullname";
    public static final String REGEXP_PHONE = "attribute_regexp_phone";
    public static final String PAYMENT_FRAGMENT = "payment_fragment";
    public static final String FRAGMENT_SUCCESS = "views/fragments/success_page.jsp";
    public static final String MESSAGE_LOG_IN_TO_CONTINUE_LOCALE = "sign_in_to_continue";
    public static final String ADMIN_FRAGMENT = "admin_fragment";
    public static final String FRAGMENT_ADMIN_ADD_ORG = "views/fragments/admin/admin_add_org.jsp";
    public static final String REGEXP_ORG_NAME = "attribute_regexp_org_name";
    public static final String ACCOUNT_INFO = "accountInfo";
    public static final String MESSAGE_WRONG_ACCOUNT_NUMBER_LOCALE = "wrong_account_number";
    public static final String FRAGMENT_ADD_ORG_CONFIRM = "views/fragments/admin/admin_add_org_confirm.jsp";
    public static final String ORG_NAME = "name";
    public static final String ACCOUNT = "account";
    public static final String CARDS = "cards";
    public static final String FRAGMENT_ADMIN_ACCOUNT = "views/fragments/admin/admin_account.jsp";
    public static final String ACCOUNT_INFO_LIST = "accountInfoList";
    public static final String FRAGMENT_ADMIN_ACCOUNTS = "views/fragments/admin/admin_accounts.jsp";
    public static final String ACCOUNT_SEARCH_ID = "searchId";
    public static final String ORGANIZATION = "organization";
    public static final String FRAGMENT_ADMIN_ORG = "views/fragments/admin/admin_org.jsp";
    public static final String ORG_LIST = "orgList";
    public static final String FRAGMENT_ADMIN_ORGS = "views/fragments/admin/admin_orgs.jsp";
    public static final String ORG_SEARCH_NAME = "searchName";
    public static final String FRAGMENT_ADMIN_USER_ACCOUNTS = "views/fragments/admin/admin_user_accounts.jsp";
    public static final String ACCOUNTS = "accounts";
    public static final String FRAGMENT_ADMIN_USER = "views/fragments/admin/admin_user.jsp";
    public static final String USER_SEARCH_NAME = "searchName";
    public static final String USER_LIST = "userList";
    public static final String FRAGMENT_ADMIN_USERS = "views/fragments/admin/admin_users.jsp";
    public static final String FRAGMENT_ADMIN_EDIT_ORG = "views/fragments/admin/admin_edit_org.jsp";
    public static final String ORGANIZATION_ID = "orgId";
    public static final String ACCOUNT_ID = "accountId";
    public static final String FRAGMENT_EDIT_ORG_CONFIRM = "views/fragments/admin/admin_edit_org_confirm.jsp";
    public static final String MESSAGE_ACCOUNT_BLOCKED_LOCALE = "message_account_blocked";


    private RequestAttribute() {
    }

}
