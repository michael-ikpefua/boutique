package com.michael.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "product_title")
    private String productTitle;
    @Column(name = "product_amount")
    private String productAmount;
    @Column(name = "product_quantity")
    private int productQuantity;
    @Column(columnDefinition = "cart_total_price")
    private String cartTotalPrice;

}
