package com.oceanview.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection
 * ----------------------------------------------------
 * Singleton for database configuration.
 * Supports:
 *  - Local Development
 *  - GitHub CI (Environment Variables)
 *
 * Java 17 | Tomcat 9 | MySQL 8.x
 */
public class DBConnection {

    private static DBConnection instance;

    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {

        // Read from environment (GitHub CI compatible)
        String host = System.getenv().getOrDefault("DB_HOST", "localhost");
        String port = System.getenv().getOrDefault("DB_PORT", "3306");
        String db   = System.getenv().getOrDefault("DB_NAME", "ocean_view_reservation");
        String user = System.getenv().getOrDefault("DB_USER", "root");
        String pass = System.getenv().getOrDefault("DB_PASS", "");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + db
                + "?useSSL=false"
                + "&allowPublicKeyRetrieval=true"
                + "&serverTimezone=Asia/Colombo";

        return DriverManager.getConnection(url, user, pass);
    }
}