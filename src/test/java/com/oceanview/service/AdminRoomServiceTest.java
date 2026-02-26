package com.oceanview.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminRoomServiceTest {

    private final AdminRoomService service = new AdminRoomService();

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    @Test
    void addRoomValidationTest() {

        System.out.println("\nRunning: addRoomValidationTest");

        try {
            service.addRoom(null, 100, "101");

            System.out.println(RED + "Result: FAIL (No Exception Thrown)" + RESET);
            fail("Expected RuntimeException was not thrown");

        } catch (RuntimeException e) {
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Wrong Exception Type)" + RESET);
            fail("Unexpected exception type");
        }
    }

    @Test
    void getAllRoomsTest() {

        System.out.println("\nRunning: getAllRoomsTest");

        try {
            var rooms = service.getAllRooms();

            if (rooms == null) {
                System.out.println(RED + "Result: FAIL (Rooms list is null)" + RESET);
                fail("Rooms list should not be null");
            }

            System.out.println("Room Count: " + rooms.size());
            System.out.println(GREEN + "Result: PASS" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Result: FAIL (Exception Thrown)" + RESET);
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}