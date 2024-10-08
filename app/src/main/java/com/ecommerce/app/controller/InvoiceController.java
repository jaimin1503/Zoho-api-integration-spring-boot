package com.ecommerce.app.controller;

import com.ecommerce.app.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/invoices")
    public ResponseEntity<String> createInvoice(@RequestBody String jsonPayload) {
        // Call the service to create an invoice
        ResponseEntity<String> response = invoiceService.createInvoice(jsonPayload);

        // Return the response from the service
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
