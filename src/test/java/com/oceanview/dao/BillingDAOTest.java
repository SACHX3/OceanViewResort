package com.oceanview.dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BillingDAOTest {

    private final BillingDAO dao = new BillingDAO();

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    @Test
    void billExistsTest() {

        System.out.println("\nRunning: billExistsTest");

        try {
            boolean exists = dao.billExists("INVALID_TEST_001");

            if (exists) {
                System.out.println(RED + "Result: FAIL (Expected false but got true)" + RESET);
                fail("Invalid reservation number should return false");
            }

            System.out.println("Bill Exists Result: " + exists);
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Exception occurred: " + e.getMessage());
        }
    }
}