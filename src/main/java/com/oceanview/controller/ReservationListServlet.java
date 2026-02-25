package com.oceanview.controller;

import com.oceanview.model.Reservation;
import com.oceanview.service.ReservationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/reservations")
public class ReservationListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ReservationService service = new ReservationService();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<Reservation> list = service.getAllReservations();

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {

                Reservation r = list.get(i);

                json.append("{")
                    .append("\"number\":\"").append(r.getReservationNumber()).append("\",")
                    .append("\"guest\":\"").append(r.getGuestName()).append("\",")
                    .append("\"room\":\"").append(r.getRoom().getRoomNumber()).append("\",")
                    .append("\"roomType\":\"").append(r.getRoom().getRoomType()).append("\",")
                    .append("\"checkIn\":\"").append(r.getCheckIn()).append("\",")
                    .append("\"checkOut\":\"")
                        .append(r.getCheckOut() != null ? r.getCheckOut() : "")
                        .append("\",")
                    .append("\"status\":\"").append(r.getStatus()).append("\"")
                    .append("}");

                if (i < list.size() - 1) json.append(",");
            }
            json.append("]");

            resp.getWriter().write(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("[]");
        }
    }
}
