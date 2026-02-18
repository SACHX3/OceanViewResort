package com.oceanview.dao;

import com.oceanview.model.Reservation;
import com.oceanview.model.Room;
import com.oceanview.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ReservationDAO
 * -------------------------------------------------
 * Handles all reservation database operations
 *
 * Java 17 | Tomcat 9 | MySQL
 */
public class ReservationDAO {

    /* ================= ADD RESERVATION ================= */

    private static final String INSERT_SQL =
        "INSERT INTO reservations " +
        "(reservation_number, guest_name, address, contact_number, contact_email, room_id, check_in, check_out, status) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'ACTIVE')";

    public void addReservation(Reservation r) throws SQLException {

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, r.getReservationNumber());
            ps.setString(2, r.getGuestName());
            ps.setString(3, r.getAddress());
            ps.setString(4, r.getContactNumber());
            ps.setString(5, r.getContactEmail());
            ps.setInt(6, r.getRoom().getRoomId());
            ps.setDate(7, Date.valueOf(r.getCheckIn()));
            ps.setDate(8, Date.valueOf(r.getCheckOut()));
            ps.executeUpdate();
        }
    }

    /* ================= FIND BY NUMBER ================= */

    public Reservation findByReservationNumber(String reservationNumber)
            throws SQLException {

        String sql =
            "SELECT r.*, rm.room_number, rt.type_name, rt.rate_per_night " +
            "FROM reservations r " +
            "JOIN rooms rm ON r.room_id = rm.room_id " +
            "JOIN room_types rt ON rm.room_type_id = rt.room_type_id " +
            "WHERE r.reservation_number = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reservationNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapReservation(rs) : null;
        }
    }

    /* ================= REQUIRED BY BILLING ================= */

    public Reservation findForBilling(String reservationNumber)
            throws SQLException {
        return findByReservationNumber(reservationNumber);
    }

    /* ================= FIND ALL ================= */

    public List<Reservation> findAllReservations() throws SQLException {

        List<Reservation> list = new ArrayList<>();

        String sql =
            "SELECT r.*, rm.room_number, rt.type_name, rt.rate_per_night " +
            "FROM reservations r " +
            "JOIN rooms rm ON r.room_id = rm.room_id " +
            "JOIN room_types rt ON rm.room_type_id = rt.room_type_id " +
            "ORDER BY r.check_in DESC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapReservation(rs));
            }
        }
        return list;
    }

    /* ================= CHECK OUT (FINAL FIX) ================= */

    public void checkOut(String reservationNumber) throws SQLException {

        String sql =
            "UPDATE reservations " +
            "SET status = 'CHECKED_OUT', " +
            "    checked_out_at = NOW() " +
            "WHERE reservation_number = ? " +
            "AND status = 'ACTIVE'";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reservationNumber);
            ps.executeUpdate();
        }
    }

    /* ================= DELETE ================= */

    public void deleteReservation(String reservationNumber)
            throws SQLException {

        String sql =
            "DELETE FROM reservations WHERE reservation_number = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reservationNumber);
            ps.executeUpdate();
        }
    }

    /* ================= ROOM HELPERS ================= */

    public int findRoomIdByReservationNumber(String reservationNumber)
            throws SQLException {

        String sql =
            "SELECT room_id FROM reservations WHERE reservation_number = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reservationNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("room_id") : -1;
        }
    }

    public void markRoomOccupied(int roomId) throws SQLException {

        String sql =
            "UPDATE rooms SET status = 'OCCUPIED' WHERE room_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.executeUpdate();
        }
    }

    public void markRoomAvailable(int roomId) throws SQLException {

        String sql =
            "UPDATE rooms SET status = 'AVAILABLE' WHERE room_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.executeUpdate();
        }
    }

    /* ================= MAPPER ================= */

    private Reservation mapReservation(ResultSet rs) throws SQLException {

        Reservation r = new Reservation();
        r.setReservationId(rs.getInt("reservation_id"));
        r.setReservationNumber(rs.getString("reservation_number"));
        r.setGuestName(rs.getString("guest_name"));
        r.setAddress(rs.getString("address"));
        r.setContactNumber(rs.getString("contact_number"));
        r.setContactEmail(rs.getString("contact_email"));
        r.setCheckIn(rs.getDate("check_in").toLocalDate());
        r.setCheckOut(rs.getDate("check_out").toLocalDate());
        r.setStatus(rs.getString("status"));

        Room room = new Room();
        room.setRoomId(rs.getInt("room_id"));
        room.setRoomNumber(rs.getString("room_number"));
        room.setRoomType(rs.getString("type_name"));
        room.setRatePerNight(rs.getDouble("rate_per_night"));

        r.setRoom(room);
        return r;
    }
}
