package com.oceanview.service;

import com.oceanview.dao.UserDAO;
import com.oceanview.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * AuthService
 * Handles authentication for ADMIN and STAFF users.
 */
public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    public User authenticate(String username, String password) {

        if (username == null || password == null) return null;

        username = username.trim();
        password = password.trim();

        if (username.isEmpty() || password.isEmpty()) return null;

        try {
            User user = userDAO.findByUsername(username);

            if (user == null ||
                user.getPasswordHash() == null ||
                user.getRole() == null) {
                return null;
            }

            String hashedInputPassword = hashPassword(password);

            if (!hashedInputPassword.equalsIgnoreCase(user.getPasswordHash())) {
                return null;
            }

            String role = user.getRole().toUpperCase();
            if (!role.equals("ADMIN") && !role.equals("STAFF")) {
                return null;
            }

            return user;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String hashPassword(String password) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes =
                    digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not supported", e);
        }
    }
}
