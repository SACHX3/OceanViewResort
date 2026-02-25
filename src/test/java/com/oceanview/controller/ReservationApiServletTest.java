package com.oceanview.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReservationApiServletTest {

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    @Test
    void reservationApiServletInstantiationTest() {

        System.out.println("\nRunning: reservationApiServletInstantiationTest");

        try {
            ReservationApiServlet servlet = new ReservationApiServlet();
            assertNotNull(servlet);

            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (AssertionError e) {
            System.out.println(RED + "Result: FAIL" + RESET);
            throw e;
        }
    }
}