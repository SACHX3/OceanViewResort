package com.oceanview.controller;

import com.oceanview.dao.RoomDAO;
import com.oceanview.model.Room;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/available-rooms")
public class AvailableRoomsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final RoomDAO roomDAO = new RoomDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Room> rooms = roomDAO.findAvailableRooms();

            StringBuilder json = new StringBuilder();
            json.append("[");

            for (Room r : rooms) {
                json.append("{")
                    .append("\"id\":").append(r.getRoomId()).append(",")
                    .append("\"number\":\"").append(r.getRoomNumber()).append("\",")
                    .append("\"type\":\"").append(r.getRoomType()).append("\",")
                    .append("\"price\":").append(r.getRatePerNight())
                    .append("},");
            }

            if (!rooms.isEmpty()) {
                json.setLength(json.length() - 1); // remove last comma
            }

            json.append("]");
            response.getWriter().write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("[]");
        }
    }
}
