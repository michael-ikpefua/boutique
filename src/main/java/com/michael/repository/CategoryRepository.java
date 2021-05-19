package com.michael.repository;

import com.michael.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    boolean existsBySlug(String slug);

    Optional<Category> findBySlug(String slug);

}
