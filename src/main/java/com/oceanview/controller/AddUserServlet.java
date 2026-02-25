package com.oceanview.controller;

import com.oceanview.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * AddUserServlet
 * ------------------------------------
 * Handles ADMIN-only user creation
 * with success / error validation.
 */
@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        System.out.println("🔥 AddUserServlet HIT");

        String username = req.getParameter("username");
        String fullName = req.getParameter("fullName");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        /* ===== BASIC VALIDATION ===== */
        if (username == null || username.isBlank()
                || fullName == null || fullName.isBlank()
                || password == null || password.isBlank()
                || role == null || role.isBlank()) {

            resp.sendRedirect("manageUsers.html?error=true");
            return;
        }

        try {
            /* ===== BUSINESS LOGIC ===== */
            userService.createUser(
                    username.trim(),
                    fullName.trim(),
                    password.trim(),
                    role.trim()
            );

            /* ===== SUCCESS ===== */
            resp.sendRedirect("manageUsers.html?success=true");

        } catch (SQLException e) {

            /* ===== DUPLICATE USERNAME ===== */
            if (e.getMessage() != null
                    && e.getMessage().toLowerCase().contains("duplicate")) {

                resp.sendRedirect("manageUsers.html?error=duplicate");
            } else {
                resp.sendRedirect("manageUsers.html?error=true");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("manageUsers.html?error=true");
        }
    }
}
