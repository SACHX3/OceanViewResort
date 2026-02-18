package com.oceanview.service;

import com.oceanview.dao.ReservationDAO;
import com.oceanview.model.Reservation;

import java.util.List;
import java.util.UUID;

/**
 * ReservationService
 * -------------------------------------------------
 * Business logic for reservation management
 */
public class ReservationService {

    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final EmailService emailService = new EmailService();

    /* ================= CREATE RESERVATION ================= */
    public void createReservation(Reservation reservation) {

        validateReservation(reservation);

        try {
            String reservationNumber =
                "RES-" + UUID.randomUUID().toString()
                              .substring(0, 8)
                              .toUpperCase();

            reservation.setReservationNumber(reservationNumber);

            reservationDAO.addReservation(reservation);
            reservationDAO.markRoomOccupied(
                reservation.getRoom().getRoomId()
            );

            // ✅ Send confirmation email
            emailService.sendReservationConfirmation(reservation);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create reservation", e);
        }
    }

    /* ================= GET SINGLE RESERVATION ================= */
    public Reservation getReservation(String reservationNumber) {

        if (reservationNumber == null || reservationNumber.isBlank())
            throw new IllegalArgumentException("Reservation number required");

        try {
            return reservationDAO.findByReservationNumber(reservationNumber);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch reservation", e);
        }
    }

    /* ================= CHECK OUT RESERVATION ================= */
    public void checkOutReservation(String reservationNumber) {

        if (reservationNumber == null || reservationNumber.isBlank()) {
            throw new IllegalArgumentException("Reservation number required");
        }

        try {
            int roomId =
                reservationDAO.findRoomIdByReservationNumber(reservationNumber);

            reservationDAO.checkOut(reservationNumber);

            if (roomId > 0) {
                reservationDAO.markRoomAvailable(roomId);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to check out reservation", e);
        }
    }

    /* ================= DELETE RESERVATION ================= */
    public void deleteReservation(String reservationNumber) {

        if (reservationNumber == null || reservationNumber.isBlank())
            throw new IllegalArgumentException("Reservation number required");

        try {
            int roomId =
                reservationDAO.findRoomIdByReservationNumber(reservationNumber);

            reservationDAO.deleteReservation(reservationNumber);

            if (roomId > 0) {
                reservationDAO.markRoomAvailable(roomId);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete reservation", e);
        }
    }

    /* ================= LIST ALL RESERVATIONS ================= */
    public List<Reservation> getAllReservations() {

        try {
            return reservationDAO.findAllReservations();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load reservations", e);
        }
    }

    /* ================= VALIDATION ================= */
    private void validateReservation(Reservation r) {

        if (r == null)
            throw new IllegalArgumentException("Reservation object is null");

        if (r.getGuestName() == null || r.getGuestName().isBlank())
            throw new IllegalArgumentException("Guest name required");

        if (r.getContactEmail() == null || r.getContactEmail().isBlank())
            throw new IllegalArgumentException("Guest email required");

        if (r.getRoom() == null || r.getRoom().getRoomId() <= 0)
            throw new IllegalArgumentException("Room selection required");

        if (r.getCheckIn() == null || r.getCheckOut() == null)
            throw new IllegalArgumentException("Check-in/out dates required");

        if (!r.getCheckOut().isAfter(r.getCheckIn()))
            throw new IllegalArgumentException("Invalid check-in/check-out dates");
    }
}
