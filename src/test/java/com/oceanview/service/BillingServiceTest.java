package com.oceanview.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BillingServiceTest {

    private final BillingService service = new BillingService();

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    @Test
    void invalidBillCalculationTest() {

        System.out.println("\nRunning: invalidBillCalculationTest");

        try {
            service.calculateBill(null, false);

            System.out.println(RED + "Result: FAIL (No Exception Thrown)" + RESET);
            fail("Expected IllegalArgumentException was not thrown");

        } catch (IllegalArgumentException e) {
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Wrong Exception Type)" + RESET);
            fail("Unexpected exception type");
        }
    }

    @Test
    void emptyReservationBillingTest() {

        System.out.println("\nRunning: emptyReservationBillingTest");

        try {
            service.calculateBill("", true);

            System.out.println(RED + "Result: FAIL (No Exception Thrown)" + RESET);
            fail("Expected IllegalArgumentException was not thrown");

        } catch (IllegalArgumentException e) {
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Wrong Exception Type)" + RESET);
            fail("Unexpected exception type");
        }
    }
}