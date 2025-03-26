package com.wordsmith.Util;

import java.security.SecureRandom;

public class OTPUtil {
    private static final SecureRandom random = new SecureRandom();

    public static String generateOTP() {
        return String.valueOf(100000 + random.nextInt(900000)); // 6-digit OTP
    }
}