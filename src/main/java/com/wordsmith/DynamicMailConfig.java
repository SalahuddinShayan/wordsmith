package com.wordsmith;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.wordsmith.Entity.MailSettings;
import com.wordsmith.Repositories.MailSettingsRepository;

@Configuration
public class DynamicMailConfig {

    private final MailSettingsRepository mailSettingsRepository;

    public DynamicMailConfig(MailSettingsRepository mailSettingsRepository) {
        this.mailSettingsRepository = mailSettingsRepository;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        MailSettings settings = mailSettingsRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new RuntimeException("Mail settings not found in DB"));

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(settings.getHost());
        mailSender.setPort(settings.getPort());
        mailSender.setUsername(settings.getUsername());
        mailSender.setPassword(settings.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", String.valueOf(settings.isSmtpAuth()));
        props.put("mail.smtp.starttls.enable", String.valueOf(settings.isStarttlsEnable()));
        props.put("mail.smtp.starttls.required", String.valueOf(settings.isStarttlsRequired()));

        return mailSender;
    }
}
