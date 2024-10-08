package com.ecommerce.app.services;

import com.ecommerce.app.entity.OAuthToken;
import com.ecommerce.app.repo.OAuthTokenRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;

@Service
public class OAuthService {

    @Autowired
    private OAuthTokenRepository oAuthTokenRepository;

    @Value("${zoho.client.id}")
    private String CLIENT_ID;

    @Value("${zoho.client.secret}")
    private String CLIENT_SECRET;

    private final String TOKEN_URL = "https://accounts.zoho.in/oauth/v2/token";

    public JsonNode fetchAndSaveTokens(String code) throws Exception {
        URL url = new URL(TOKEN_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        String requestBody = "client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=http://localhost:9090/login/oauth2/code/zoho"
                + "&code=" + code
                + "&grant_type=authorization_code";

        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestBody.getBytes());
            os.flush();
        }

        // Parse the response JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(connection.getInputStream());

        String accessToken = responseJson.get("access_token").asText();
        String refreshToken = responseJson.get("refresh_token").asText();
        Instant expiryDate = Instant.now().plusSeconds(responseJson.get("expires_in").asLong());

        // Save the refresh token to the database
        OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.setRefreshToken(refreshToken);
        oAuthToken.setExpiryDate(expiryDate);
        oAuthTokenRepository.save(oAuthToken);

        // Return the access token and expiry time for client-side storage
        return responseJson;
    }
}
