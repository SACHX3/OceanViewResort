package com.oceanview.service;

import com.oceanview.model.Reservation;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * EmailService
 * -----------------------------------------
 * Sends reservation confirmation emails
 */
public class EmailService {

    // GMAIL ACCOUNT
    private static final String FROM_EMAIL = "sachworkspace5@gmail.com";
    private static final String APP_PASSWORD = "hvinxthziojfktsl"; // 

    public void sendReservationConfirmation(Reservation r) {

        if (r == null || r.getContactEmail() == null || r.getContactEmail().isBlank()) {
            System.out.println("⚠ Email skipped (no guest email)");
            return;
        }

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");

            Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                            FROM_EMAIL,
                            APP_PASSWORD
                        );
                    }
                }
            );

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(r.getContactEmail())
            );

            message.setSubject("Reservation Confirmation - Ocean View Resort");

            String body =
                "Dear " + r.getGuestName() + ",\n\n" +
                "Your reservation has been confirmed.\n\n" +
                "Reservation Number: " + r.getReservationNumber() + "\n" +
                "Check-in Date: " + r.getCheckIn() + "\n" +
                "Check-out Date: " + r.getCheckOut() + "\n\n" +
                "Thank you for choosing Ocean View Resort.\n\n" +
                "Regards,\nOcean View Resort";

            message.setText(body);

            Transport.send(message);

            System.out.println("✅ EMAIL SENT to " + r.getContactEmail());

        } catch (Exception e) {
            System.err.println("❌ EMAIL FAILED");
            e.printStackTrace();
        }
    }
}
