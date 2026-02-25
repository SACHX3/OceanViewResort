package com.oceanview.dao;

import com.oceanview.model.Bill;
import com.oceanview.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * BillingDAO
 * -----------------------------------------
 * Handles persistence of billing records
 */
public class BillingDAO {

    /* ================= CHECK DUPLICATE BILL ================= */
    public boolean billExists(String reservationNumber) throws SQLException {

        String sql =
            "SELECT 1 FROM billing WHERE reservation_number = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reservationNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    /* ================= SAVE BILL ================= */
    public void saveBill(Bill bill) throws SQLException {

        String sql =
            "INSERT INTO billing " +
            "(reservation_number, nights, price_per_night, total_amount, generated_at) " +
            "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bill.getReservationNumber());
            ps.setInt(2, bill.getNights());
            ps.setDouble(3, bill.getPricePerNight());
            ps.setDouble(4, bill.getTotalAmount());
            ps.setTimestamp(5,
                java.sql.Timestamp.valueOf(bill.getGeneratedAt()));

            ps.executeUpdate();
        }
    }

    /* ================= FETCH BILL FOR API ================= */
    public Bill getLatestBill() throws SQLException {

        String sql =
            "SELECT * FROM billing ORDER BY generated_at DESC LIMIT 1";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                Bill bill = new Bill();
                bill.setReservationNumber(
                    rs.getString("reservation_number")
                );
                bill.setNights(
                    rs.getInt("nights")
                );
                bill.setPricePerNight(
                    rs.getDouble("price_per_night")
                );
                bill.setTotalAmount(
                    rs.getDouble("total_amount")
                );
                bill.setGeneratedAt(
                    rs.getTimestamp("generated_at").toLocalDateTime()
                );
                return bill;
            }
        }
        return null;
    }
}
