package com.oceanview.controller;

import com.oceanview.service.ReportService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

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

        try {

            Map<String, Double> revenue7 = service.fetchLast30DaysRevenue();
            Map<String, Integer> occupancy7 = service.fetchLast30DaysOccupancy();
            Map<String, Integer> checkInTrend = service.fetchLast30DaysCheckInTrend();
            Map<String, Integer> checkOutTrend = service.fetchLast30DaysCheckOutTrend();
            Map<String, Integer> bookingTrend = service.fetchLast30DaysBookingActivity();

            /* ================= CHECK-IN TREND ================= */

            StringBuilder checkInValues = new StringBuilder("[");
            for (Map.Entry<String, Integer> e : checkInTrend.entrySet()) {
                checkInValues.append(e.getValue()).append(",");
            }

            if (checkInValues.length() > 1)
                checkInValues.setLength(checkInValues.length()-1);

            checkInValues.append("]");


            /* ================= CHECK-OUT TREND ================= */

            StringBuilder checkOutValues = new StringBuilder("[");
            for (Map.Entry<String, Integer> e : checkOutTrend.entrySet()) {
                checkOutValues.append(e.getValue()).append(",");
            }

            if (checkOutValues.length() > 1)
                checkOutValues.setLength(checkOutValues.length()-1);

            checkOutValues.append("]");


            /* ================= REVENUE TREND ================= */

            StringBuilder revLabels = new StringBuilder("[");
            StringBuilder revValues = new StringBuilder("[");

            for (Map.Entry<String, Double> e : revenue7.entrySet()) {

                revLabels.append("\"").append(e.getKey()).append("\",");
                revValues.append(e.getValue()).append(",");
            }

            if (revLabels.length() > 1) {
                revLabels.setLength(revLabels.length() - 1);
                revValues.setLength(revValues.length() - 1);
            }

            revLabels.append("]");
            revValues.append("]");


            /* ================= OCCUPANCY TREND ================= */

            StringBuilder occValues = new StringBuilder("[");

            for (Map.Entry<String, Integer> e : occupancy7.entrySet()) {
                occValues.append(e.getValue()).append(",");
            }

            if (occValues.length() > 1)
                occValues.setLength(occValues.length() - 1);

            occValues.append("]");


            /* ================= booking activity 30days ================= */

            StringBuilder bookingValues = new StringBuilder("[");

            for (Map.Entry<String, Integer> e : bookingTrend.entrySet()) {
                bookingValues.append(e.getValue()).append(",");
            }

            if (bookingValues.length() > 1)
                bookingValues.setLength(bookingValues.length()-1);

            bookingValues.append("]");


            /* ================= JSON RESPONSE ================= */

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
                            + "\"checkInTrend\": %s,"
                            + "\"checkOutTrend\": %s,"
                            + "\"bookingTrend\": %s,"
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
                    checkInValues.toString(),
                    checkOutValues.toString(),
                    bookingValues.toString(),
                    occValues.toString()
            );

            resp.getWriter().write(json);

        } catch (Exception e) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            resp.getWriter().write(
                    "{\"error\":\"Failed to generate report\"}"
            );
        }
    }
}