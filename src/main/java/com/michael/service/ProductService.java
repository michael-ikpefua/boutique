package com.michael.service;

import com.michael.model.Product;
import com.michael.repository.ProductRepository;
import com.michael.service.contracts.ICategoryService;
import com.michael.service.contracts.IProductService;
import com.michael.utils.Money;
import com.michael.utils.Slug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    Slug slug;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    Money money;

    @Override
    public void addProduct(Product product) {

        product.setSlug(slug.makeSlug(product.getTitle()));


        Product checkIfProductSlugExist = this.getProductBySlug(product.getSlug());

        if (checkIfProductSlugExist != null) {
            product.setSlug(product.getSlug() + "-" + (checkIfProductSlugExist.getId() + 1));
        }
        product.setPrice(money.formatMoneyToLocalCurrency(product.getPrice()));

        productRepository.save(product);
    }

    @Override
    public Product getProductBySlug(String slug) {
        Product product = null;
        Optional <Product> optionalProduct = productRepository.findProductBySlug(slug);
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
            return product;
        }

        return product;
    }

    @Override
    public List viewAllProducts() {
//        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
//        return productRepository.findAll(pageable);
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(long id) {

        Optional<Product> productOptional = productRepository.findById(id);

        Product product = null;

        if (productOptional.isPresent()) {
            product = productOptional.get();
            return product;
        }

        return product;

    }
}
