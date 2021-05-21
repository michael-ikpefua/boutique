package com.michael.controller;

import com.michael.service.contracts.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndController {

    @Autowired
    IProductService productService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products",  productService.viewAllProducts());
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

    @GetMapping("checkout")
    public String checkout() {
        return "checkout";
    }

    @GetMapping("confirmation")
    public String confirmation() {
        return "confirmation";
    }

}
