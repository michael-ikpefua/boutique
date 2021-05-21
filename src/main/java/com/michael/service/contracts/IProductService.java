package com.michael.service.contracts;

import com.michael.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    void addProduct(Product product);

    Product getProductBySlug(String slug);

    List viewAllProducts();

    void deleteProductById(long id);

    Product getProductById(long id);
}
