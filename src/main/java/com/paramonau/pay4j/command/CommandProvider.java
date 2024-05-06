package com.paramonau.pay4j.command;

import com.paramonau.pay4j.command.impl.*;
import com.paramonau.pay4j.command.impl.admin.impl.*;
import com.paramonau.pay4j.command.impl.admin.impl.go.*;
import com.paramonau.pay4j.command.impl.auth.go.GoToPersonalEditCommand;
import com.paramonau.pay4j.command.impl.auth.impl.PersonalEditCommand;
import com.paramonau.pay4j.command.impl.go.*;

import java.util.EnumMap;

public class CommandProvider {

    private static CommandProvider instance;
    private static final EnumMap<CommandType, Command> commands = new EnumMap<>(CommandType.class);

    private CommandProvider() {
        commands.put(CommandType.GO_TO_SIGN_IN_COMMAND, new GoToSignInCommand());
        commands.put(CommandType.GO_TO_SIGN_UP_COMMAND, new GoToSignUpCommand());
        commands.put(CommandType.GO_TO_ABOUT_PAGE_COMMAND, new GoToAboutPageCommand());
        commands.put(CommandType.GO_TO_ERROR_PAGE_COMMAND, new GoToErrorPageCommand());
//        commands.put(CommandType.GO_TO_PERSONAL_PAGE_COMMAND, new GoToPersonalPageCommand());
        commands.put(CommandType.GO_TO_PERSONAL_EDIT_COMMAND, new GoToPersonalEditCommand());
//        commands.put(CommandType.GO_TO_ACCOUNTS_COMMAND, new GoToAccountsCommand());
//        commands.put(CommandType.GO_TO_ACCOUNT_COMMAND, new GoToAccountCommand());
//        commands.put(CommandType.GO_TO_ACCOUNT_HISTORY_COMMAND, new GoToAccountHistoryCommand());
//        commands.put(CommandType.GO_TO_USER_HISTORY_COMMAND, new GoToUserHistoryCommand());
//        commands.put(CommandType.GO_TO_ADD_CARD_COMMAND, new GoToAddCardCommand());
        commands.put(CommandType.GO_TO_ADD_ORG_COMMAND, new GoToAddOrgCommand());
        commands.put(CommandType.GO_TO_ADD_ORG_CONFIRM_COMMAND, new GoToAddOrgConfirmCommand());
        commands.put(CommandType.GO_TO_EDIT_ORG_COMMAND, new GoToEditOrgCommand());
        commands.put(CommandType.GO_TO_EDIT_ORG_CONFIRM_COMMAND, new GoToEditOrgConfirmCommand());
//        commands.put(CommandType.GO_TO_CARDS_COMMAND, new GoToCardsCommand());
//        commands.put(CommandType.GO_TO_CARD_COMMAND, new GoToCardCommand());
//        commands.put(CommandType.GO_TO_TOP_UP_ACCOUNT_COMMAND, new GoToTopUpAccountCommand());
//        commands.put(CommandType.GO_TO_PAY_SELECT_CARD_COMMAND, new GoToPaySelectCardCommand());
//        commands.put(CommandType.GO_TO_PAY_SELECT_ACCOUNT_COMMAND, new GoToPaySelectAccountCommand());
//        commands.put(CommandType.GO_TO_PAY_TRANSFER_TO_COMMAND, new GoToPayTransferToCommand());
//        commands.put(CommandType.GO_TO_PAY_TRANSFER_CONFIRM_COMMAND, new GoToPayTransferConfirmCommand());
//        commands.put(CommandType.GO_TO_PAYMENT_CONFIRM_COMMAND, new GoToPaymentConfirmCommand());
        commands.put(CommandType.GO_TO_ADMIN_ACCOUNTS_COMMAND, new GoToAdminAccountsCommand());
        commands.put(CommandType.GO_TO_ADMIN_USER_COMMAND, new GoToAdminUserCommand());
        commands.put(CommandType.GO_TO_ADMIN_USER_ACCOUNTS_COMMAND, new GoToAdminUserAccountsCommand());
        commands.put(CommandType.GO_TO_ADMIN_ACCOUNT_COMMAND, new GoToAdminAccountCommand());
        commands.put(CommandType.GO_TO_ADMIN_USERS_COMMAND, new GoToAdminUsersCommand());
        commands.put(CommandType.GO_TO_ADMIN_ORGS_COMMAND, new GoToAdminOrgsCommand());
        commands.put(CommandType.GO_TO_ADMIN_ORG_COMMAND, new GoToAdminOrgCommand());
        commands.put(CommandType.GO_TO_SUCCESS_PAGE_COMMAND, new GoToSuccessPageCommand());
//        commands.put(CommandType.GO_TO_PAY_SELECT_ORG_COMMAND, new GoToPaySelectOrgCommand());
//        commands.put(CommandType.GRAND_ADMIN_RIGHTS_COMMAND, new GrandAdminRightsCommand());
        commands.put(CommandType.REVOKE_ADMIN_RIGHTS_COMMAND, new RevokeAdminRightsCommand());
//        commands.put(CommandType.UPLOAD_USER_IMAGE_COMMAND, new UploadUserImageCommand());
//        commands.put(CommandType.PAY_COMMAND, new PayCommand());
        commands.put(CommandType.PERSONAL_EDIT_COMMAND, new PersonalEditCommand());
        commands.put(CommandType.SIGN_IN_COMMAND, new SignInCommand());
        commands.put(CommandType.SIGN_UP_COMMAND, new SignUpCommand());
        commands.put(CommandType.LOG_OUT_COMMAND, new LogOutCommand());
//        commands.put(CommandType.ADD_ACCOUNT_COMMAND, new AddAccountCommand());
//        commands.put(CommandType.TOP_UP_ACCOUNT_COMMAND, new TopUpAccountCommand());
//        commands.put(CommandType.BLOCK_ACCOUNT_COMMAND, new BlockAccountCommand());
        commands.put(CommandType.UNLOCK_ACCOUNT_COMMAND, new UnlockAccountCommand());
//        commands.put(CommandType.DELETE_ACCOUNT_COMMAND, new DeleteAccountCommand());
//        commands.put(CommandType.ADD_CARD_COMMAND, new AddCardCommand());
//        commands.put(CommandType.DELETE_CARD_COMMAND, new DeleteCardCommand());
        commands.put(CommandType.BLOCK_ORG_COMMAND, new BlockOrgCommand());
        commands.put(CommandType.UNLOCK_ORG_COMMAND, new UnlockOrgCommand());
        commands.put(CommandType.DELETE_ORG_COMMAND, new DeleteOrgCommand());
        commands.put(CommandType.ADD_ORG_COMMAND, new AddOrgCommand());
        commands.put(CommandType.EDIT_ORG_COMMAND, new EditOrgCommand());
        commands.put(CommandType.CHANGE_LOCALE_COMMAND, new ChangeLocaleCommand());
        commands.put(CommandType.DEFAULT, new DefaultCommand());
    }

    public static CommandProvider getInstance() {
        if (instance == null) {
            instance = new CommandProvider();
        }
        return instance;
    }

    public Command getCommand(String commandName) {
        if (commandName == null) {
            return commands.get(CommandType.DEFAULT);
        }

        CommandType commandType;
        try {
            commandType = CommandType.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandType = CommandType.DEFAULT;
        }

        return commands.get(commandType);
    }
}
