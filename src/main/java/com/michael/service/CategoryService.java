package com.michael.service;

import com.michael.model.Category;
import com.michael.repository.CategoryRepository;
import com.michael.service.contracts.ICategoryService;
import com.michael.utils.Slug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    Slug slug;

    @Override
    public void addCategory(Category category) {

        category.setSlug(slug.makeSlug(category.getName()));
        Category checkSlotExist = getPostBySlug(category.getSlug());

        if (checkSlotExist != null) {
            category.setSlug(category.getSlug() + "-" + (checkSlotExist.getId() + 1));
        }

        categoryRepository.save(category);
    }

    @Override
    public Iterable<Category> viewAllCategories() {
        return  categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Category getPostBySlug(String slug) {
        Category category = null;
        Optional<Category> categoryOptional = categoryRepository.findBySlug(slug);

        if (categoryOptional.isPresent()) {
            category = categoryOptional.get();

            return category;
        }

        return category;
    }

    @Override
    public void deleteCategory(long id) {

        categoryRepository.deleteById(id);

    }

    @Override
    public Category getCategoryById(long id) {
        Category category = null;
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            category = optionalCategory.get();
            return  category;
        }

        return category;
    }
}
