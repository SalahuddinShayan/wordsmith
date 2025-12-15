package com.wordsmith.Util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordsmith.DevPayPalConfigProperties;
import com.wordsmith.Entity.PayPalSettings;
import com.wordsmith.Services.PayPalSettingsService;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Component
public class PayPalClient {

    private final PayPalSettingsService liveSettings;
    private final DevPayPalConfigProperties devSettings;
    private final boolean isDev;

    private final RestTemplate restTemplate = new RestTemplate();

    public PayPalClient(
            PayPalSettingsService liveSettings,
            DevPayPalConfigProperties devSettings,
            Environment env
    ) {
        this.liveSettings = liveSettings;
        this.devSettings = devSettings;
        this.isDev = java.util.Arrays.asList(env.getActiveProfiles()).contains("dev"); // TRUE on local dev

        System.out.println("======== PAYPAL CLIENT DEBUG ========");
        System.out.println("Active Profiles → " + String.join(",", env.getActiveProfiles()));
        System.out.println("isDev = " + isDev);

        try {
            System.out.println("devSettings.clientId = " + devSettings.getClientId());
            System.out.println("devSettings.clientSecret = " + devSettings.getClientSecret());
            System.out.println("devSettings.webhookId = " + devSettings.getWebhookId());
        } catch (Exception ex) {
            System.out.println("devSettings is NULL (not loaded!)");
        }

        try {
            var live = liveSettings.getSettings();
            System.out.println("live.clientId = " + live.getClientId());
            System.out.println("live.clientSecret = " + live.getClientSecret());
            System.out.println("live.webhookId = " + live.getWebhookId());
        } catch (Exception ex) {
            System.out.println("LIVE settings not found in DB!");
        }
        System.out.println("=======================================");

    }

    // ---------------------------------------------------------------------
    // 🔹 SELECT CORRECT VALUES (Sandbox vs Live)
    // ---------------------------------------------------------------------
    public String getClientId() {
        return isDev ? devSettings.getClientId() : liveSettings.getSettings().getClientId();
    }

    private String getClientSecret() {
        return isDev ? devSettings.getClientSecret() : liveSettings.getSettings().getClientSecret();
    }

    public String getWebhookId() {
        return isDev ? devSettings.getWebhookId() : liveSettings.getSettings().getWebhookId();
    }

    private String getApiBase() {
        return isDev
                ? "https://api-m.sandbox.paypal.com"
                : "https://api-m.paypal.com";
    }

    // ---------------------------------------------------------------------
    // 🔹 Get OAuth Access Token
    // ---------------------------------------------------------------------
    public String getAccessToken() {
        String credentials = getClientId() + ":" + getClientSecret();
        String base64Creds = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + base64Creds);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        Map<String, Object> response = restTemplate.postForObject(
                getApiBase() + "/v1/oauth2/token",
                request,
                Map.class
        );

        return (String) response.get("access_token");
    }

    // ---------------------------------------------------------------------
    // 🔹 Verify Webhook Signature
    // ---------------------------------------------------------------------
    public boolean verifySignature(Map<String, Object> verificationPayload) {

        String url = getApiBase() + "/v1/notifications/verify-webhook-signature";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getAccessToken());

        HttpEntity<Map<String, Object>> req = new HttpEntity<>(verificationPayload, headers);

        Map<String, Object> response =
                restTemplate.postForObject(url, req, Map.class);

        String status = (String) response.get("verification_status");

        return "SUCCESS".equalsIgnoreCase(status);
    }

    // ---------------------------------------------------------------------
    // 🔹 Fetch Subscription Details
    // ---------------------------------------------------------------------
    public JsonNode getSubscription(String subscriptionId) {

        String url = getApiBase() + "/v1/billing/subscriptions/" + subscriptionId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        try {
            return new ObjectMapper().readTree(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Error parsing subscription JSON", e);
        }
    }

    public JsonNode getSubscriptionTransactions(String subscriptionId) {

        String url = getApiBase()
                + "/v1/billing/subscriptions/"
                + subscriptionId
                + "/transactions?start_time=2000-01-01T00:00:00Z&end_time=2099-01-01T00:00:00Z";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken());

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        try {
            return new ObjectMapper().readTree(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse PayPal transactions response", e);
        }
    }

}
