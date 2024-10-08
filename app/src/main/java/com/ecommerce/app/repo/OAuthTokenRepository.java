package com.ecommerce.app.repo;

import com.ecommerce.app.entity.OAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository for OAuthToken

public interface OAuthTokenRepository extends JpaRepository<OAuthToken, Long> {
    Optional<OAuthToken> findTopByOrderByIdDesc(); // To get the latest token
}

