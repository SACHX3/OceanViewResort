package com.oceanview.controller;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.model.Reservation;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/reservation/billing")
public class ViewReservationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ReservationDAO reservationDAO = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String reservationNumber =
                request.getParameter("reservationNumber");

        if (reservationNumber == null || reservationNumber.isBlank()) {
            response.getWriter().write("{}");
            return;
        }

        try {
            Reservation r =
                    reservationDAO.findForBilling(reservationNumber);

            if (r == null) {
                response.getWriter().write("{}");
                return;
            }

            String json =
                "{"
                + "\"number\":\"" + r.getReservationNumber() + "\","
                + "\"room\":\"" + r.getRoom().getRoomNumber() + "\","
                + "\"type\":\"" + r.getRoom().getRoomType() + "\","
                + "\"price\":" + r.getRoom().getRatePerNight() + ","
                + "\"checkIn\":\"" + r.getCheckIn() + "\","
                + "\"checkOut\":\"" + r.getCheckOut() + "\""
                + "}";

            response.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{}");
        }
    }
}
