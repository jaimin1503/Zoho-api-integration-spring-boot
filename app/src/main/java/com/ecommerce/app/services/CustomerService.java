
package com.ecommerce.app.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CustomerService {

    @Value("${zoho.organization.id}")
    private String organizationId;

    @Value("${zoho.accessToken}")
    private String accessToken;

    private static final String BASE_URL = "https://www.zohoapis.in/invoice/v3/contacts";

    public String createCustomer(String json) throws IOException {
        return sendRequest(BASE_URL, "POST", json);
    }

    public String getCustomers() throws IOException {
        return sendRequest(BASE_URL, "GET", null);
    }

    public String getCustomerById(String contactId) throws IOException {
        return sendRequest(BASE_URL + "/" + contactId, "GET", null);
    }

    public String updateCustomer(String contactId, String json) throws IOException {
        return sendRequest(BASE_URL + "/" + contactId, "PUT", json);
    }

    public String deleteCustomer(String contactId) throws IOException {
        return sendRequest(BASE_URL + "/" + contactId, "DELETE", null);
    }

    public String activateCustomer(String contactId) throws IOException {
        return sendRequest(BASE_URL + "/" + contactId + "/active", "POST", null);
    }

    public String deactivateCustomer(String contactId) throws IOException {
        return sendRequest(BASE_URL + "/" + contactId + "/inactive", "POST", null);
    }

    private String sendRequest(String urlString, String method, String jsonInputString) throws IOException {
        HttpURLConnection connection = createConnection(urlString, method);
        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
            sendRequestBody(connection, jsonInputString);
        }

        return handleResponse(connection);
    }

    private HttpURLConnection createConnection(String urlString, String method) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-com-zoho-invoice-organizationid", organizationId);
        connection.setRequestProperty("Authorization", "Zoho-oauthtoken " + accessToken);
        connection.setDoOutput(true);
        return connection;
    }

    private void sendRequestBody(HttpURLConnection connection, String jsonInputString) throws IOException {
        if (jsonInputString != null) {
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        }
    }

    private String handleResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        StringBuilder response = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                responseCode < HttpURLConnection.HTTP_BAD_REQUEST ? connection.getInputStream() : connection.getErrorStream(), "utf-8"))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            throw new IOException("Error: " + responseCode + " Response: " + response.toString());
        }

        return response.toString();
    }
}
