package com.oceanview.controller;

import com.oceanview.service.AuthService;
import com.oceanview.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LoginServlet
 * --------------------------------------------
 * Handles user login and role-based redirection.
 * Provides validation feedback for invalid credentials.
 *
 * Java 17 | Tomcat 9
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("🔥 LoginServlet HIT");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = authService.authenticate(username, password);

        if (user != null) {

            // ---------- Create session ----------
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedUser", user);

            // ---------- Role-based redirect ----------
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect("adminPanel.html");
            } else {
                response.sendRedirect("dashboard.html");
            }

        } else {
            // ❌ Invalid username or password
            response.sendRedirect("login.html?error=invalid");
        }
    }
}
