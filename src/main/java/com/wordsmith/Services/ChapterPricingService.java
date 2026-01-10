package com.wordsmith.Services;

import org.springframework.stereotype.Service;

@Service
public class ChapterPricingService {

    // 🔒 Pricing rules (locked for Phase 2)
    private static final int CHARACTERS_PER_PAGE = 1000;
    private static final int MIN_COINS_PER_CHAPTER = 1;

    /**
     * Calculates coin price for a chapter based on character count.
     *
     * Rules:
     * - 1 coin per 1000 characters
     * - Minimum 1 coin
     * - No maximum cap
     */
    public int calculatePrice(int characterCount) {

        if (characterCount <= 0) {
            return MIN_COINS_PER_CHAPTER;
        }

        int pages = (int) Math.ceil(
                (double) characterCount / CHARACTERS_PER_PAGE
        );

        return Math.max(MIN_COINS_PER_CHAPTER, pages);
    }
}
