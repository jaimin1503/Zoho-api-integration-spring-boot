package com.ecommerce.app.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String salutation;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String mobile;
    private Boolean isPrimaryContact;

    // Add getters and setters here
}

