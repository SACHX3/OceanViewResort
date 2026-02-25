package com.oceanview.service;

import com.oceanview.dao.UserDAO;
import com.oceanview.model.User;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public void createUser(String username, String fullName,
                           String password, String role) throws Exception {

        if (userDAO.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setFullName(fullName);
        user.setPasswordHash(hash(password));
        user.setRole(role);

        userDAO.addUser(user);
    }

    public List<User> getAllUsers() throws Exception {
        return userDAO.findAllUsers();
    }

    public void updateUser(int id, String fullName, String role) throws Exception {

        User u = new User();
        u.setUserId(id);
        u.setFullName(fullName);
        u.setRole(role);

        userDAO.updateUser(u);
    }

    public void deleteUser(int id) throws Exception {
        userDAO.deleteUser(id);
    }

    private String hash(String password) throws Exception {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
