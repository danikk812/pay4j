package com.paramonau.pay4j.validator;

import java.math.BigDecimal;

public final class PaymentValidator {

    private PaymentValidator() {
    }

    public static boolean validateAmount(BigDecimal amount) {
        return greaterThanZero(amount);
    }

    private static boolean greaterThanZero(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }
}
