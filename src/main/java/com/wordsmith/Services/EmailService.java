package com.wordsmith.Services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String to, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("noreply@easternwordsmith.com"); // ✅ MUST match Zoho authenticated email
            helper.setTo(to);
            helper.setSubject("Verify Your Email - OTP");
            helper.setText("Your OTP for registration is: " + otp);

            mailSender.send(message);
            System.out.println("OTP sent to " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}