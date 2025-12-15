package com.wordsmith.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.wordsmith.Util.EmailMasker;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends OTP email with masked logging.
     * - Logs only masked email
     * - Does NOT log OTP
     */
    public void sendOtpEmail(String to, String otp) {

        String maskedEmail = EmailMasker.mask(to);

        log.info("EMAIL_OTP_SEND_ATTEMPT — to={}", maskedEmail);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("noreply@easternwordsmith.com");
            helper.setTo(to);
            helper.setSubject("Verify Your Email - OTP");

            // ❗ DO NOT log or expose OTP anywhere except the email body itself
            helper.setText("Your OTP for registration is: " + otp);

            mailSender.send(message);

            log.info("EMAIL_OTP_SEND_SUCCESS — to={}", maskedEmail);

        } catch (MessagingException e) {
            log.error("EMAIL_OTP_SEND_FAILURE — to={}, error={}",
                    maskedEmail, e.getMessage(), e);
        }
    }
}
