package com.ecommerce.app.controller;

import com.ecommerce.app.entity.OAuthToken;
import com.ecommerce.app.repo.OAuthTokenRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
public class OAuthController {

    @Autowired
    private OAuthTokenRepository oAuthTokenRepository;

    @Value("${zoho.client.id}")
    private String CLIENT_ID;

    @Value("${zoho.client.secret}")
    private String CLIENT_SECRET;

    private final String TOKEN_URL = "https://accounts.zoho.in/oauth/v2/token";

    @GetMapping("/login/oauth2/code/zoho")
    public ResponseEntity<Map<String, Object>> handleZohoOAuth(@RequestParam String code) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // Create a URL object
            URL url = new URL(TOKEN_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            // Build the request body
            String requestBody = "client_id=" + CLIENT_ID
                    + "&client_secret=" + CLIENT_SECRET
                    + "&redirect_uri=http://localhost:5173/get-code"
                    + "&code=" + code
                    + "&grant_type=authorization_code";

            // Write the request body to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.getBytes());
                os.flush();
            }

            // Read the response
            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    responseCode == HttpURLConnection.HTTP_OK ? connection.getInputStream() : connection.getErrorStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
            }

            System.out.println("Response: " + response.toString());

            // Handle the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String responseBody = response.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                // Safely retrieve JSON values with null checks
                JsonNode accessTokenNode = jsonNode.get("access_token");
                JsonNode refreshTokenNode = jsonNode.get("refresh_token");
                JsonNode expiresInNode = jsonNode.get("expires_in");

                if (accessTokenNode != null && expiresInNode != null) {
                    String accessToken = accessTokenNode.asText();
                    String refreshToken = refreshTokenNode != null ? refreshTokenNode.asText() : null;
                    Instant expiryDate = Instant.now().plusSeconds(expiresInNode.asLong());

                    // Save the tokens in the database
                    OAuthToken oAuthToken = new OAuthToken();
                    oAuthToken.setAccessToken(accessToken);
                    oAuthToken.setRefreshToken(refreshToken);
                    oAuthToken.setExpiryDate(expiryDate);
                    oAuthTokenRepository.save(oAuthToken);

                    System.out.println("Access Token saved successfully.");

                    // Add access token and expiry to the response map
                    responseMap.put("access_token", accessToken);
                    responseMap.put("expiry_date", expiryDate.toString());
                } else {
                    System.out.println("Error: Missing 'access_token' or 'expires_in' in the response.");
                    responseMap.put("error", "Missing 'access_token' or 'expires_in'.");
                }
            } else {
                System.out.println("Failed to get access token: " + responseCode + " - " + response);
                responseMap.put("error", "Failed to get access token: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", "An error occurred while processing the request.");
        }

        return ResponseEntity.ok(responseMap);  // Return response to frontend
    }
}
