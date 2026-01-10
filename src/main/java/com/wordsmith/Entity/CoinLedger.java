package com.wordsmith.Entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "coin_ledger")
public class CoinLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Long delta; // +credit / -debit

    @Column(nullable = false)
    private String reason;
    // COIN_PURCHASE, CHAPTER_PURCHASE, ADMIN_ADJUST, REFUND

    private String referenceType;
    // PAYMENT_TRANSACTION, CHAPTER, ADMIN

    private String referenceId;

    @Column(nullable = false)
    private Long balanceAfter;

    @Column(nullable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @Transient
    private String formattedCreatedAt;

    // --------------------
    // Getters & Setters
    // --------------------

    public Long getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Long getDelta() { return delta; }
    public void setDelta(Long delta) { this.delta = delta; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }

    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }

    public Long getBalanceAfter() { return balanceAfter; }
    public void setBalanceAfter(Long balanceAfter) { this.balanceAfter = balanceAfter; }

    public ZonedDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(ZonedDateTime createdAt) { this.createdAt = createdAt; }

    public String getFormattedCreatedAt() { return formattedCreatedAt; }
    public void setFormattedCreatedAt(String formattedCreatedAt) { this.formattedCreatedAt = formattedCreatedAt; }
}
