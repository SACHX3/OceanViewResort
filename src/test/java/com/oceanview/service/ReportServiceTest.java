package com.oceanview.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {

    private final ReportService service = new ReportService();

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    @Test
    void dailyRevenueTest() {

        System.out.println("\nRunning: dailyRevenueTest");

        try {
            double revenue = service.fetchDailyRevenue();

            if (revenue < 0) {
                System.out.println(RED + "Result: FAIL (Revenue is negative)" + RESET);
                fail("Daily revenue should not be negative");
            }

            System.out.println("Daily Revenue: " + revenue);
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void fetchAvailableRoomsTest() {

        System.out.println("\nRunning: fetchAvailableRoomsTest");

        try {
            int rooms = service.fetchAvailableRooms();

            if (rooms < 0) {
                System.out.println(RED + "Result: FAIL (Rooms count is negative)" + RESET);
                fail("Available rooms should not be negative");
            }

            System.out.println("Available Rooms: " + rooms);
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void fetchMonthlyRevenueTest() {

        System.out.println("\nRunning: fetchMonthlyRevenueTest");

        try {
            double revenue = service.fetchMonthlyRevenue();

            if (revenue < 0) {
                System.out.println(RED + "Result: FAIL (Revenue is negative)" + RESET);
                fail("Monthly revenue should not be negative");
            }

            System.out.println("Monthly Revenue: " + revenue);
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}