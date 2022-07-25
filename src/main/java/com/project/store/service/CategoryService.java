package com.project.store.service;

import com.project.store.model.category.Category;
import com.project.store.repository.filter.SearchCriteria;

import java.util.List;

public interface CategoryService {

    Category saveCategory(Category categoryToBeSaved);

    Category getCategory(Integer id);

    List<Category> getAllCategories();

    Category updateCategory(Category categoryToBeUpdated);

    void deleteCategory(Integer id);

    List<Category> filter(List<SearchCriteria> searchCriteria);
}
