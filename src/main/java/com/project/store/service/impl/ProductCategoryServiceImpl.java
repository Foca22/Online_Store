package com.project.store.service.impl;

import com.project.store.exceptions.category.ProductCategoryNotFoundException;
import com.project.store.exceptions.messages.ExceptionMessages;
import com.project.store.model.category.ProductCategory;
import com.project.store.repository.ProductCategoryRepository;
import com.project.store.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    public final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository){
        this.productCategoryRepository = productCategoryRepository;
    }


    @Override
    public ProductCategory saveProductCategory(ProductCategory productCategoryToBeSaved) {
        return productCategoryRepository.save(productCategoryToBeSaved);
    }

    @Override
    public ProductCategory getProductCategory(Integer id) {
        return findProductCategoryById(id);
    }

    @Override
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory updateProductCategory(ProductCategory productCategoryToBeUpdated) {
        ProductCategory updatedProductCategory = findProductCategoryById(productCategoryToBeUpdated.getId());
        updatedProductCategory.setName(productCategoryToBeUpdated.getName());
        updatedProductCategory.setType(productCategoryToBeUpdated.getType());

        return productCategoryRepository.save(updatedProductCategory);
    }

    @Override
    public void deleteProductCategory(Integer id) {
        productCategoryRepository.deleteById(id);
    }

    private ProductCategory findProductCategoryById (Integer id) {
        Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(id);
        if(optionalProductCategory.isEmpty()){
            throw new ProductCategoryNotFoundException(ExceptionMessages.PRODUCT_CATEGORY_NOT_FOUND.getErrorMessage(),
                    ExceptionMessages.PRODUCT_CATEGORY_NOT_FOUND.getHttpStatusCode());
        }

        return optionalProductCategory.get();
    }
}
