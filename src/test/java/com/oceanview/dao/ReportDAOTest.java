package com.oceanview.dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReportDAOTest {

    private final ReportDAO reportDAO = new ReportDAO();

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    @Test
    void dailyReservationsShouldNotBeNegative() {

        System.out.println("\nRunning: dailyReservationsShouldNotBeNegative");

        try {
            int count = reportDAO.dailyReservations();

            if (count < 0) {
                System.out.println(RED + "Result: FAIL (Negative reservation count)" + RESET);
                fail("Daily reservations should not be negative");
            }

            System.out.println("Daily Reservations: " + count);
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    void monthlyRevenueShouldNotBeNegative() {

        System.out.println("\nRunning: monthlyRevenueShouldNotBeNegative");

        try {
            double revenue = reportDAO.monthlyRevenue();

            if (revenue < 0) {
                System.out.println(RED + "Result: FAIL (Negative revenue)" + RESET);
                fail("Monthly revenue should not be negative");
            }

            System.out.println("Monthly Revenue: " + revenue);
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Exception: " + e.getMessage());
        }
    }
}