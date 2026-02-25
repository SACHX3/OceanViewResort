package com.oceanview.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private final UserService service = new UserService();

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    @Test
    void getAllUsersTest() throws Exception {

        System.out.println("\nRunning: getAllUsersTest");

        try {
            var users = service.getAllUsers();

            if (users == null) {
                System.out.println(RED + "Result: FAIL (Users list is null)" + RESET);
                fail("Users list should not be null");
            }

            System.out.println("User Count: " + users.size());
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void deleteUserTest() {

        System.out.println("\nRunning: deleteUserTest");

        try {
            service.deleteUser(9999);

            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Delete user threw unexpected exception");
        }
    }
}