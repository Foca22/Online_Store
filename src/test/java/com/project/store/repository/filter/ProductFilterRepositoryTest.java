package com.project.store.repository.filter;

import com.project.store.model.product.Product;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ProductFilterRepositoryTest {

    @Autowired
    ProductFilterRepository productFilterRepository;


    @Test
    public void searchForCablesWherePriceIsGreaterThanTwenty () {
        SearchCriteria searchCriteria1 = new SearchCriteria("name", SearchOperation.LIKE, "USB Cable", null);
        SearchCriteria searchCriteria2 = new SearchCriteria("name", SearchOperation.LIKE, "HDMI Cable", null);
        SearchCriteria searchCriteria3 = new SearchCriteria("price", SearchOperation.GREATER_THAN, "20F", null);
        List<SearchCriteria> searchCriteriaList = List.of(searchCriteria1, searchCriteria2, searchCriteria3);

        List<Product> productList = productFilterRepository.filter(searchCriteriaList);

        assertEquals(4, productList.size());
    }

    @Test
    public void searchForCablesWherePriceIsLessThanTwenty () {
        SearchCriteria searchCriteria1 = new SearchCriteria("name", SearchOperation.LIKE, "USB Cable", null);
        SearchCriteria searchCriteria2 = new SearchCriteria("name", SearchOperation.LIKE, "HDMI Cable", null);
        SearchCriteria searchCriteria3 = new SearchCriteria("price", SearchOperation.LESS_THAN, "20F", null);
        List<SearchCriteria> searchCriteriaList = List.of(searchCriteria1, searchCriteria2, searchCriteria3);

        List<Product> productList = productFilterRepository.filter(searchCriteriaList);

        assertEquals(3, productList.size());
    }

    @Test
    public void searchForProductsStartingWithSam () {
        SearchCriteria searchCriteria1 = new SearchCriteria("name", SearchOperation.LIKE_START, "Sam", null);
        List<SearchCriteria> searchCriteriaList = List.of(searchCriteria1);

        List<Product> productList = productFilterRepository.filter(searchCriteriaList);

        assertEquals(2, productList.size());
    }

    @Test
    public void searchForDellWherePriceIsBetween120And200 () {
        SearchCriteria searchCriteria1 = new SearchCriteria("name", SearchOperation.EQUAL, "Dell", null);
        SearchCriteria searchCriteria2 = new SearchCriteria("price", SearchOperation.GREATER_THAN_EQUAL, "120F", null);
        SearchCriteria searchCriteria3 = new SearchCriteria("price", SearchOperation.LESS_THAN_EQUAL, "200F", null);
        List<SearchCriteria> searchCriteriaList = List.of(searchCriteria1, searchCriteria2, searchCriteria3);

        List<Product> productList = productFilterRepository.filter(searchCriteriaList);

        assertEquals(7, productList.size());
    }

    @Test
    public void searchForProductsWhereCategoryIdIsFive () {
        SearchCriteria searchCriteria1 = new SearchCriteria("id", SearchOperation.EQUAL, "5", null);
        List<SearchCriteria> searchCriteriaList = List.of(searchCriteria1);

        List<Product> productList = productFilterRepository.filter(searchCriteriaList);

        assertEquals(1, productList.size());
    }

}