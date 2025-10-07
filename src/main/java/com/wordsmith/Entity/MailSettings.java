package com.wordsmith.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mail_settings")
public class MailSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String host;
    private int port;
    private String username;
    private String password;
    private boolean smtpAuth;
    private boolean starttlsEnable;
    private boolean starttlsRequired;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isSmtpAuth() {
        return smtpAuth;
    }
    public void setSmtpAuth(boolean smtpAuth) {
        this.smtpAuth = smtpAuth;
    }
    public boolean isStarttlsEnable() {
        return starttlsEnable;
    }
    public void setStarttlsEnable(boolean starttlsEnable) {
        this.starttlsEnable = starttlsEnable;
    }
    public boolean isStarttlsRequired() {
        return starttlsRequired;
    }
    public void setStarttlsRequired(boolean starttlsRequired) {
        this.starttlsRequired = starttlsRequired;
    }

    
}
