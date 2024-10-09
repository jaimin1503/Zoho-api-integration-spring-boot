package com.ecommerce.app.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ZohoHttpService {

    public String sendPostRequest(String urlString, String jsonInputString, String organizationId, String accessToken) throws IOException {
        System.out.println("Sending request to: " + urlString);
        System.out.println("With payload: " + jsonInputString);

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-com-zoho-invoice-organizationid", organizationId);
        connection.setRequestProperty("Authorization", "Zoho-oauthtoken " + accessToken);
        connection.setDoOutput(true);

        // Send request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Handle response
        return handleResponse(connection);
    }

    public String sendGetRequest(String url, String organizationId, String accessToken) throws IOException {
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-com-zoho-invoice-organizationid", organizationId);
        connection.setRequestProperty("Authorization", "Zoho-oauthtoken " + accessToken);

        // Handle response
        return handleResponse(connection);
    }

    public String sendPutRequest(String urlString, String jsonInputString, String organizationId, String accessToken) throws IOException {
        System.out.println("Sending PUT request to: " + urlString);
        System.out.println("With payload: " + jsonInputString);

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-com-zoho-invoice-organizationid", organizationId);
        connection.setRequestProperty("Authorization", "Zoho-oauthtoken " + accessToken);
        connection.setDoOutput(true);

        // Send request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Handle response
        return handleResponse(connection);
    }

    public void sendDeleteRequest(String urlString, String organizationId, String accessToken) throws IOException {
        System.out.println("Sending DELETE request to: " + urlString);

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("X-com-zoho-invoice-organizationid", organizationId);
        connection.setRequestProperty("Authorization", "Zoho-oauthtoken " + accessToken);

        // Handle response
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_NO_CONTENT) { // No content for a successful delete
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            System.err.println("Error Response: " + response.toString());
            throw new IOException("Error: " + responseCode + " Response: " + response.toString());
        }
    }

    private String handleResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        StringBuilder response = new StringBuilder();

        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            System.err.println("Error Response: " + response.toString());
            throw new IOException("Error: " + responseCode + " Response: " + response.toString());
        }

        return response.toString();
    }
}
