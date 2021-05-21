package com.michael.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ShoppingCart {
    private Long productId;
    private String productTitle;
    private double productAmount;
    private int productQuantity;
    private String cartPrice;
    private String cartTotal;
}
