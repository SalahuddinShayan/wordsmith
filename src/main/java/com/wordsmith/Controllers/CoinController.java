package com.wordsmith.Controllers;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wordsmith.Entity.User;
import com.wordsmith.Util.EmailMasker;
import com.wordsmith.Util.PayPalClient;

import jakarta.servlet.http.HttpSession;


@Controller
public class CoinController {

    private final PayPalClient payPalClient;
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(CoinController.class);

    public CoinController(PayPalClient payPalClient) {
        this.payPalClient = payPalClient;
    }


    @GetMapping("/coin")
    public String getMethodName(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            logger.warn("Unauthorized access to /coins — redirecting to login");
            return "redirect:/auth/loginpage";
        }

        String maskedUser = EmailMasker.mask(user.getUsername());
        logger.info("Loading Coin page for user={}", maskedUser);

        model.addAttribute("paypalClientId", payPalClient.getClientId());


        return "buycoin";
    }
    
    private static class CoinPack {
        BigDecimal usd;
        long coins;

        CoinPack(BigDecimal usd, long coins) {
            this.usd = usd;
            this.coins = coins;
        }
    }

    private CoinPack resolvePack(String pack) {
        return switch (pack) {
            case "SMALL"  -> new CoinPack(new BigDecimal("1.00"), 80);
            case "MEDIUM" -> new CoinPack(new BigDecimal("5.00"), 450);
            case "LARGE"  -> new CoinPack(new BigDecimal("10.00"), 1000);
            default -> throw new IllegalArgumentException("Invalid coin pack");
        };
    }
 
    @PostMapping("/paypal/create-coin-order")
    public ResponseEntity<Map<String, String>> createCoinOrder(
            @RequestParam String pack,
            HttpSession session
    ) throws JsonProcessingException {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "Unauthorized"));
        }
        
        CoinPack packInfo = resolvePack(pack);
        String username = user.getUsername();

        ObjectNode orderRequest = mapper.createObjectNode();
        orderRequest.put("intent", "CAPTURE");

        ArrayNode purchaseUnits = orderRequest.putArray("purchase_units");

        ObjectNode unit = purchaseUnits.addObject();
        unit.put("custom_id", username); // 🔥 THIS IS CRITICAL
        unit.put("description", "Coin Pack " + pack);

        ObjectNode amount = unit.putObject("amount");
        amount.put("currency_code", "USD");
        amount.put("value", packInfo.usd.toString());

        
        String accessToken = payPalClient.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(orderRequest.toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                payPalClient.getApiBase() + "/v2/checkout/orders",
                request,
                String.class
        );

        JsonNode paypalResponse;
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("PayPal order creation failed");
        }

        try {
            paypalResponse = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse PayPal response", e);
        }
        if (paypalResponse.get("id") == null) {
            throw new IllegalStateException("PayPal order ID missing");
        }
        String orderId = paypalResponse.get("id").asText();

        return ResponseEntity.ok(
                Map.of("orderId", orderId)
        );


    }

}
