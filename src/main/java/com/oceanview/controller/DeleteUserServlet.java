package com.oceanview.controller;

import com.oceanview.dao.UserDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws IOException {

        try {
            int userId =
                Integer.parseInt(req.getParameter("userId"));

            userDAO.deleteUser(userId);

            resp.sendRedirect("manageUsers.html?success=true");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("manageUsers.html?error=true");
        }
    }
}
