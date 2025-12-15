package com.wordsmith.Services;

import com.wordsmith.Entity.PayPalSettings;
import com.wordsmith.Repositories.PayPalSettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class PayPalSettingsService {

    private final PayPalSettingsRepository repo;

    public PayPalSettingsService(PayPalSettingsRepository repo) {
        this.repo = repo;
    }

    /** Always load the FIRST row */
    public PayPalSettings getSettings() {
        return repo.findAll().stream().findFirst()
            .orElseThrow(() -> new IllegalStateException("⚠️ LIVE PayPal settings not configured in DB"));
    }

    public void save(PayPalSettings settings) {
        repo.save(settings);
    }
}
