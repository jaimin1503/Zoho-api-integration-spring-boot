package com.ecommerce.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Address {

    @Column(insertable = false, updatable = false)
    private String attention;

    @Column(insertable = false, updatable = false)
    private String address;

    @Column(insertable = false, updatable = false)
    private String street2;

    @Column(insertable = false, updatable = false)
    private String stateCode;

    @Column(insertable = false, updatable = false)
    private String city;

    @Column(insertable = false, updatable = false)
    private String state;

    @Column(insertable = false, updatable = false)
    private Integer zip;

    @Column(insertable = false, updatable = false)
    private String country;

    @Column(insertable = false, updatable = false)
    private String fax;

    @Column(insertable = false, updatable = false)
    private String phone;

    // Add getters and setters here
}
