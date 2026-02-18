package com.oceanview.controller;

import com.oceanview.model.Reservation;
import com.oceanview.service.ReservationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/reservation")
public class ReservationApiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ReservationService service = new ReservationService();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String number = req.getParameter("reservationNumber");

        if (number == null || number.isBlank()) {
            resp.getWriter().write("{}");
            return;
        }

        try {
            Reservation r = service.getReservation(number);

            if (r == null) {
                resp.getWriter().write("{}");
                return;
            }

            String json =
                "{"
              + "\"number\":\"" + r.getReservationNumber() + "\","
              + "\"guest\":\"" + r.getGuestName() + "\","
              + "\"room\":\"" + r.getRoom().getRoomNumber() + "\","
              + "\"roomType\":\"" + r.getRoom().getRoomType() + "\","
              + "\"price\":\"" + r.getRoom().getRatePerNight() + "\","
              + "\"checkIn\":\"" + r.getCheckIn() + "\","
              + "\"checkOut\":\"" + (r.getCheckOut() != null ? r.getCheckOut() : "") + "\","
              + "\"status\":\"" + r.getStatus() + "\""
              + "}";

            resp.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{}");
        }
    }
}
