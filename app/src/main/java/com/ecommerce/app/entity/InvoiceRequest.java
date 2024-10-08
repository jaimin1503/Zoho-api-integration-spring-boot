package com.ecommerce.app.entity;

import java.util.List;

public class InvoiceRequest {
    private String invoiceId;
    private String invoiceNumber;
    private String date;
    private String dueDate;
    private String customer_id;
    private String customerName;
    private String email;
    private String currencyCode;
    private String status;
    private String placeOfSupply;
    private boolean paymentReminderEnabled;
    private double paymentMade;
    private List<LineItem> line_items;
    private double subTotal;
    private double taxTotal;
    private double total;
    private double balance;
    private String gstNo;
    private String taxTreatment;
    private BillingAddress billingAddress;
    private ShippingAddress shippingAddress;
    private String notes;
    private String terms;

    // Getters and Setters

    public static class LineItem {
        private String lineItemId;
        private String name;
        private String description;
        private double quantity;
        private double rate;
        private double itemTotal;
        private List<Tax> taxes;

        // Getters and Setters
    }

    public static class Tax {
        private String taxName;
        private double taxAmount;

        // Getters and Setters
    }

    public static class BillingAddress {
        private String street;
        private String city;
        private String state;
        private String zip;
        private String country;

        // Getters and Setters
    }

    public static class ShippingAddress {
        private String street;
        private String city;
        private String state;
        private String zip;
        private String country;

        // Getters and Setters
    }
}
