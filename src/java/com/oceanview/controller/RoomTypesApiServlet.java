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

    private static final long serialVersionUID = 1L;
    private final RoomDAO roomDAO = new RoomDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("application/json");

        try {
            List<Room> types = roomDAO.findAllRoomTypes();

            StringBuilder json = new StringBuilder("[");
            for (Room r : types) {
                json.append("{")
                    .append("\"id\":").append(r.getRoomTypeId()).append(",")
                    .append("\"name\":\"").append(r.getRoomType()).append("\",")
                    .append("\"price\":").append(r.getRatePerNight())
                    .append("},");
            }
            if (json.length() > 1) json.setLength(json.length() - 1);
            json.append("]");

            res.getWriter().write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().write("[]");
        }
    }
}
