package com.project.store.service;

import com.project.store.model.category.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    ProductCategory saveProductCategory(ProductCategory productCategoryToBeSaved);

    ProductCategory getProductCategory(Integer id);

    List<ProductCategory> getAllProductCategories();

    ProductCategory updateProductCategory(ProductCategory productCategoryToBeUpdated);

    void deleteProductCategory(Integer id);
}
