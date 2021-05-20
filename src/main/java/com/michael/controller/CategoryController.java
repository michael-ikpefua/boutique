package com.michael.controller;

import com.michael.model.Category;
import com.michael.service.contracts.ICategoryService;
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
import java.util.ArrayList;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    @GetMapping( "")
    public String index(Model model) {
        Iterable<Category> categories = categoryService.viewAllCategories();

        model.addAttribute("category", new Category());
        model.addAttribute("categories", categories);

        return "admin/category/index";
    }

    @PostMapping("/store")
    public String store(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Long checkId = category.getId();

        if (bindingResult.hasErrors()) {
            return "admin/category/index";
        }

        categoryService.addCategory(category);

        if (checkId == null) {
            redirectAttributes.addFlashAttribute("category_success", "Category Added Successfully!!!");
        } else {
            redirectAttributes.addFlashAttribute("category_success", "Category Updated Succesfully!!!");
        }

        return "redirect:/admin/category";
    }
    @GetMapping("/{slug}/edit")
    public String edit(@PathVariable(value = "slug") String slug, Model model) {

        Category category = categoryService.getPostBySlug(slug);

        model.addAttribute("category", category);

        return "admin/category/edit";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable(value = "id") long id, RedirectAttributes redirectAttributes) {

        categoryService.deleteCategory(id);

        redirectAttributes.addFlashAttribute("delete_category", "Record Deleted");

        return "redirect:/admin/category";

    }


}
