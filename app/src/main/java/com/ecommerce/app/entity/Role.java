//package com.ecommerce.app.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Column;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@Table(name = "roles")
//@NoArgsConstructor
//@AllArgsConstructor
//public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long roleId;
//
//    @NotBlank(message = "Role name cannot be blank")
//    @Size(min = 3, max = 20, message = "Role name must be between 3 and 20 characters")
//    @Column(nullable = false, unique = true)
//    private String roleName;
//}
