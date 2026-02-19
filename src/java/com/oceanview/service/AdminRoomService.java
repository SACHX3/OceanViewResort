package com.oceanview.service;

import com.oceanview.dao.RoomDAO;
import com.oceanview.model.Room;

import java.util.List;

/**
 * AdminRoomService
 * -----------------------------------------
 * Business logic for admin room management
 */
public class AdminRoomService {

    private final RoomDAO roomDAO = new RoomDAO();

    /* ================= ADD ROOM ================= */
    public void addRoom(String typeName,
                        double price,
                        String roomNumber) {

        if (typeName == null || typeName.isBlank())
            throw new IllegalArgumentException("Room type is required");

        if (price <= 0)
            throw new IllegalArgumentException("Invalid price");

        if (roomNumber == null || roomNumber.isBlank())
            throw new IllegalArgumentException("Room number is required");

        try {
            // Prevent duplicate room numbers
            if (roomDAO.roomNumberExists(roomNumber)) {
                throw new IllegalArgumentException("Room number already exists");
            }

            // FIND OR CREATE ROOM TYPE (ALSO UPDATES PRICE)
            int roomTypeId =
                roomDAO.findOrCreateRoomType(typeName, price);

            // SAVE ROOM
            Room room = new Room();
            room.setRoomNumber(roomNumber);
            room.setRoomTypeId(roomTypeId);

            roomDAO.addRoom(room);

        } catch (Exception e) {
            throw new RuntimeException("Failed to add room", e);
        }
    }

    /* ================= LIST ROOMS ================= */
    public List<Room> getAllRooms() {

        try {
            return roomDAO.findAllRoomsWithStatus();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load rooms", e);
        }
    }

    /* ================= DELETE ROOM ================= */
    public void deleteRoom(int roomId) {

        try {
            roomDAO.deleteRoom(roomId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete room", e);
        }
    }
}
