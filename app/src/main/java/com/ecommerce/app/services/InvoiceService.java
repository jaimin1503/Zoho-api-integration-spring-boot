package com.ecommerce.app.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class InvoiceService {

    @Value("${zoho.organization.id}")
    private String organizationId;

    @Value("${zoho.accessToken}")
    private String accessToken;

    private final ZohoHttpService zohoHttpService;

    public InvoiceService(ZohoHttpService zohoHttpService) {
        this.zohoHttpService = zohoHttpService;
    }

    public String createInvoice(String json) throws IOException {
        return sendPostRequest("https://www.zohoapis.in/invoice/v3/invoices", json);
    }

    public String getInvoices() throws IOException {
        return sendGetRequest("https://www.zohoapis.in/invoice/v3/invoices");
    }

    public String updateInvoice(String invoiceId, String json) throws IOException {
        return sendPutRequest("https://www.zohoapis.in/invoice/v3/invoices/" + invoiceId, json);
    }

    public String getInvoiceById(String invoiceId) throws IOException {
        return sendGetRequest("https://www.zohoapis.in/invoice/v3/invoices/" + invoiceId);
    }

    public void deleteInvoice(String invoiceId) throws IOException {
        sendDeleteRequest("https://www.zohoapis.in/invoice/v3/invoices/" + invoiceId);
    }

    public String updateCustomFields(String invoiceId, String json) throws IOException {
        return sendPutRequest("https://www.zohoapis.in/invoice/v3/invoices/" + invoiceId + "/customfields", json);
    }

    public String changeInvoiceStatus(String invoiceId, String status) throws IOException {
        return sendPostRequest("https://www.zohoapis.in/invoice/v3/invoices/" + invoiceId + "/status/" + status, "");
    }

    public String emailInvoice(String invoiceId) throws IOException {
        return sendPostRequest("https://www.zohoapis.in/invoice/v3/invoices/" + invoiceId + "/email", "");
    }

    public String getEmailDetails(String invoiceId) throws IOException {
        return sendGetRequest("https://www.zohoapis.in/invoice/v3/invoices/" + invoiceId + "/email");
    }

    public String emailInvoices(String invoiceIdsJson) throws IOException {
        return sendPostRequest("https://www.zohoapis.in/invoice/v3/invoices/email", invoiceIdsJson);
    }

    // Helper methods for HTTP requests
    private String sendPostRequest(String url, String json) throws IOException {
        return zohoHttpService.sendPostRequest(url, json, organizationId, accessToken);
    }

    private String sendGetRequest(String url) throws IOException {
        return zohoHttpService.sendGetRequest(url, organizationId, accessToken);
    }

    private String sendPutRequest(String url, String json) throws IOException {
        return zohoHttpService.sendPutRequest(url, json, organizationId, accessToken);
    }

    private void sendDeleteRequest(String url) throws IOException {
        zohoHttpService.sendDeleteRequest(url, organizationId, accessToken);
    }

}
