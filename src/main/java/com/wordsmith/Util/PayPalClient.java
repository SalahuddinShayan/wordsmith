package com.wordsmith.Util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordsmith.Services.PayPalSettingsService;
import com.wordsmith.DevPayPalConfigProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;
import java.util.Arrays;

@Component
public class PayPalClient {

    private final PayPalSettingsService liveSettings;
    private final DevPayPalConfigProperties devSettings;
    private final boolean isDev;

    private final RestTemplate restTemplate = new RestTemplate();

    public PayPalClient(
            PayPalSettingsService liveSettings,
            ObjectProvider<DevPayPalConfigProperties> devSettingsProvider,
            Environment env
    ) {
        this.liveSettings = liveSettings;
        this.devSettings = devSettingsProvider.getIfAvailable();
        this.isDev = Arrays.asList(env.getActiveProfiles()).contains("dev");
    }

    // ---------------------------------------------------------------------
    // 🔹 Credential Selection
    // ---------------------------------------------------------------------
    public String getClientId() {
        if (isDev) {
            if (devSettings == null) {
                throw new IllegalStateException("Dev PayPal config missing while dev profile is active");
            }
            return devSettings.getClientId();
        }
        return liveSettings.getSettings().getClientId();
    }

    private String getClientSecret() {
        if (isDev) {
            if (devSettings == null) {
                throw new IllegalStateException("Dev PayPal config missing while dev profile is active");
            }
            return devSettings.getClientSecret();
        }
        return liveSettings.getSettings().getClientSecret();
    }

    public String getWebhookId() {
        if (isDev) {
            if (devSettings == null) {
                throw new IllegalStateException("Dev PayPal config missing while dev profile is active");
            }
            return devSettings.getWebhookId();
        }
        return liveSettings.getSettings().getWebhookId();
    }

    public String getApiBase() {
        return isDev
                ? "https://api-m.sandbox.paypal.com"
                : "https://api-m.paypal.com";
    }

    // ---------------------------------------------------------------------
    // 🔹 OAuth Token
    // ---------------------------------------------------------------------
    public String getAccessToken() {
        String credentials = getClientId() + ":" + getClientSecret();
        String base64Creds = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + base64Creds);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request =
                new HttpEntity<>("grant_type=client_credentials", headers);

        Map<String, Object> response = restTemplate.postForObject(
                getApiBase() + "/v1/oauth2/token",
                request,
                Map.class
        );

        return (String) response.get("access_token");
    }

    // ---------------------------------------------------------------------
    // 🔹 Webhook Signature Verification
    // ---------------------------------------------------------------------
    public boolean verifySignature(Map<String, Object> verificationPayload) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getAccessToken());

        HttpEntity<Map<String, Object>> req =
                new HttpEntity<>(verificationPayload, headers);

        Map<String, Object> response = restTemplate.postForObject(
                getApiBase() + "/v1/notifications/verify-webhook-signature",
                req,
                Map.class
        );

        String status = (String) response.get("verification_status");
        return "SUCCESS".equalsIgnoreCase(status);
    }

    // ---------------------------------------------------------------------
    // 🔹 Subscription APIs
    // ---------------------------------------------------------------------
    public JsonNode getSubscription(String subscriptionId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken());

        ResponseEntity<String> response = restTemplate.exchange(
                getApiBase() + "/v1/billing/subscriptions/" + subscriptionId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        try {
            return new ObjectMapper().readTree(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse PayPal subscription response", e);
        }
    }

    public JsonNode getSubscriptionTransactions(String subscriptionId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken());

        ResponseEntity<String> response = restTemplate.exchange(
                getApiBase()
                        + "/v1/billing/subscriptions/"
                        + subscriptionId
                        + "/transactions?start_time=2000-01-01T00:00:00Z&end_time=2099-01-01T00:00:00Z",
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
