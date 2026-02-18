package com.oceanview.controller;

import com.oceanview.service.ReservationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/deleteReservation")
public class DeleteReservationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ReservationService service = new ReservationService();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        try {
            String reservationNumber =
                request.getParameter("reservationNumber");

            service.deleteReservation(reservationNumber);

            response.setContentType("application/json");
            response.getWriter().write("{\"success\":true}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"success\":false}");
        }
    }
}
