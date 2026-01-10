package com.wordsmith.Enum;

public enum ChapterAccessResult {
    ALLOWED,            // Released OR purchased
    LOGIN_REQUIRED,     // Stockpile + not logged in
    PURCHASE_REQUIRED   // Stockpile + logged in + not purchased
}
