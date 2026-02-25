package com.oceanview.service;

import com.oceanview.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private final AuthService service = new AuthService();

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    @Test
    void authenticateNullPasswordTest() {

        System.out.println("\nRunning: authenticateNullPasswordTest");

        try {
            User user = service.authenticate("admin", null);

            if (user != null) {
                System.out.println(RED + "Result: FAIL (Expected null but got user)" + RESET);
                fail("Authentication should return null for null password");
            }

            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void authenticateInvalidUserTest() {

        System.out.println("\nRunning: authenticateInvalidUserTest");

        try {
            User user = service.authenticate("fakeUser", "fakePass");

            if (user != null) {
                System.out.println(RED + "Result: FAIL (Expected null but got user)" + RESET);
                fail("Authentication should return null for invalid credentials");
            }

            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}