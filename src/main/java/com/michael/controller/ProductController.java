package com.michael.controller;

import com.michael.model.Product;
import com.michael.service.contracts.ICategoryService;
import com.michael.service.contracts.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequestMapping("/admin/product")
@Controller
public class ProductController {

    @Autowired
    IProductService productService;

    @Autowired
    ICategoryService categoryService;

    @GetMapping("")
    public String index(Model model) {

        model.addAttribute("products", productService.viewAllProducts());

        return  "admin/product/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.viewAllCategories());

        return "admin/product/create";
    }

    @PostMapping("/store")
    public String store(@Valid Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            return "admin/product/create";
        }
        Long id = product.getId();

        productService.addProduct(product);
        if (id == null) {
            redirectAttributes.addFlashAttribute("product_success", "Product Added Successfully");
        } else {
            redirectAttributes.addFlashAttribute("product_success", "Product Updated Successfully");
        }

        return "redirect:/admin/product";

    }

    @GetMapping("/{slug}/edit")
    public String edit(@PathVariable(value = "slug") String slug, Model model) {
        Product product = productService.getProductBySlug(slug);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.viewAllCategories());
        model.addAttribute("edit", true);
        return  "admin/product/create";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable(value = "id") long id, RedirectAttributes redirectAttributes) {
        productService.deleteProductById(id);

        redirectAttributes.addFlashAttribute("delete_product", "Product Deleted Successfully");
        return "redirect:/admin/product";
    }
}
