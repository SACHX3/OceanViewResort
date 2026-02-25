package com.oceanview.controller;

import com.oceanview.model.Bill;
import com.oceanview.service.BillingService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/calculateBill")
public class CalculateBillServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final BillingService billingService =
            new BillingService();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        String reservationNumber =
            request.getParameter("reservationNumber");

        boolean recordRevenue =
            "true".equals(request.getParameter("recordRevenue"));

        try {
            Bill bill =
                billingService.calculateBill(
                    reservationNumber,
                    recordRevenue
                );

            // STORE FULL BILL IN SESSION
            HttpSession session = request.getSession();
            session.setAttribute("bill", bill);

            response.sendRedirect("billResult.html");

        } catch (Exception e) {

            HttpSession session = request.getSession();
            session.setAttribute(
                "billError",
                "Invalid reservation number"
            );

            response.sendRedirect("bill.html");
        }
    }
}
