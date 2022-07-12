package com.project.store.service;

import com.project.store.model.category.Category;
import com.project.store.model.product.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product productToBeAdded);

    Product getProduct(Integer id);

    List<Product> getAllProducts();

    Product updateProduct(Product productToBeUpdated);

    Product updateProductAndCategory(Product productToBeUpdated);

    void deleteProduct(Integer id);
}
