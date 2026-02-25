package com.oceanview.dao;

import com.oceanview.util.DBConnection;
import java.sql.*;
import java.util.*;

public class ReportDAO {

    /* ================= DAILY COUNTS ================= */

    public int dailyReservations() {
        return fetchCount(
            "SELECT COUNT(*) FROM reservations " +
            "WHERE check_in >= DATE(NOW()) " +
            "AND check_in < DATE(NOW()) + INTERVAL 1 DAY"
        );
    }

    public int checkInToday() {
        return fetchCount(
            "SELECT COUNT(*) FROM reservations " +
            "WHERE check_in >= DATE(NOW()) " +
            "AND check_in < DATE(NOW()) + INTERVAL 1 DAY"
        );
    }

    public int checkOutToday() {
        return fetchCount(
            "SELECT COUNT(*) FROM reservations " +
            "WHERE status='CHECKED_OUT' " +
            "AND checked_out_at >= DATE(NOW()) " +
            "AND checked_out_at < DATE(NOW()) + INTERVAL 1 DAY"
        );
    }

    public int occupiedRooms() {
        return fetchCount(
            "SELECT COUNT(*) FROM rooms WHERE status='OCCUPIED'"
        );
    }

    public int availableRooms() {
        return fetchCount(
            "SELECT COUNT(*) FROM rooms WHERE status='AVAILABLE'"
        );
    }

    /* ================= DAILY REVENUE ================= */

    public double dailyRevenue() {
        return fetchSum(
            "SELECT IFNULL(SUM(total_amount),0) FROM billing " +
            "WHERE generated_at >= DATE(NOW()) " +
            "AND generated_at < DATE(NOW()) + INTERVAL 1 DAY"
        );
    }

    /* ================= MONTHLY REVENUE ================= */

    public double monthlyRevenue() {
        return fetchSum(
            "SELECT IFNULL(SUM(total_amount),0) FROM billing " +
            "WHERE generated_at >= DATE_FORMAT(NOW(), '%Y-%m-01') " +
            "AND generated_at < DATE_FORMAT(NOW() + INTERVAL 1 MONTH, '%Y-%m-01')"
        );
    }

    /* ================= 7 DAY REVENUE ================= */

    public Map<String, Double> last7DaysRevenue() {

        Map<String, Double> map = new LinkedHashMap<>();

        String sql =
            "SELECT DATE(generated_at) as day, " +
            "IFNULL(SUM(total_amount),0) as total " +
            "FROM billing " +
            "WHERE generated_at >= DATE(NOW()) - INTERVAL 6 DAY " +
            "AND generated_at < DATE(NOW()) + INTERVAL 1 DAY " +
            "GROUP BY DATE(generated_at) " +
            "ORDER BY day ASC";

        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {

            while (r.next()) {
                map.put(r.getString("day"), r.getDouble("total"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /* ================= 7 DAY OCCUPANCY ================= */

    public Map<String, Integer> last7DaysOccupancy() {

        Map<String, Integer> map = new LinkedHashMap<>();

        String sql =
            "SELECT DATE(check_in) as day, COUNT(*) as total " +
            "FROM reservations " +
            "WHERE check_in >= DATE(NOW()) - INTERVAL 6 DAY " +
            "AND check_in < DATE(NOW()) + INTERVAL 1 DAY " +
            "GROUP BY DATE(check_in) " +
            "ORDER BY day ASC";

        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {

            while (r.next()) {
                map.put(r.getString("day"), r.getInt("total"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /* ================= COMMON METHODS ================= */

    private int fetchCount(String sql) {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {

            return r.next() ? r.getInt(1) : 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private double fetchSum(String sql) {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {

            return r.next() ? r.getDouble(1) : 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
