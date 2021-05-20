package com.michael.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndController {


    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/shop")
    public String shop() {
        return "shop";
    }

    @GetMapping("/product-details")
    public String productDetails() {
        return "product-detail";
    }

    @GetMapping("/cart")
    public String shoppingCart() {
        return "shopping-cart";
    }

    @GetMapping("checkout")
    public String checkout() {
        return "checkout";
    }

    @GetMapping("confirmation")
    public String confirmation() {
        return "confirmation";
    }

}
