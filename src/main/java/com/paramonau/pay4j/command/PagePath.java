package com.paramonau.pay4j.command;

public final class PagePath {

    public static final String ERROR_PAGE = "views/error.jsp";
    public static final String ERROR_404_PAGE = "views/404.jsp";
    public static final String SIGN_IN_PAGE = "views/sign_in.jsp";
    public static final String SIGN_UP_PAGE = "views/sign_up.jsp";
    public static final String ABOUT_PAGE = "views/about_page.jsp";
    public static final String PAYMENT_PAGE = "views/payments.jsp";
    public static final String NO_RIGHTS_PAGE = "views/no_rights.jsp";
    public static final String ADMIN_PAGE = "views/admin.jsp";
    public static final String PERSONAL_EDIT_PAGE = "views/personal_edit.jsp";
    public static final String PERSONAL_PAGE = "views/fragments/user_about.jsp";

    public static final String GO_TO_ABOUT_PAGE_COMMAND = "Controller?command=go_to_about_page_command";
    public static final String GO_TO_ACCOUNTS_COMMAND = "Controller?command=go_to_accounts_command";
    public static final String GO_TO_SUCCESS_PAGE_COMMAND = "Controller?command=go_to_success_page_command";
    public static final String GO_TO_ERROR_PAGE_COMMAND = "Controller?command=go_to_error_page_command";
    public static final String GO_TO_SIGN_IN_COMMAND = "Controller?command=go_to_sign_in_command";
    public static final String GO_TO_ADD_ORG_COMMAND = "Controller?command=go_to_add_org_command";
    public static final String GO_TO_EDIT_ORG_COMMAND = "Controller?command=go_to_edit_org_command";
    public static final String GO_TO_ADMIN_ORG_COMMAND = "Controller?command=go_to_admin_org_command";


    private PagePath() {
    }
}
