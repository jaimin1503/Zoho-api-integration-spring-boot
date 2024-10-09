package com.ecommerce.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Assuming you're using Long as the identifier

    private String contactName;
    private String companyName;
    private Integer paymentTerms;
    private Long currencyId;
    private String website;
    private String notes;
    private String vatRegNo;
    private Long taxRegNo;
    private String countryCode;
    private String vatTreatment;
    private String taxTreatment;
    private String legalName;
    private Boolean isTdsRegistered;
    private String placeOfContact;
    private String gstNo;
    private String facebook;
    private String twitter;

    @Embedded
    private Address billingAddress;

    @Embedded
    private Address shippingAddress;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<ContactPerson> contactPersons;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<CustomField> customFields;

    // Add getters and setters here
}
