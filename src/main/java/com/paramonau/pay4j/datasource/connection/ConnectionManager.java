package com.paramonau.pay4j.datasource.connection;

import com.paramonau.pay4j.datasource.DBPropertiesUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final Logger logger = LogManager.getLogger(ConnectionManager.class);
    private static final String DB_URL_KEY = "db.url";
    private static final String DB_USER_KEY = "db.user";
    private static final String DB_PASSWORD_KEY = "db.password";
    private static final String DB_DRIVER_KEY = "db.driver";

    static {
        loadDriver();
    }

    private ConnectionManager() {
    }

    static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                DBPropertiesUtil.get(DB_URL_KEY),
                DBPropertiesUtil.get(DB_USER_KEY),
                DBPropertiesUtil.get(DB_PASSWORD_KEY)
        );
    }

    private static void loadDriver() {
        String driverName = null;
        try {
            driverName = DBPropertiesUtil.get(DB_DRIVER_KEY);
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't register driver: " + driverName, e);
        }
    }
}
