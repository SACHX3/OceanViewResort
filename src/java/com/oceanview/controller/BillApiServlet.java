package com.oceanview.controller;

import com.oceanview.model.Bill;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * BillApiServlet
 * ---------------------------------------
 * Provides bill data to billResult.html
 */
@WebServlet("/api/bill")
public class BillApiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        HttpSession session = request.getSession(false);
        Bill bill = (session != null)
                ? (Bill) session.getAttribute("bill")
                : null;

        if (bill == null) {
            response.getWriter().write("{}");
            return;
        }

        String json = String.format(
            "{"
            + "\"reservationNumber\":\"%s\","
            + "\"guestName\":\"%s\","
            + "\"roomNumber\":\"%s\","
            + "\"roomType\":\"%s\","
            + "\"pricePerNight\":%.2f,"
            + "\"checkIn\":\"%s\","
            + "\"checkOut\":\"%s\","
            + "\"nights\":%d,"
            + "\"totalAmount\":%.2f"
            + "}",
            bill.getReservationNumber(),
            bill.getGuestName(),
            bill.getRoomNumber(),
            bill.getRoomType(),
            bill.getPricePerNight(),
            bill.getCheckIn(),
            bill.getCheckOut(),
            bill.getNights(),
            bill.getTotalAmount()
        );

        response.getWriter().write(json);
    }
}
