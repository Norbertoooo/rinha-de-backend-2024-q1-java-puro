package com.vitu.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Database {

    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource datasource;
    private static final String usernameDefault = "java_puro_db";
    private static final String passwordDefault = "java_puro_db";
    private static final String urlDefault = "jdbc:postgresql://localhost:5432/crebito";
    private static final int poolSizeDefault = 10;

    static {
        config.setMaximumPoolSize(getMaxPoolSize());
        config.setMinimumIdle(5);
        config.setIdleTimeout(30000);
        config.setJdbcUrl(getdbcUrl());
        config.setAutoCommit(false);
        config.setUsername(getDatabaseUsername());
        config.setPassword(getDatabasePassword());
        datasource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());
        logger.info("Criando conexão com banco de dados");
        return datasource.getConnection();
    }

    private static String getDatabaseUsername() {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());
        String databaseUsername = System.getenv("DATABASE_USERNAME");
        return Objects.isNull(databaseUsername) ? usernameDefault : databaseUsername;
    }

    private static String getDatabasePassword() {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());
        String databasePassword = System.getenv("DATABASE_PASSWORD");
        return Objects.isNull(databasePassword) ? passwordDefault : databasePassword;
    }

    private static String getdbcUrl() {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());
        String databaseUrl = System.getenv("DATABASE_URL");
        return Objects.isNull(databaseUrl) ? urlDefault : databaseUrl;
    }

    private static int getMaxPoolSize() {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());
        String databaseUrl = System.getenv("MAX_POOL_SIZE");
        return Objects.isNull(databaseUrl) ? poolSizeDefault : Integer.parseInt(databaseUrl);
    }

    public static void closeConnection(Connection connection, int clienteId) throws SQLException {
        logger.debug("TheadId: {} - fechando conexão com banco de dados - clienteId: {}", Thread.currentThread().threadId(), clienteId);
        connection.close();
    }

}
