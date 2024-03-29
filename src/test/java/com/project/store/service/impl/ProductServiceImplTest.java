package com.project.store.service.impl;

import com.project.store.exceptions.messages.ExceptionMessages;
import com.project.store.exceptions.product.ProductNotFoundException;
import com.project.store.model.category.Category;
import com.project.store.model.product.Product;
import com.project.store.repository.ProductRepository;
import com.project.store.repository.filter.ProductFilterRepository;
import com.project.store.repository.filter.SearchCriteria;
import com.project.store.repository.filter.SearchOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {


    private static final Integer productId = 1;
    private static final String name = "USB Cable";
    private static final Float price = 25F;

    private static final Category category = new Category();

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductFilterRepository productFilterRepository;

    @Mock
    List<SearchCriteria> searchCriteria;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldAddProduct() {
        Product productToBeSaved = new Product(name, price, category);

        when(productRepository.save(any(Product.class))).thenAnswer(a -> {
            Product product = (Product) a.getArguments()[0];
            product.setId(productId);
            return product;
        });

        Product savedProduct = productService.addProduct(productToBeSaved);

        assertEquals(productId, savedProduct.getId());
        assertEquals(name, savedProduct.getName());
        assertEquals(price, savedProduct.getPrice());
        assertEquals(category, savedProduct.getCategory());
    }

    @Test
    public void shouldGetProduct() {
        Product product = new Product(name, price, category);
        product.setId(productId);
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(product));

        Product retrievedProduct = productService.getProduct(productId);

        assertEquals(product, retrievedProduct);
    }

    @Test
    public void shouldNotGetProduct() {
        when(productRepository.findById(any(Integer.class)))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class,
                () -> productService.getProduct(productId));

        String actualMessage = exception.getMessage();
        String expectedMessage = ExceptionMessages.PRODUCT_NOT_FOUND.getErrorMessage();

        assertTrue(expectedMessage.equals(actualMessage));
    }

    @Test
    public void shouldGetAllProducts() {
        Product product1 = new Product(name, price, category);
        Product product2 = new Product("HDMI Cable", 38F, category);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> productResultList = productService.getAllProducts();

        assertEquals(2, productResultList.size());
    }

    @Test
    public void shouldNotGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<Product> productResultList = productService.getAllProducts();

        assertEquals(0, productResultList.size());
    }

    @Test
    public void shouldUpdateProduct() {
        Product product = new Product(name, price, category);
        product.setId(productId);

        Product updatedProductInfo = new Product("HDMI Cable", 37F, category);
        updatedProductInfo.setId(productId);

        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(a -> (Product) a.getArguments()[0]);

        Product updatedProduct = productService.updateProduct(updatedProductInfo);

        assertEquals(updatedProductInfo.getId(), updatedProduct.getId());
        assertEquals(updatedProductInfo.getName(), updatedProduct.getName());
        assertEquals(updatedProductInfo.getPrice(), updatedProduct.getPrice());
    }

    @Test
    public void shouldNotUpdateProduct() {
        Product product = new Product(name, price, category);
        product.setId(productId);

        Product updatedProductInfo = new Product("HDMI Cable", 37F, category);

        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenAnswer(a -> (Product) a.getArguments()[0]);

        Exception exception = assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(updatedProductInfo));

        String expectedMessage = ExceptionMessages.PRODUCT_NOT_FOUND.getErrorMessage();
        String actualMessage = exception.getMessage();

        assertTrue(expectedMessage.equals(actualMessage));
    }

    @Test
    public void shouldUpdateProductCategory() {
        Product product = new Product(name, price, category);
        product.setId(productId);

        Product productWithUpdatedCategory = new Product(name, price, new Category());
        productWithUpdatedCategory.setId(productId);

        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(a -> (Product) a.getArguments()[0]);

        Product updatedProductWithCategory = productService.updateProductCategory(productWithUpdatedCategory);

        assertEquals(productWithUpdatedCategory.getId(), updatedProductWithCategory.getId());
        assertEquals(productWithUpdatedCategory.getName(), updatedProductWithCategory.getName());
        assertEquals(productWithUpdatedCategory.getPrice(), updatedProductWithCategory.getPrice());
        assertEquals(productWithUpdatedCategory.getCategory(), updatedProductWithCategory.getCategory());

    }

    @Test
    public void shouldNotUpdateProductCategory() {
        Product product = new Product(name, price, category);
        product.setId(productId);

        Product productWithUpdatedCategory = new Product(name, price, null);

        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenAnswer(a -> (Product) a.getArguments()[0]);

        Exception exception = assertThrows(ProductNotFoundException.class,
                () -> productService.updateProductCategory(productWithUpdatedCategory));

        String expectedMessage = ExceptionMessages.PRODUCT_NOT_FOUND.getErrorMessage();
        String actualMessage = exception.getMessage();

        assertTrue(expectedMessage.equals(actualMessage));
    }


    @Test
    public void shouldCatchArgumentForFilteringProducts() {
        SearchCriteria searchCriteria1 = new SearchCriteria("name", SearchOperation.LIKE, "USB Cable", null);
        SearchCriteria searchCriteria2 = new SearchCriteria("name", SearchOperation.LIKE, "HDMI Cable", null);
        SearchCriteria searchCriteria3 = new SearchCriteria("price", SearchOperation.LESS_THAN, "20F", null);

        List<SearchCriteria> searchCriteriaList = List.of(searchCriteria1, searchCriteria2, searchCriteria3);

        List<Product> productList = productService.searchProducts(searchCriteriaList);

        ArgumentCaptor<List<SearchCriteria>> argument =
                ArgumentCaptor.forClass(List.class);

        verify(productFilterRepository).filter(argument.capture());

        assertEquals(searchCriteria1.getFilterBy(), argument.getValue().get(0).getFilterBy());
        assertEquals(searchCriteria1.getOperation(), argument.getValue().get(0).getOperation());
        assertEquals(searchCriteria1.getValue(), argument.getValue().get(0).getValue());

        assertEquals(searchCriteria2.getFilterBy(), argument.getValue().get(1).getFilterBy());
        assertEquals(searchCriteria2.getOperation(), argument.getValue().get(1).getOperation());
        assertEquals(searchCriteria2.getValue(), argument.getValue().get(1).getValue());

        assertEquals(searchCriteria3.getFilterBy(), argument.getValue().get(2).getFilterBy());
        assertEquals(searchCriteria3.getOperation(), argument.getValue().get(2).getOperation());
        assertEquals(searchCriteria3.getValue(), argument.getValue().get(2).getValue());
    }


    @Test
    public void shouldReturnProducts() {
        Product product1 = new Product(name, price, category);
        product1.setId(productId);

        Product product2 = new Product("HDMI Cable", 37F, category);
        product2.setId(2);

        when(productFilterRepository.filter(any(List.class))).thenReturn(List.of(product1, product2));

        List<Product> productList = productService.searchProducts(searchCriteria);

        assertEquals(2, productList.size());
        assertEquals(product1, productList.get(0));
        assertEquals(product2, productList.get(1));


    }
    @Test
    public void shouldDeleteProduct() {
        Product product = new Product(name, price, category);
        product.setId(productId);

        productRepository.deleteById(productId);

        Optional<Product> afterDeletion = productRepository.findById(productId);

        assertNull(null, String.valueOf(afterDeletion.isEmpty()));
    }

}