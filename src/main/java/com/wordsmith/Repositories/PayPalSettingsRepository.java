package com.wordsmith.Repositories;

import com.wordsmith.Entity.PayPalSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayPalSettingsRepository extends JpaRepository<PayPalSettings, Long> {
}
