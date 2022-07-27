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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Mock
    List<SearchCriteria> searchCriteria;

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
    public void shouldCatchArgumentsForFilteringCategories() {
        SearchCriteria searchCriteria1 = new SearchCriteria("name", SearchOperation.LIKE, "Laptop", null);
        SearchCriteria searchCriteria2 = new SearchCriteria("name", SearchOperation.LIKE, "Smartphone & Accesories", null);

        List<SearchCriteria> searchCriteriaList = List.of(searchCriteria1, searchCriteria2);

        List<Category> categoryList = categoryService.filter(searchCriteriaList);

        ArgumentCaptor<List<SearchCriteria>> argumentCaptor =
                ArgumentCaptor.forClass(List.class);

        verify(categoryFilterRepository).filterCategory(argumentCaptor.capture());

        assertEquals(searchCriteria1.getFilterBy(), argumentCaptor.getValue().get(0).getFilterBy());
        assertEquals(searchCriteria1.getOperation(), argumentCaptor.getValue().get(0).getOperation());
        assertEquals(searchCriteria1.getValue(), argumentCaptor.getValue().get(0).getValue());

        assertEquals(searchCriteria2.getFilterBy(), argumentCaptor.getValue().get(1).getFilterBy());
        assertEquals(searchCriteria2.getOperation(), argumentCaptor.getValue().get(1).getOperation());
        assertEquals(searchCriteria2.getValue(), argumentCaptor.getValue().get(1).getValue());
    }

    @Test
    public void shouldReturnCategories(){
        Category category1 = new Category(name, type, productList);
        category1.setId(categoryID);

        Category category2 = new Category("Smartphone & Accessories", "Smartphone", productList);
        category2.setId(2);

        when(categoryFilterRepository.filterCategory(searchCriteria)).thenReturn(List.of(category1, category2));

        List<Category> categoryList = categoryService.filter(searchCriteria);

        assertEquals(2, categoryList.size());
        assertEquals(category1, categoryList.get(0));
        assertEquals(category2, categoryList.get(1));
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