package com.oceanview.service;

import com.oceanview.dao.RoomDAO;
import com.oceanview.model.Room;

import java.util.List;

public class AdminRoomService {

    private final RoomDAO roomDAO = new RoomDAO();

    public void addRoom(String typeName,
                        double price,
                        String roomNumber) {

        try {

            if (roomDAO.roomNumberExists(roomNumber))
                throw new IllegalArgumentException("Room number already exists");

            int typeId = roomDAO.findOrCreateRoomType(typeName);

            Room room = new Room();
            room.setRoomNumber(roomNumber);
            room.setRoomTypeId(typeId);
            room.setRatePerNight(price);

            roomDAO.addRoom(room);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateRoom(int roomId,
                           String typeName,
                           double price,
                           String roomNumber) {

        try {
            roomDAO.updateRoom(roomId, typeName, price, roomNumber);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteRoom(int roomId) {
        try {
            roomDAO.deleteRoom(roomId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Room> getAllRooms() {
        try {
            return roomDAO.findAllRoomsWithStatus();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}