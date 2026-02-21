package com.oceanview.dao;

import com.oceanview.util.DBConnection;
import java.sql.*;

public class ReportDAO {

    public int dailyReservations() {
        return fetchCount(
            "SELECT COUNT(*) FROM reservations WHERE DATE(check_in)=CURDATE()"
        );
    }

    public int checkInToday() {
        return fetchCount(
            "SELECT COUNT(*) FROM reservations WHERE DATE(check_in)=CURDATE()"
        );
    }

    /* ✅ THIS NOW WORKS */
    public int checkOutToday() {
        return fetchCount(
            "SELECT COUNT(*) FROM reservations " +
            "WHERE status='CHECKED_OUT' " +
            "AND DATE(checked_out_at)=CURDATE()"
        );
    }

    public int occupiedRooms() {
        return fetchCount(
            "SELECT COUNT(*) FROM rooms WHERE status='OCCUPIED'"
        );
    }

    public double dailyRevenue() {
        return fetchSum(
            "SELECT IFNULL(SUM(total_amount),0) FROM billing " +
            "WHERE DATE(generated_at)=CURDATE()"
        );
    }

    public double monthlyRevenue() {
        return fetchSum(
            "SELECT IFNULL(SUM(total_amount),0) FROM billing " +
            "WHERE YEAR(generated_at)=YEAR(CURDATE()) " +
            "AND MONTH(generated_at)=MONTH(CURDATE())"
        );
    }

    private int fetchCount(String sql) {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            return r.next() ? r.getInt(1) : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    private double fetchSum(String sql) {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            return r.next() ? r.getDouble(1) : 0;
        } catch (Exception e) {
            return 0;
        }
    }
}
