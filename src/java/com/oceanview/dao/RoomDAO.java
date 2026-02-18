package com.oceanview.dao;

import com.oceanview.model.Room;
import com.oceanview.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * RoomDAO
 * ----------------------------------------------------
 * Supports:
 * - Admin room management
 * - Staff room availability
 * - Reservation handling
 * - Room type dropdown
 *
 * Java 17 | Tomcat 9 | MySQL
 */
public class RoomDAO {

    /* =================================================
       ROOM TYPE HANDLING (CREATE / UPDATE PRICE)
       ================================================= */

    public int findOrCreateRoomType(String typeName, double price)
            throws SQLException {

        String selectSql =
            "SELECT room_type_id FROM room_types WHERE type_name = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(selectSql)) {

            ps.setString(1, typeName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int typeId = rs.getInt("room_type_id");
                updateRoomTypePrice(typeId, price);
                return typeId;
            }
        }

        String insertSql =
            "INSERT INTO room_types (type_name, rate_per_night) VALUES (?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps =
                 conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, typeName);
            ps.setDouble(2, price);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }

        throw new SQLException("Failed to create room type");
    }

    private void updateRoomTypePrice(int typeId, double price)
            throws SQLException {

        String sql =
            "UPDATE room_types SET rate_per_night = ? WHERE room_type_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, price);
            ps.setInt(2, typeId);
            ps.executeUpdate();
        }
    }

    /* =================================================
       ROOM TYPE LIST
       ================================================= */

    public List<Room> findAllRoomTypes() throws SQLException {

        List<Room> types = new ArrayList<>();

        String sql =
            "SELECT room_type_id, type_name, rate_per_night FROM room_types";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Room r = new Room();
                r.setRoomTypeId(rs.getInt("room_type_id"));
                r.setRoomType(rs.getString("type_name"));
                r.setRatePerNight(rs.getDouble("rate_per_night"));
                types.add(r);
            }
        }
        return types;
    }

    /* =================================================
       ROOM CRUD
       ================================================= */

    public void addRoom(Room room) throws SQLException {

        String sql =
            "INSERT INTO rooms (room_number, room_type_id, status) " +
            "VALUES (?, ?, 'AVAILABLE')";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, room.getRoomNumber());
            ps.setInt(2, room.getRoomTypeId());
            ps.executeUpdate();
        }
    }

    public boolean roomNumberExists(String roomNumber) throws SQLException {

        String sql =
            "SELECT COUNT(*) FROM rooms WHERE room_number = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roomNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public void deleteRoom(int roomId) throws SQLException {

        String sql = "DELETE FROM rooms WHERE room_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.executeUpdate();
        }
    }

    /* =================================================
       ROOM STATUS
       ================================================= */

    public void updateRoomStatus(int roomId, String status)
            throws SQLException {

        String sql =
            "UPDATE rooms SET status = ? WHERE room_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, roomId);
            ps.executeUpdate();
        }
    }

    /* =================================================
       AVAILABLE ROOMS
       ================================================= */

    public List<Room> findAvailableRooms() throws SQLException {

        List<Room> rooms = new ArrayList<>();

        String sql =
            "SELECT r.room_id, r.room_number, " +
            "rt.type_name, rt.rate_per_night " +
            "FROM rooms r " +
            "JOIN room_types rt ON r.room_type_id = rt.room_type_id " +
            "WHERE r.status = 'AVAILABLE'";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("room_id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomType(rs.getString("type_name"));
                room.setRatePerNight(rs.getDouble("rate_per_night"));
                rooms.add(room);
            }
        }
        return rooms;
    }

    /* =================================================
       ADMIN VIEW
       ================================================= */

    public List<Room> findAllRoomsWithStatus() throws SQLException {

        List<Room> list = new ArrayList<>();

        String sql =
            "SELECT r.room_id, r.room_number, r.status, " +
            "rt.type_name, rt.rate_per_night " +
            "FROM rooms r " +
            "JOIN room_types rt ON r.room_type_id = rt.room_type_id";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("room_id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setStatus(rs.getString("status"));
                room.setRoomType(rs.getString("type_name"));
                room.setRatePerNight(rs.getDouble("rate_per_night"));
                list.add(room);
            }
        }
        return list;
    }
}
