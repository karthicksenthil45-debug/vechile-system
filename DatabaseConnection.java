package com.vcet.vehicleservice.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centralized JDBC Database Connection Factory.
 * Supports SQLite (embedded default) and MySQL 8.0+ server.
 * Implements 25ITT32 Unit 4 (Database Connection).
 */
public class DatabaseConnection {

    // Database Mode Selector: "SQLITE" or "MYSQL"
    private static final String DB_TYPE = "SQLITE";

    // SQLite Configuration
    private static final String SQLITE_RELATIVE_PATH = "database/vehicle_service.db";

    // MySQL Configuration
    private static final String MYSQL_HOST = "localhost";
    private static final int MYSQL_PORT = 3306;
    private static final String MYSQL_DB = "vehicle_service_db";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASS = "admin";

    private static Connection connection = null;

    private DatabaseConnection() {
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        try {
            if ("SQLITE".equalsIgnoreCase(DB_TYPE)) {
                // Dynamically resolve relative path whether running from root, bin, or IDE
                File dbFile = new File(SQLITE_RELATIVE_PATH);
                if (!dbFile.exists()) {
                    dbFile = new File("../" + SQLITE_RELATIVE_PATH);
                }
                if (!dbFile.exists()) {
                    dbFile = new File("../../" + SQLITE_RELATIVE_PATH);
                }

                String sqliteUrl = "jdbc:sqlite:" + dbFile.getAbsolutePath();
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(sqliteUrl);
            } else {
                String mysqlUrl = String.format("jdbc:mysql://%s:%d/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                        MYSQL_HOST, MYSQL_PORT, MYSQL_DB);
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(mysqlUrl, MYSQL_USER, MYSQL_PASS);
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found. Please ensure the appropriate JDBC jar is in classpath: " + e.getMessage());
        }

        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
