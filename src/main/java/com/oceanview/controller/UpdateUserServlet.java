package com.oceanview.controller;

import com.oceanview.dao.UserDAO;
import com.oceanview.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws IOException {

        try {
            int userId =
                Integer.parseInt(req.getParameter("userId"));

            String fullName =
                req.getParameter("fullName");

            String role =
                req.getParameter("role");
            
            User user = new User();
            user.setUserId(userId);
            user.setFullName(fullName);
            user.setRole(role);

            userDAO.updateUser(user);

            resp.sendRedirect("manageUsers.html?success=true");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("manageUsers.html?error=true");
        }
    }
}
