package com.ecommerce.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.app.entity.OAuthToken;
import com.ecommerce.app.repo.OAuthTokenRepository;

import java.time.Instant;
import java.util.Optional;

@Service
public class OAuthService {
    @Autowired
    private OAuthTokenRepository oAuthTokenRepository;

    // Method to save token
    public void saveToken(String accessToken, String refreshToken, Instant expiryDate) {
        OAuthToken token = new OAuthToken();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setExpiryDate(expiryDate);
        oAuthTokenRepository.save(token);
    }

    // Optionally, method to retrieve the latest access token
    public Optional<OAuthToken> getLatestToken() {
        return oAuthTokenRepository.findTopByOrderByIdDesc();
    }
}
