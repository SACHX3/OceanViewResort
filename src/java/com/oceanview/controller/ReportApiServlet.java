package com.oceanview.controller;

import com.oceanview.service.ReportService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

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

        Map<String, Double> revenue7 = service.fetchLast7DaysRevenue();
        Map<String, Integer> occupancy7 = service.fetchLast7DaysOccupancy();

        StringBuilder revLabels = new StringBuilder("[");
        StringBuilder revValues = new StringBuilder("[");

        for (var e : revenue7.entrySet()) {
            revLabels.append("\"").append(e.getKey()).append("\",");
            revValues.append(e.getValue()).append(",");
        }

        if (revLabels.length() > 1) {
            revLabels.setLength(revLabels.length() - 1);
            revValues.setLength(revValues.length() - 1);
        }

        revLabels.append("]");
        revValues.append("]");

        StringBuilder occValues = new StringBuilder("[");
        for (var e : occupancy7.entrySet()) {
            occValues.append(e.getValue()).append(",");
        }
        if (occValues.length() > 1)
            occValues.setLength(occValues.length() - 1);
        occValues.append("]");

        String json = String.format(
            "{"
          + "\"dailyReservations\": %d,"
          + "\"checkInToday\": %d,"
          + "\"checkOutToday\": %d,"
          + "\"occupiedRooms\": %d,"
          + "\"availableRooms\": %d,"
          + "\"dailyRevenue\": %.0f,"
          + "\"monthlyRevenue\": %.0f,"
          + "\"revenueLabels\": %s,"
          + "\"revenueChart\": %s,"
          + "\"occupancyTrend\": %s"
          + "}",
            service.fetchDailyReservations(),
            service.fetchCheckInToday(),
            service.fetchCheckOutToday(),
            service.fetchOccupiedRooms(),
            service.fetchAvailableRooms(),
            service.fetchDailyRevenue(),
            service.fetchMonthlyRevenue(),
            revLabels.toString(),
            revValues.toString(),
            occValues.toString()
        );

        resp.getWriter().write(json);
    }
}
