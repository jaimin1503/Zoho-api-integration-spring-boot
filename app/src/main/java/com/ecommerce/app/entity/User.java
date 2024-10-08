//package com.ecommerce.app.entity;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinTable;
//import jakarta.persistence.ManyToMany;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//
//import lombok.*;
//
//@Entity
//@Table(name = "users")
//@Data
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;
//
//    @Size(min = 5, max = 30, message = "First Name must be between 5 and 30 characters long")
//    @Pattern(regexp = "^[a-zA-Z]*$", message = "First Name must not contain numbers or special characters")
//    private String firstName;
//
//    @Size(min = 5, max = 30, message = "Last Name must be between 5 and 30 characters long")
//    @Pattern(regexp = "^[a-zA-Z]*$", message = "Last Name must not contain numbers or special characters")
//    private String lastName;
//
//    @Size(min = 10, max = 15, message = "Mobile Number must be between 10 and 15 digits long")
//    @Pattern(regexp = "^\\d{10,15}$", message = "Mobile Number must contain only numbers")
//    private String mobileNumber;
//
//    @Email
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    @Size(min = 8, message = "Password must be at least 8 characters long")
//    private String password;
//
//    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
//    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles = new HashSet<>();
//
//    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//    @JoinTable(name = "user_address", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "address_id"))
//    private Set<Address> addresses = new HashSet<>();
//
//    @OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
//    private Cart cart;
//}
