package com.ecommerce.app.services;

import com.ecommerce.app.entity.OAuthToken;
import com.ecommerce.app.repo.OAuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

@Service
public class InvoiceService {

    private final RestTemplate restTemplate;

    @Autowired
    private OAuthTokenRepository oAuthTokenRepository;

    @Value("${zoho.organization.id}")
    private String organizationId;


    @Value("${zoho.client.id}")
    private String CLIENT_ID;

    @Value("${zoho.client.secret}")
    private String CLIENT_SECRET;

    @Value("${zoho.token.refresh.url}")
    private String tokenRefreshUrl; // URL to refresh token

    public InvoiceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> createInvoice(String jsonPayload) {
        String accessToken = getAccessToken();
        String url = "https://www.zohoapis.com/invoice/v3/invoices";

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Zoho-oauthtoken " + accessToken);
        headers.set("X-com-zoho-invoice-organizationid", organizationId);
        headers.set("Content-Type", "application/json");

        // Create the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

        // Make the POST request
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    private String getAccessToken() {
        Optional<OAuthToken> tokenOpt = oAuthTokenRepository.findAll().stream().findFirst();
        if (tokenOpt.isPresent()) {
            OAuthToken oAuthToken = tokenOpt.get();
            if (oAuthToken.isExpired()) {
                // Refresh the token
                refreshAccessToken(oAuthToken);
            }
            return oAuthToken.getAccessToken();
        }
        throw new RuntimeException("No OAuth Token found");
    }

    private void refreshAccessToken(OAuthToken oAuthToken) {
        // Implement token refresh logic here
        // You may need to call Zoho's API to refresh the token using the refresh token
        // Here's a placeholder for that logic:

        String refreshToken = oAuthToken.getRefreshToken();

        // Set the request body for refreshing the token
        String requestBody = "client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET +
                "&refresh_token=" + refreshToken +
                "&grant_type=refresh_token";

        // Call the refresh token API
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the POST request to refresh the token
        ResponseEntity<String> response = restTemplate.exchange(tokenRefreshUrl, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // Parse the new access token and expiry
            // For example purposes, you should use proper JSON parsing here
            String newAccessToken = extractAccessToken(response.getBody());
            Instant newExpiryDate = extractExpiryDate(response.getBody());

            // Update the token in the database
            oAuthToken.setAccessToken(newAccessToken);
            oAuthToken.setExpiryDate(newExpiryDate);
            oAuthTokenRepository.save(oAuthToken);
        } else {
            throw new RuntimeException("Failed to refresh access token: " + response.getBody());
        }
    }

    private String extractAccessToken(String responseBody) {
        // Implement JSON parsing logic here to extract the access token
        // For example, you could use ObjectMapper from Jackson library
        return ""; // Replace with actual access token extraction logic
    }

    private Instant extractExpiryDate(String responseBody) {
        // Implement JSON parsing logic here to extract the expiry date
        return Instant.now().plusSeconds(3600); // Replace with actual logic
    }
}
