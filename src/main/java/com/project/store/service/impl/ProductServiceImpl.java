package com.project.store.service.impl;


import com.project.store.exceptions.messages.ExceptionMessages;
import com.project.store.exceptions.product.ProductNotFoundException;
import com.project.store.model.product.Product;
import com.project.store.repository.filter.ProductFilterRepository;
import com.project.store.repository.ProductRepository;
import com.project.store.repository.filter.SearchCriteria;
import com.project.store.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private CategoryServiceImpl categoryService;

    private ProductFilterRepository productFilterRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryServiceImpl categoryService, ProductFilterRepository productFilterRepository) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productFilterRepository = productFilterRepository;
    }

    @Override
    public Product addProduct(Product productToBeAdded) {
        return productRepository.save(productToBeAdded);
    }

    @Override
    public Product getProduct(Integer id) {
        return findProductById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Product productToBeUpdated) {
        Product updatedProduct = findProductById(productToBeUpdated.getId());
        updatedProduct.setName(productToBeUpdated.getName());
        updatedProduct.setPrice(productToBeUpdated.getPrice());

        return productRepository.save(updatedProduct);
    }

    @Override
    public Product updateProductCategory(Product productToBeUpdated) {
        Product updatedProductCategory = findProductById(productToBeUpdated.getId());
        updatedProductCategory.setCategory(productToBeUpdated.getCategory());
        return productRepository.save(productToBeUpdated);
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchProducts(List<SearchCriteria> searchCriteria) {
        return productFilterRepository.filter(searchCriteria);
    }

    private Product findProductById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(ExceptionMessages.PRODUCT_NOT_FOUND.getErrorMessage(),
                    ExceptionMessages.PRODUCT_NOT_FOUND.getHttpStatusCode());
        }
        return optionalProduct.get();
    }
}
