package com.oceanview.controller;

import com.oceanview.model.Reservation;
import com.oceanview.model.Room;
import com.oceanview.service.ReservationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/**
 * AddReservationServlet
 * -----------------------------------------
 * Handles reservation form submission
 */
@WebServlet("/addReservation")
public class AddReservationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ReservationService reservationService =
            new ReservationService();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        try {
            String guestName = request.getParameter("guestName");
            String address = request.getParameter("address");
            String contact = request.getParameter("contactNumber");
            String email = request.getParameter("contactEmail");
            String roomIdStr = request.getParameter("roomId");
            String checkInStr = request.getParameter("checkIn");
            String checkOutStr = request.getParameter("checkOut");

            if (guestName == null || guestName.isBlank()
                    || contact == null || contact.isBlank()
                    || email == null || email.isBlank()
                    || roomIdStr == null || roomIdStr.isBlank()
                    || checkInStr == null || checkOutStr == null) {

                response.sendRedirect("addReservation.html?status=error");
                return;
            }

            int roomId = Integer.parseInt(roomIdStr);

            Room room = new Room();
            room.setRoomId(roomId);

            Reservation reservation = new Reservation();
            reservation.setGuestName(guestName.trim());
            reservation.setAddress(address != null ? address.trim() : "");
            reservation.setContactNumber(contact.trim());
            reservation.setContactEmail(email.trim()); // ✅ VERY IMPORTANT
            reservation.setRoom(room);
            reservation.setCheckIn(LocalDate.parse(checkInStr));
            reservation.setCheckOut(LocalDate.parse(checkOutStr));

            // ✅ THIS CALL MUST NOT FAIL
            reservationService.createReservation(reservation);

            response.sendRedirect("addReservation.html?status=success");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addReservation.html?status=error");
        }
    }
}
