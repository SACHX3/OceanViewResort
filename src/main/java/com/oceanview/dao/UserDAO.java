package com.oceanview.dao;

import com.oceanview.model.User;
import com.oceanview.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /* ================= CREATE ================= */
    public void addUser(User user) throws SQLException {

        String sql =
            "INSERT INTO users (username, full_name, password_hash, role) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getRole());

            ps.executeUpdate();
        }
    }

    /* ================= READ ALL ================= */
    public List<User> findAllUsers() throws SQLException {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users ORDER BY role, username";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setFullName(rs.getString("full_name"));
                u.setRole(rs.getString("role"));
                users.add(u);
            }
        }
        return users;
    }

    /* ================= UPDATE ================= */
    public void updateUser(User user) throws SQLException {

        String sql =
            "UPDATE users SET full_name = ?, role = ? WHERE user_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getRole());
            ps.setInt(3, user.getUserId());

            ps.executeUpdate();
        }
    }

    /* ================= DELETE ================= */
    public void deleteUser(int userId) throws SQLException {

        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    /* ================= AUTH ================= */
    public User findByUsername(String username) throws SQLException {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setFullName(rs.getString("full_name"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setRole(rs.getString("role"));
                return u;
            }
        }
        return null;
    }
}
