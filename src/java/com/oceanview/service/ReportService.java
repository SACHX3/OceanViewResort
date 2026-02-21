package com.oceanview.service;

import com.oceanview.dao.ReportDAO;

/**
 * ReportService
 * ------------------------------------
 * Business layer for dashboard statistics
 */
public class ReportService {

    private final ReportDAO reportDAO = new ReportDAO();

    public int fetchDailyReservations() {
        return reportDAO.dailyReservations();
    }

    public int fetchCheckInToday() {
        return reportDAO.checkInToday();
    }

    public int fetchCheckOutToday() {
        return reportDAO.checkOutToday();
    }

    public int fetchOccupiedRooms() {
        return reportDAO.occupiedRooms();
    }

    public double fetchDailyRevenue() {
        return reportDAO.dailyRevenue();
    }

    public double fetchMonthlyRevenue() {
        return reportDAO.monthlyRevenue();
    }
}
