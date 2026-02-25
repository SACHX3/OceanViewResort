package com.oceanview.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginServletTest {

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    @Test
    void loginServletInstantiationTest() {

        System.out.println("\nRunning: loginServletInstantiationTest");

        try {
            LoginServlet servlet = new LoginServlet();
            assertNotNull(servlet);

            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (AssertionError e) {
            System.out.println(RED + "Result: FAIL" + RESET);
            throw e;
        }
    }

    @Test
    void logoutServletInstantiationTest() {

        System.out.println("\nRunning: logoutServletInstantiationTest");

        try {
            LogoutServlet servlet = new LogoutServlet();
            assertNotNull(servlet);

            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (AssertionError e) {
            System.out.println(RED + "Result: FAIL" + RESET);
            throw e;
        }
    }
}