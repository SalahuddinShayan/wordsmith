package com.wordsmith.Util;

public class EmailMasker {

    /**
     * Masks an email or username for logs.
     *
     * Examples:
     *   "john.doe@example.com" → "j***e@example.com"
     *   "username123"          → "u******3"
     *   "a@b.com"              → "a*@b.com"
     *   null                   → "unknown"
     */
    public static String mask(String input) {
        if (input == null || input.isBlank()) {
            return "unknown";
        }

        // Case 1 — Email
        if (input.contains("@")) {
            String[] parts = input.split("@", 2);
            String local = parts[0];
            String domain = parts[1];

            if (local.length() <= 2) {
                return local.charAt(0) + "*@" + domain;
            }

            return local.charAt(0)
                    + "***"
                    + local.charAt(local.length() - 1)
                    + "@" + domain;
        }

        // Case 2 — Username (non-email)
        if (input.length() <= 2) {
            return input.charAt(0) + "*";
        }

        return input.charAt(0)
                + "******"
                + input.charAt(input.length() - 1);
    }
}
