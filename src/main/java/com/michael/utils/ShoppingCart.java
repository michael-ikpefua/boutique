package com.michael.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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

    public double getTotalCartPrice(ArrayList<ShoppingCart> productsInSession) {
        double totalCartPrice = 0.0;
        if (productsInSession != null) {
            totalCartPrice = productsInSession.stream()
                    .map(product -> product.getProductAmount() * product.getProductQuantity())
                    .reduce(0.0, Double::sum);
        }

        return totalCartPrice;
    }
}
