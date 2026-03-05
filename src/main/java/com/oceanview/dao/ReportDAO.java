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
            "GROUP BY DATE(generated_at) ORDER BY day ASC";

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
    
    /* ================= 30 DAY REVENUE ================= */
    public Map<String, Double> last30DaysRevenue() {

        Map<String, Double> map = new LinkedHashMap<>();

        String sql =
            "SELECT DATE(generated_at) as day, " +
            "IFNULL(SUM(total_amount),0) as total " +
            "FROM billing " +
            "WHERE generated_at >= DATE(NOW()) - INTERVAL 29 DAY " +
            "AND generated_at < DATE(NOW()) + INTERVAL 1 DAY " +
            "GROUP BY DATE(generated_at) ORDER BY day ASC";

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

    /* ================= OCCUPANCY TREND ================= */

    public Map<String, Integer> last30DaysOccupancy() {

        Map<String, Integer> map = new LinkedHashMap<>();

        String sql =
            "SELECT DATE(check_in) as day, COUNT(*) as total " +
            "FROM reservations " +
            "WHERE check_in >= DATE(NOW()) - INTERVAL 29 DAY " +
            "AND check_in < DATE(NOW()) + INTERVAL 1 DAY " +
            "GROUP BY DATE(check_in) ORDER BY day ASC";

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
    
    /* ================= CHECK-IN TREND ================= */

    public Map<String, Integer> last30DaysCheckInTrend() {

        Map<String, Integer> map = new LinkedHashMap<>();

        String sql =
            "SELECT DATE(check_in) as day, COUNT(*) as total " +
            "FROM reservations " +
            "WHERE check_in >= DATE(NOW()) - INTERVAL 29 DAY " +
            "AND check_in < DATE(NOW()) + INTERVAL 1 DAY " +
            "GROUP BY DATE(check_in) ORDER BY day ASC";

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

    /* ================= CHECK-OUT TREND ================= */

    public Map<String, Integer> last30DaysCheckOutTrend() {

        Map<String, Integer> map = new LinkedHashMap<>();

        String sql =
            "SELECT DATE(checked_out_at) as day, COUNT(*) as total " +
            "FROM reservations " +
            "WHERE status='CHECKED_OUT' " +
            "AND checked_out_at >= DATE(NOW()) - INTERVAL 29 DAY " +
            "AND checked_out_at < DATE(NOW()) + INTERVAL 1 DAY " +
            "GROUP BY DATE(checked_out_at) ORDER BY day ASC";

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
    
    /* ================= Booking activity 30days ================= */
    
    public Map<String, Integer> last30DaysBookingActivity() {

        Map<String, Integer> map = new LinkedHashMap<>();

        String sql =
            "SELECT DATE(created_at) as day, COUNT(*) as total " +
            "FROM reservations " +
            "WHERE created_at >= DATE(NOW()) - INTERVAL 29 DAY " +
            "AND created_at < DATE(NOW()) + INTERVAL 1 DAY " +
            "GROUP BY DATE(created_at) ORDER BY day ASC";

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