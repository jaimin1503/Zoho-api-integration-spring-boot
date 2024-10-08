package com.ecommerce.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/zoho")
@CrossOrigin(origins = "http://localhost:5173")
public class ZohoOAuthController {

    @Value("${zoho.client.id}")
    private String CLIENT_ID;

    private static final String REDIRECT_URI = "http://localhost:5173/get-code"; // Your backend's redirect URI
    private static final String SCOPE = "ZohoInvoice.contacts.ALL,ZohoInvoice.settings.ALL,ZohoInvoice.estimates.ALL,ZohoInvoice.invoices.ALL,ZohoSubscriptions.customers.ALL";
    private static final String AUTH_URL = "https://accounts.zoho.in/oauth/v2/auth";

    @GetMapping("/auth")
    public String redirectToZohoOAuth() {
        String authRedirectUrl = AUTH_URL + "?scope=" + SCOPE +
                "&client_id=" + CLIENT_ID +
                "&response_type=code" +
                "&access_type=online" +
                "&redirect_uri=" + REDIRECT_URI +
                "&prompt=consent";

        return "redirect:" + authRedirectUrl;
    }
}
