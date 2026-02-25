package com.oceanview.service;

import com.oceanview.dao.BillingDAO;
import com.oceanview.dao.ReservationDAO;
import com.oceanview.model.Bill;
import com.oceanview.model.Reservation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * BillingService
 * -----------------------------------------
 * Handles bill calculation and revenue control
 */
public class BillingService {

    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final BillingDAO billingDAO = new BillingDAO();

    /* ================= CALCULATE BILL ================= */
    public Bill calculateBill(String reservationNumber,
                              boolean recordRevenue) {

        if (reservationNumber == null || reservationNumber.isBlank()) {
            throw new IllegalArgumentException("Reservation number required");
        }

        try {
            Reservation r =
                reservationDAO.findByReservationNumber(reservationNumber);

            if (r == null) {
                throw new IllegalArgumentException("Reservation not found");
            }

            long nights =
                ChronoUnit.DAYS.between(
                    r.getCheckIn(),
                    r.getCheckOut()
                );

            if (nights <= 0) {
                throw new IllegalArgumentException("Invalid stay duration");
            }

            double total =
                nights * r.getRoom().getRatePerNight();

            /* ===== BUILD BILL ===== */
            Bill bill = new Bill();
            bill.setReservationNumber(r.getReservationNumber());
            bill.setGuestName(r.getGuestName());
            bill.setRoomNumber(r.getRoom().getRoomNumber());
            bill.setRoomType(r.getRoom().getRoomType());
            bill.setPricePerNight(r.getRoom().getRatePerNight());
            bill.setCheckIn(r.getCheckIn());
            bill.setCheckOut(r.getCheckOut());
            bill.setNights((int) nights);
            bill.setTotalAmount(total);
            bill.setGeneratedAt(LocalDateTime.now());

            /* REVENUE CONTROL (VERY IMPORTANT) */
            if (recordRevenue && !billingDAO.billExists(reservationNumber)) {
                billingDAO.saveBill(bill);
            }

            return bill;

        } catch (Exception e) {
            throw new RuntimeException("Bill calculation failed", e);
        }
    }
}
