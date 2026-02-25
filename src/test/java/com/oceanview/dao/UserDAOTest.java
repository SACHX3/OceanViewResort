package com.oceanview.dao;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private final UserDAO dao = new UserDAO();

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    @Test
    void findAllUsersTest() {

        System.out.println("\nRunning: findAllUsersTest");

        try {
            List<?> users = dao.findAllUsers();

            if (users == null) {
                System.out.println(RED + "Result: FAIL (Users list is null)" + RESET);
                fail("Users list should not be null");
            }

            System.out.println("Users Found: " + users.size());
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    void findByUsernameTest() {

        System.out.println("\nRunning: findByUsernameTest");

        try {
            var user = dao.findByUsername("admin");

            System.out.println("User Found: " + user);
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Exception: " + e.getMessage());
        }
    }
}