package com.michael.service.contracts;

import com.michael.model.Category;


public interface ICategoryService {

    void addCategory(Category category);

    Iterable<Category> viewAllCategories();

    Category getPostBySlug(String slug);

    void deleteCategory(long slug);

}
