package com.project.store.service.impl;

import com.project.store.exceptions.category.CategoryNotFoundException;
import com.project.store.exceptions.messages.ExceptionMessages;
import com.project.store.model.category.Category;
import com.project.store.model.product.Product;
import com.project.store.repository.CategoryRepository;
import com.project.store.repository.filter.CategoryFilterRepository;
import com.project.store.repository.filter.ProductFilterRepository;
import com.project.store.repository.filter.SearchCriteria;
import com.project.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    public final CategoryRepository categoryRepository;

    public final CategoryFilterRepository categoryFilterRepository;


    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryFilterRepository categoryFilterRepository){
        this.categoryRepository = categoryRepository;
        this.categoryFilterRepository = categoryFilterRepository;
    }

    public List<Category> filter(List<SearchCriteria> searchCriteria){
        return categoryFilterRepository.filterCategory(searchCriteria);
    }

    @Override
    public Category saveCategory(Category categoryToBeSaved) {
        return categoryRepository.save(categoryToBeSaved);
    }

    @Override
    public Category getCategory(Integer id) {
        return findCategoryById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Category categoryToBeUpdated) {
        Category updatedCategory = findCategoryById(categoryToBeUpdated.getId());
        updatedCategory.setName(categoryToBeUpdated.getName());
        updatedCategory.setType(categoryToBeUpdated.getType());

        return categoryRepository.save(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    private Category findCategoryById (Integer id) {
        Optional<Category> optionalProductCategory = categoryRepository.findById(id);
        if(optionalProductCategory.isEmpty()){
            throw new CategoryNotFoundException(ExceptionMessages.PRODUCT_CATEGORY_NOT_FOUND.getErrorMessage(),
                    ExceptionMessages.PRODUCT_CATEGORY_NOT_FOUND.getHttpStatusCode());
        }

        return optionalProductCategory.get();
    }
}
