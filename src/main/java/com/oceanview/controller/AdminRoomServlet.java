package com.oceanview.controller;

import com.oceanview.model.Room;
import com.oceanview.service.AdminRoomService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/rooms")
public class AdminRoomServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final AdminRoomService service = new AdminRoomService();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {

            List<Room> rooms = service.getAllRooms();

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < rooms.size(); i++) {

                Room r = rooms.get(i);

                json.append("{")
                        .append("\"id\":").append(r.getRoomId()).append(",")
                        .append("\"number\":\"").append(r.getRoomNumber()).append("\",")
                        .append("\"type\":\"").append(r.getRoomType()).append("\",")
                        .append("\"price\":").append(r.getRatePerNight()).append(",")
                        .append("\"status\":\"").append(r.getStatus()).append("\"")
                        .append("}");

                if (i < rooms.size() - 1)
                    json.append(",");
            }

            json.append("]");
            resp.getWriter().write(json.toString());

        } catch (Exception e) {
            resp.getWriter().write("[]");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        try {

            if ("add".equals(action)) {

                String typeName = req.getParameter("typeName");
                double price = Double.parseDouble(req.getParameter("price"));
                String roomNumber = req.getParameter("roomNumber");

                service.addRoom(typeName, price, roomNumber);

                resp.getWriter().write(
                        "{\"success\":true,\"message\":\"Room added successfully\"}"
                );
            }

            else if ("update".equals(action)) {

                int roomId = Integer.parseInt(req.getParameter("roomId"));
                String typeName = req.getParameter("typeName");
                double price = Double.parseDouble(req.getParameter("price"));
                String roomNumber = req.getParameter("roomNumber");

                service.updateRoom(roomId, typeName, price, roomNumber);

                resp.getWriter().write(
                        "{\"success\":true,\"message\":\"Room updated successfully\"}"
                );
            }

            else if ("delete".equals(action)) {

                int roomId = Integer.parseInt(req.getParameter("roomId"));

                service.deleteRoom(roomId);

                resp.getWriter().write(
                        "{\"success\":true,\"message\":\"Room deleted successfully\"}"
                );
            }

            else {
                resp.getWriter().write(
                        "{\"success\":false,\"message\":\"Invalid action\"}"
                );
            }

        } catch (Exception e) {

            String safeMessage =
                    e.getMessage() == null ? "Server error"
                            : e.getMessage().replace("\"", "");

            resp.getWriter().write(
                    "{\"success\":false,\"message\":\"" +
                            safeMessage +
                            "\"}"
            );
        }
    }
}