package com.ecommerce.app.controller;

import com.ecommerce.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Endpoint to create a new customer.
     *
     * @param customerJson JSON representation of the customer data.
     * @return ResponseEntity containing the response from the customer creation request.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody String customerJson) {
        try {
            String response = customerService.createCustomer(customerJson);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return handleIOException(e);
        }
    }

    /**
     * Endpoint to retrieve a list of all customers.
     *
     * @return ResponseEntity containing the list of customers in JSON format.
     */
    @GetMapping("/all")
    public ResponseEntity<String> getCustomers() {
        try {
            String response = customerService.getCustomers();
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return handleIOException(e);
        }
    }

    /**
     * Endpoint to retrieve a customer by their contact ID.
     *
     * @param contactId The ID of the contact to retrieve.
     * @return ResponseEntity containing the customer data in JSON format.
     */
    @GetMapping(value = "/{contact_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomerById(@PathVariable("contact_id") String contactId) {
        try {
            String response = customerService.getCustomerById(contactId);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return handleIOException(e);
        }
    }

    /**
     * Endpoint to update an existing customer by their contact ID.
     *
     * @param contactId The ID of the contact to update.
     * @param customerJson JSON representation of the updated customer data.
     * @return ResponseEntity containing the response from the update request.
     */
    @PutMapping(value = "/{contact_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCustomer(@PathVariable("contact_id") String contactId, @RequestBody String customerJson) {
        try {
            String response = customerService.updateCustomer(contactId, customerJson);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return handleIOException(e);
        }
    }

    /**
     * Endpoint to delete a customer by their contact ID.
     *
     * @param contactId The ID of the contact to delete.
     * @return ResponseEntity containing the response from the delete request.
     */
    @DeleteMapping(value = "/{contact_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCustomer(@PathVariable("contact_id") String contactId) {
        try {
            String response = customerService.deleteCustomer(contactId);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return handleIOException(e);
        }
    }

    /**
     * Endpoint to activate a customer by their contact ID.
     *
     * @param contactId The ID of the contact to activate.
     * @return ResponseEntity containing the response from the activation request.
     */
    @PostMapping(value = "/{contact_id}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> activateCustomer(@PathVariable("contact_id") String contactId) {
        try {
            String response = customerService.activateCustomer(contactId);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return handleIOException(e);
        }
    }

    /**
     * Endpoint to deactivate a customer by their contact ID.
     *
     * @param contactId The ID of the contact to deactivate.
     * @return ResponseEntity containing the response from the deactivation request.
     */
    @PostMapping(value = "/{contact_id}/inactive", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deactivateCustomer(@PathVariable("contact_id") String contactId) {
        try {
            String response = customerService.deactivateCustomer(contactId);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return handleIOException(e);
        }
    }

    /**
     * Handles IOExceptions and returns a bad request response with the exception message.
     *
     * @param e The IOException that occurred.
     * @return ResponseEntity containing the error message.
     */
    private ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
