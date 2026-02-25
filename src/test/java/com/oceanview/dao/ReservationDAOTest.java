package com.oceanview.dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReservationDAOTest {

    private final ReservationDAO dao = new ReservationDAO();

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    @Test
    void findAllReservationsTest() {

        System.out.println("\nRunning: findAllReservationsTest");

        try {
            var list = dao.findAllReservations();

            if (list == null) {
                System.out.println(RED + "Result: FAIL (Reservation list is null)" + RESET);
                fail("Reservation list should not be null");
            }

            System.out.println("Reservations Found: " + list.size());
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    void findReservationByNumberTest() {

        System.out.println("\nRunning: findReservationByNumberTest");

        try {
            var r = dao.findByReservationNumber("INVALID");

            if (r != null) {
                System.out.println(RED + "Result: FAIL (Expected null for invalid number)" + RESET);
                fail("Invalid reservation number should return null");
            }

            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Exception: " + e.getMessage());
        }
    }
}