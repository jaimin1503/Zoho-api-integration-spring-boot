package com.ecommerce.app.controller;

import com.ecommerce.app.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // Creating an invoice
    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody String invoiceJson) {
        try {
            String jsonResponse = invoiceService.createInvoice(invoiceJson);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Content-Type", "application/json")
                    .body(jsonResponse);
        } catch (IOException e) {
            return handleException(e);
        }
    }

    // Retrieving a list of invoices
    @GetMapping
    public ResponseEntity<?> getInvoices() {
        try {
            String jsonResponse = invoiceService.getInvoices();
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonResponse);
        } catch (IOException e) {
            return handleException(e);
        }
    }

    // Updating an invoice
    @PutMapping("/{invoice_id}")
    public ResponseEntity<?> updateInvoice(@PathVariable String invoice_id, @RequestBody String invoiceJson) {
        try {
            String jsonResponse = invoiceService.updateInvoice(invoice_id, invoiceJson);
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonResponse);
        } catch (IOException e) {
            return handleException(e);
        }
    }

    // Retrieving a specific invoice
    @GetMapping("/{invoice_id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable String invoice_id) {
        try {
            String jsonResponse = invoiceService.getInvoiceById(invoice_id);
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonResponse);
        } catch (IOException e) {
            return handleException(e);
        }
    }

    // Deleting a specific invoice
    @DeleteMapping("/{invoice_id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable String invoice_id) {
        try {
            invoiceService.deleteInvoice(invoice_id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException e) {
            return handleException(e);
        }
    }

    // Updating custom fields of an invoice
    @PutMapping("/{invoice_id}/customfields")
    public ResponseEntity<?> updateCustomFields(@PathVariable String invoice_id, @RequestBody String customFieldsJson) {
        try {
            String jsonResponse = invoiceService.updateCustomFields(invoice_id, customFieldsJson);
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonResponse);
        } catch (IOException e) {
            return handleException(e);
        }
    }

    // Changing invoice status to sent
    @PostMapping("/{invoice_id}/status/sent")
    public ResponseEntity<?> markInvoiceAsSent(@PathVariable String invoice_id) {
        try {
            String jsonResponse = invoiceService.changeInvoiceStatus(invoice_id, "sent");
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonResponse);
        } catch (IOException e) {
            return handleException(e);
        }
    }

    // Changing invoice status to void
    @PostMapping("/{invoice_id}/status/void")
    public ResponseEntity<?> markInvoiceAsVoid(@PathVariable String invoice_id) {
        try {
            String jsonResponse = invoiceService.changeInvoiceStatus(invoice_id, "void");
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonResponse);
        } catch (IOException e) {
            return handleException(e);
        }
    }

    // Changing invoice status to draft
    @PostMapping("/{invoice_id}/status/draft")
    public ResponseEntity<?> markInvoiceAsDraft(@PathVariable String invoice_id) {
        try {
            String jsonResponse = invoiceService.changeInvoiceStatus(invoice_id, "draft");
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonResponse);
        } catch (IOException e) {
            return handleException(e);
        }
    }

    // Emailing the invoice
    @PostMapping("/{invoice_id}/email")
    public ResponseEntity<?> emailInvoice(@PathVariable String invoice_id) {
        try {
            String jsonResponse = invoiceService.emailInvoice(invoice_id);
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonResponse);
        } catch (IOException e) {
            return handleException(e);
        }
    }

    // Retrieving details related to the email sent for the invoice
    @GetMapping("/{invoice_id}/email")
    public ResponseEntity<?> getEmailDetails(@PathVariable String invoice_id) {
        try {
            String jsonResponse = invoiceService.getEmailDetails(invoice_id);
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonResponse);
        } catch (IOException e) {
            return handleException(e);
        }
    }

    // Emailing a list of invoices
    @PostMapping("/email")
    public ResponseEntity<?> emailInvoices(@RequestBody String invoiceIdsJson) {
        try {
            String jsonResponse = invoiceService.emailInvoices(invoiceIdsJson);
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonResponse);
        } catch (IOException e) {
            return handleException(e);
        }
    }

    // A generic method to handle exceptions and send JSON responses
    private ResponseEntity<?> handleException(Exception e) {
        // Create a custom error response
        ErrorResponse errorResponse = new ErrorResponse("An error occurred", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-Type", "application/json")
                .body(errorResponse);
    }

    // A simple ErrorResponse class to structure the error response as JSON
    static class ErrorResponse {
        private String error;
        private String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }
}
