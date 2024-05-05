package com.paramonau.pay4j.validator;

import com.paramonau.pay4j.entity.Card;
import com.paramonau.pay4j.util.RegexpPropertiesUtil;

public final class CardValidator {

    private static final String REGEXP_CARD_NUMBER = "regexp.card_number";
    private static final String REGEXP_OWNER_NAME = "regexp.card_ownername";
    private static final String REGEXP_CARD_CVV = "regexp.card_cvv";

    private static final RegexpPropertiesUtil regexpPropertyUtil = RegexpPropertiesUtil.getInstance();

    private CardValidator() {
    }

    public static boolean validate(Card card) {
        String number = card.getNumber();
        String ownerName = card.getOwnerName();
        String cvv = String.valueOf(card.getCvv());

        return validateNumber(number) && validateOwnerName(ownerName) && validateCvv(cvv);
    }

    private static boolean validateNumber(String number) {
        return matches(number,regexpPropertyUtil.get(REGEXP_CARD_NUMBER));
    }

    private static boolean validateOwnerName(String ownerName) {
        return matches(ownerName,regexpPropertyUtil.get(REGEXP_OWNER_NAME));
    }

    private static boolean validateCvv(String cvv) {
        return matches(cvv, regexpPropertyUtil.get(REGEXP_CARD_CVV));
    }

    private static boolean matches(String text, String regex) {
        return text != null && text.matches(regex);
    }
}
