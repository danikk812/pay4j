package com.paramonau.pay4j.util;

import java.util.Locale;
import java.util.ResourceBundle;

public final class RegexpPropertiesUtil {

    private static final RegexpPropertiesUtil instance = new RegexpPropertiesUtil();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("regexp", Locale.getDefault());

    private RegexpPropertiesUtil() {
    }

    public static RegexpPropertiesUtil getInstance() {
        return instance;
    }

    public String get(String key) {
        return resourceBundle.getString(key);
    }
}
