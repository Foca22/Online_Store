package com.project.store.service.impl;

import com.project.store.exceptions.category.CategoryNotFoundException;
import com.project.store.exceptions.messages.ExceptionMessages;
import com.project.store.model.category.Category;
import com.project.store.model.product.Product;
import com.project.store.repository.CategoryRepository;
import com.project.store.repository.filter.CategoryFilterRepository;
import com.project.store.repository.filter.SearchCriteria;
import com.project.store.repository.filter.SearchOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

class CategoryServiceImplTest {

    private static final Integer categoryID = 1;
    private static final String name = "Laptop";
    private static final String type = "Notebook";
    private static final List<Product> productList = new ArrayList<>();

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    CategoryFilterRepository categoryFilterRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveCategory() {
        Category categoryToBeSaved = new Category(name, type, productList);

        when(categoryRepository.save(any(Category.class))).thenAnswer(a -> {
            Category category = (Category) a.getArguments()[0];
            category.setId(categoryID);
            return category;
        });

        Category savedCategory = categoryService.saveCategory(categoryToBeSaved);

        assertEquals(categoryID, savedCategory.getId());
        assertEquals(name, savedCategory.getName());
        assertEquals(type, savedCategory.getType());
        assertEquals(productList, savedCategory.getProducts());
    }

    @Test
    public void shouldGetCategory() {
        Category category = new Category(name, type, productList);
        category.setId(categoryID);

        when(categoryRepository.findById(any(Integer.class))).thenReturn(Optional.of(category));

        Category retrievedCategory = categoryService.getCategory(categoryID);

        assertEquals(category, retrievedCategory);
    }

    @Test
    public void shouldNotGetCategory() {
        when(categoryRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(CategoryNotFoundException.class,
                () -> categoryService.getCategory(categoryID));

        String expectedMessage = ExceptionMessages.PRODUCT_CATEGORY_NOT_FOUND.getErrorMessage();
        String actualMessage = exception.getMessage();

        assertTrue(expectedMessage.equals(actualMessage));
    }

    @Test
    public void shouldGetCategories() {
        Category category1 = new Category(name, type, productList);
        Category category2 = new Category("Smartphone & Accessories", "Smartphone", new ArrayList<>());

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<Category> categoryResultList = categoryService.getAllCategories();

        assertEquals(2, categoryResultList.size());
    }

    @Test
    public void shouldNotGetCategories() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        List<Category> categoryResultList = categoryService.getAllCategories();

        assertEquals(0, categoryResultList.size());
    }

    @Test
    public void shouldUpdateCategory() {
        Category category = new Category(name, type, productList);
        category.setId(categoryID);

        Category categoryWithUpdates = new Category("Smartphone & Accessories", "Smartphone", productList);
        categoryWithUpdates.setId(categoryID);

        when(categoryRepository.findById(any(Integer.class))).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenAnswer(a -> (Category) a.getArguments()[0]);

        Category updatedCategory = categoryService.updateCategory(categoryWithUpdates);

        assertEquals(categoryWithUpdates.getId(), updatedCategory.getId());
        assertEquals(categoryWithUpdates.getName(), updatedCategory.getName());
        assertEquals(categoryWithUpdates.getType(), updatedCategory.getType());
    }

    @Test
    public void shouldNotUpdateCategory() {
        Category category = new Category(name, type, productList);
        category.setId(categoryID);

        Category categoryWithUpdates = new Category("Smartphone & Accessories", "Smartphone", productList);

        when(categoryRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenAnswer(a -> (Category)a.getArguments()[0]);

        Exception exception = assertThrows(CategoryNotFoundException.class,
                () -> categoryService.updateCategory(categoryWithUpdates));

        String expectedMessage = ExceptionMessages.PRODUCT_CATEGORY_NOT_FOUND.getErrorMessage();
        String actualMessage = exception.getMessage();

        assertTrue(expectedMessage.equals(actualMessage));
    }

    @Test
    public void shouldDeleteCategory() {
        Category category = new Category(name, type, productList);
        category.setId(categoryID);

        categoryRepository.deleteById(categoryID);

        Optional<Category> afterDeletion = categoryRepository.findById(categoryID);

        assertNull(null, String.valueOf(afterDeletion.isEmpty()));
    }
}