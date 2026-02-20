package com.oceanview.controller;

import com.oceanview.service.ReservationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CheckOutReservationServlet
 * --------------------------------------------
 * Handles manual guest check-out
 */
@WebServlet("/checkoutReservation")
public class CheckOutReservationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // ✅ FIXED

    private final ReservationService service =
            new ReservationService();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        String reservationNumber =
                request.getParameter("reservationNumber");

        if (reservationNumber == null || reservationNumber.isBlank()) {
            response.getWriter().write("{\"success\":false}");
            return;
        }

        try {
            service.checkOutReservation(reservationNumber);

            response.getWriter().write("{\"success\":true}");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\":false}");
        }
    }
}
