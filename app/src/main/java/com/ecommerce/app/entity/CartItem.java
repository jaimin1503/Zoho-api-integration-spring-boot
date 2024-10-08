//package com.ecommerce.app.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//public class CartItem {
//
//    @Entity
//    @Table(name = "cart_items")
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public class cartItem {
//
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Long cartItemId;
//
//        @ManyToOne
//        @JoinColumn(name = "cart_id")
//        private Cart cart;
//
//        @ManyToOne
//        @JoinColumn(name = "product_id")
//        private Product product;
//
//        private Integer quantity;
//        private double discount;
//        private double productPrice;
//
//    }
//}
