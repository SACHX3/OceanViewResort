package com.oceanview.controller;

import com.oceanview.service.ReportService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ReportApiServlet
 * ------------------------------------
 * Provides dashboard statistics as JSON
 */
@WebServlet("/api/reports")
public class ReportApiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ReportService service = new ReportService();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String json = String.format(
            "{"
          + "\"dailyReservations\": %d,"
          + "\"checkInToday\": %d,"
          + "\"checkOutToday\": %d,"
          + "\"occupiedRooms\": %d,"
          + "\"dailyRevenue\": %.0f,"
          + "\"monthlyRevenue\": %.0f"
          + "}",
            service.fetchDailyReservations(),
            service.fetchCheckInToday(),
            service.fetchCheckOutToday(),
            service.fetchOccupiedRooms(),
            service.fetchDailyRevenue(),
            service.fetchMonthlyRevenue()
        );

        resp.getWriter().write(json);
    }
}
