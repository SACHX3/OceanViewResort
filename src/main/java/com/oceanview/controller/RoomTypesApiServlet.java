package com.oceanview.controller;

import com.oceanview.dao.RoomDAO;
import com.oceanview.model.Room;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/roomTypes")
public class RoomTypesApiServlet extends HttpServlet {

    private final RoomDAO roomDAO = new RoomDAO();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
            throws IOException {

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        try {

            List<Room> types = roomDAO.findAllRoomTypes();

            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < types.size(); i++) {

                Room r = types.get(i);

                json.append("{")
                        .append("\"id\":").append(r.getRoomTypeId()).append(",")
                        .append("\"name\":\"").append(r.getRoomType()).append("\"")
                        .append("}");

                if (i < types.size() - 1)
                    json.append(",");
            }

            json.append("]");

            res.getWriter().write(json.toString());

        } catch (Exception e) {

            res.getWriter().write("[]");
        }
    }
}