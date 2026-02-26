package com.oceanview.dao;

import com.oceanview.model.Room;
import com.oceanview.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    /* ================= FIND OR CREATE ROOM TYPE ================= */

    public int findOrCreateRoomType(String typeName)
            throws SQLException {

        String selectSql =
                "SELECT room_type_id FROM room_types WHERE type_name = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(selectSql)) {

            ps.setString(1, typeName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("room_type_id");
            }
        }

        String insertSql =
                "INSERT INTO room_types (type_name) VALUES (?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, typeName);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        }

        throw new SQLException("Failed to create room type");
    }

    /* ================= ADD ROOM ================= */

    public void addRoom(Room room) throws SQLException {

        String sql =
                "INSERT INTO rooms (room_number, room_type_id, rate_per_night, status) " +
                "VALUES (?, ?, ?, 'AVAILABLE')";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, room.getRoomNumber());
            ps.setInt(2, room.getRoomTypeId());
            ps.setDouble(3, room.getRatePerNight());
            ps.executeUpdate();
        }
    }

    /* ================= UPDATE ROOM ================= */

    public void updateRoom(int roomId,
                           String typeName,
                           double price,
                           String roomNumber) throws SQLException {

        int typeId = findOrCreateRoomType(typeName);

        String sql =
                "UPDATE rooms SET room_number=?, room_type_id=?, rate_per_night=? WHERE room_id=?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roomNumber);
            ps.setInt(2, typeId);
            ps.setDouble(3, price);
            ps.setInt(4, roomId);
            ps.executeUpdate();
        }
    }

    /* ================= DELETE ROOM ================= */

    public void deleteRoom(int roomId) throws SQLException {

        String sql = "DELETE FROM rooms WHERE room_id=?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.executeUpdate();
        }
    }

    /* ================= CHECK DUPLICATE ================= */

    public boolean roomNumberExists(String roomNumber)
            throws SQLException {

        String sql =
                "SELECT COUNT(*) FROM rooms WHERE room_number=?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roomNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    /* ================= UPDATE STATUS ================= */

    public void updateRoomStatus(int roomId, String status)
            throws SQLException {

        String sql =
                "UPDATE rooms SET status=? WHERE room_id=?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, roomId);
            ps.executeUpdate();
        }
    }

    /* ================= FIND AVAILABLE ROOMS ================= */

    public List<Room> findAvailableRooms()
            throws SQLException {

        List<Room> rooms = new ArrayList<>();

        String sql =
                "SELECT r.room_id, r.room_number, r.status, r.rate_per_night, " +
                "rt.room_type_id, rt.type_name " +
                "FROM rooms r " +
                "JOIN room_types rt ON r.room_type_id = rt.room_type_id " +
                "WHERE r.status='AVAILABLE'";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Room room = new Room();
                room.setRoomId(rs.getInt("room_id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setStatus(rs.getString("status"));
                room.setRatePerNight(rs.getDouble("rate_per_night"));
                room.setRoomTypeId(rs.getInt("room_type_id"));
                room.setRoomType(rs.getString("type_name"));

                rooms.add(room);
            }
        }

        return rooms;
    }

    /* ================= FIND ALL ROOMS ================= */

    public List<Room> findAllRoomsWithStatus()
            throws SQLException {

        List<Room> list = new ArrayList<>();

        String sql =
                "SELECT r.room_id, r.room_number, r.status, r.rate_per_night, " +
                "rt.room_type_id, rt.type_name " +
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
                room.setRatePerNight(rs.getDouble("rate_per_night"));
                room.setRoomTypeId(rs.getInt("room_type_id"));
                room.setRoomType(rs.getString("type_name"));

                list.add(room);
            }
        }

        return list;
    }

    /* ================= ROOM TYPE LIST ================= */

    public List<Room> findAllRoomTypes()
            throws SQLException {

        List<Room> types = new ArrayList<>();

        String sql =
                "SELECT room_type_id, type_name FROM room_types";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Room r = new Room();
                r.setRoomTypeId(rs.getInt("room_type_id"));
                r.setRoomType(rs.getString("type_name"));

                types.add(r);
            }
        }

        return types;
    }
}