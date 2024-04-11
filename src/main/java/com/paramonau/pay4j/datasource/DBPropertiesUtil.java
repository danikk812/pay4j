package com.paramonau.pay4j.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class DBPropertiesUtil {

    private static final Properties properties = new Properties();
    private static final String RESOURCE = "db.properties";

    static {
        loadProperties();
    }
    private DBPropertiesUtil() {
    }

    private static void loadProperties() {
        try (InputStream inputStream = DBPropertiesUtil.class.getClassLoader().getResourceAsStream(RESOURCE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Can't load db properties: ", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
