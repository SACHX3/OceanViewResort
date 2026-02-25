package com.oceanview.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    private final ReservationService service = new ReservationService();

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    @Test
    void getReservation_shouldThrowException_whenInputIsNull() {

        System.out.println("Running: getReservation_shouldThrowException_whenInputIsNull");

        try {
            assertThrows(IllegalArgumentException.class,
                    () -> service.getReservation(null));

            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (AssertionError e) {
            System.out.println(RED + "Result: FAIL" + RESET);
            throw e;
        }
    }

    @Test
    void checkOutReservation_shouldThrowException_whenInputIsNull() {

        System.out.println("Running: checkOutReservation_shouldThrowException_whenInputIsNull");

        try {
            assertThrows(IllegalArgumentException.class,
                    () -> service.checkOutReservation(null));

            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (AssertionError e) {
            System.out.println(RED + "Result: FAIL" + RESET);
            throw e;
        }
    }
}