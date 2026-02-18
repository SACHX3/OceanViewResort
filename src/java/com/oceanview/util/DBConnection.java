package com.oceanview.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection
 * ----------------------------------------------------
 * Singleton for database configuration.
 * Provides a NEW JDBC Connection per request.
 *
 * Java 17 | Tomcat 9 | MySQL 8.x
 */
public class DBConnection {

    private static DBConnection instance;

    private static final String URL =
            "jdbc:mysql://localhost:3306/ocean_view_reservation"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Private constructor loads JDBC driver once.
     */
    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL JDBC Driver loaded");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ MySQL JDBC Driver not found", e);
        }
    }

    /**
     * Returns singleton instance.
     */
    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    /**
     * ALWAYS returns a NEW, OPEN connection.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
