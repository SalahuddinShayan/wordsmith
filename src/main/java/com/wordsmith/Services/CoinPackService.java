package com.wordsmith.Services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class CoinPackService {

    public long mapUsdToCoins(BigDecimal usd) {

        if (usd.compareTo(BigDecimal.valueOf(1)) == 0) return 80;
        if (usd.compareTo(BigDecimal.valueOf(5)) == 0) return 450;
        if (usd.compareTo(BigDecimal.valueOf(10)) == 0) return 1000;

        throw new IllegalArgumentException("Unsupported coin pack: $" + usd);
    }
}
