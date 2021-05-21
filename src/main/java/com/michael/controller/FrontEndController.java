package com.michael.controller;

import com.michael.model.Category;
import com.michael.model.Product;
import com.michael.service.contracts.ICategoryService;
import com.michael.service.contracts.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class FrontEndController {

    @Autowired
    IProductService productService;

    @Autowired
    ICategoryService categoryService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products",  productService.viewAllProducts());
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("pageName", "Shop Page");
        model.addAttribute("categories", categoryService.viewAllCategories());
        model.addAttribute("products", productService.viewAllProducts());
        return "shop";
    }

    @GetMapping("/shop/{slug}")
    @Transactional
    public String shopByCategory(@PathVariable(value = "slug") String slug, Model model) {

        Category category = categoryService.getCategoryBySlug(slug);

        List<Product> products = category.getProducts();

        model.addAttribute("pageName",  category.getName() + " Category");
        model.addAttribute("categories", categoryService.viewAllCategories());
        model.addAttribute("products", products);

        return "shop";
    }

    @GetMapping("/product-details")
    public String productDetails() {
        return "product-detail";
    }

    @GetMapping("confirmation")
    public String confirmation() {
        return "confirmation";
    }

}
