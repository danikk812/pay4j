package com.paramonau.pay4j.datasource.connection;

import com.paramonau.pay4j.datasource.DBPropertiesUtil;
import com.paramonau.pay4j.exception.DAOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public final class ConnectionPool {

    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final ConnectionPool instance = new ConnectionPool();

    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static final int DEFAULT_POOL_SIZE = 8;

    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> activeConnections;

    private ConnectionPool() {
        initPool();
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public void initPool() {
        String poolSize = DBPropertiesUtil.get(POOL_SIZE_KEY);
        int size = (poolSize == null) ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);

        activeConnections = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);
        freeConnections = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);

        for (int i = 0; i < size; i++) {
            try {
                Connection connection = ConnectionManager.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
            } catch (SQLException e) {
                logger.error("SQL issue while creating connection: ", e);
            }
        }
        if (freeConnections.isEmpty()) {
            logger.fatal("Can't create connections, empty pool");
            throw new RuntimeException("Can't create connections, empty pool");
        }
    }

    public Connection getConnection() throws DAOException {
        ProxyConnection proxyConnection;
        proxyConnection = freeConnections.poll();

        if (proxyConnection == null) {
            throw new DAOException("No free connection");
        }

        activeConnections.add(proxyConnection);

        return proxyConnection;
    }

    public void releaseConnection(Connection connection) {
        if (connection.getClass() == ProxyConnection.class) {
            if (activeConnections.remove(connection)) {
                freeConnections.add((ProxyConnection) connection);
                logger.debug("Connection has been released: " + connection);
            }
        } else {
            logger.error("Wild connection is detected, can't release");
            throw new RuntimeException("Wild connection is detected, can't release: " + connection);
        }
    }

    public void clearConnectionPool() throws DAOException {
        closeConnectionQueue(freeConnections);
        closeConnectionQueue(activeConnections);
    }

    private void closeConnectionQueue(BlockingQueue<ProxyConnection> connectionQueue) throws DAOException {
        ProxyConnection proxyConnection;

        while ((proxyConnection = connectionQueue.poll()) != null) {
            try {
                proxyConnection.reallyClose();
            } catch (SQLException e) {
                throw new DAOException("Closing connection problem", e);
            }
        }
    }
}
