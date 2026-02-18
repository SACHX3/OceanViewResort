package com.oceanview.controller;

import com.oceanview.dao.UserDAO;
import com.oceanview.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/users")
public class UsersApiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<User> users = userDAO.findAllUsers();

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < users.size(); i++) {

                User u = users.get(i);

                json.append("{")
                    .append("\"id\":").append(u.getUserId()).append(",")
                    .append("\"username\":\"").append(u.getUsername()).append("\",")
                    .append("\"fullName\":\"").append(u.getFullName()).append("\",")
                    .append("\"role\":\"").append(u.getRole()).append("\"")
                    .append("}");

                if (i < users.size() - 1) json.append(",");
            }
            json.append("]");

            resp.getWriter().write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("[]");
        }
    }
}
