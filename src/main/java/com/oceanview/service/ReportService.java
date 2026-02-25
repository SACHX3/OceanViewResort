package com.oceanview.service;

import com.oceanview.dao.ReportDAO;
import java.util.Map;

public class ReportService {

    private final ReportDAO dao = new ReportDAO();

    public int fetchDailyReservations() { return dao.dailyReservations(); }
    public int fetchCheckInToday() { return dao.checkInToday(); }
    public int fetchCheckOutToday() { return dao.checkOutToday(); }
    public int fetchOccupiedRooms() { return dao.occupiedRooms(); }
    public int fetchAvailableRooms() { return dao.availableRooms(); }
    public double fetchDailyRevenue() { return dao.dailyRevenue(); }
    public double fetchMonthlyRevenue() { return dao.monthlyRevenue(); }

    public Map<String, Double> fetchLast7DaysRevenue() {
        return dao.last7DaysRevenue();
    }

    public Map<String, Integer> fetchLast7DaysOccupancy() {
        return dao.last7DaysOccupancy();
    }
}
