//package com.ecommerce.app.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.*;
//
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "address")
//@Data
//@Entity
//public class Address {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long addressId;
//
//    @NotBlank
//    @Size(min = 5, message = "Street should have at least 5 characters")
//    private String street;
//
//    @NotBlank
//    @Size(min = 5, message = "Building name should have at least 5 characters")
//    private String buildingName;
//
//    @NotBlank
//    @Size(min = 5, message = "City should have at least 5 characters")
//    private String city;
//
//    @NotBlank
//    @Size(min = 5, message = "State should have at least 5 characters")
//    private String state;
//
//    @NotBlank
//    @Size(min = 5, message = "Country should have at least 5 characters")
//    private String country;
//
//    @NotBlank
//    @Size(min = 6, message = "Pincode should have at least 6 characters")
//    private String pinCode;
//}
