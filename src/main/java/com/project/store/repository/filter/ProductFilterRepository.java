package com.project.store.repository.filter;

import com.project.store.exceptions.messages.ExceptionMessages;
import com.project.store.exceptions.product.SearchCriteriaInvalidException;
import com.project.store.model.category.Category;
import com.project.store.model.product.Product;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ProductFilterRepository {

    private EntityManager entityManager;
    private final String PRODUCT_NAME = "name";
    private final String PRODUCT_PRICE = "price";
    private final String PRODUCT_CATEGORY_ID = "id";

    public ProductFilterRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Product> filter(List<SearchCriteria> searchCriteria) {
        CriteriaQuery<Product> criteriaQuery = getProductCriteriaQuery(searchCriteria);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private CriteriaQuery<Product> getProductCriteriaQuery(List<SearchCriteria> searchCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        Join<Product, Category> products = productRoot.join("category");


        List<Predicate> predicateList = getPredicates(searchCriteria, criteriaBuilder, productRoot, products);

        criteriaQuery.select(productRoot).where(predicateList.toArray(new Predicate[0]));
        return criteriaQuery;
    }

    private List<Predicate> getPredicates(List<SearchCriteria> searchCriteria, CriteriaBuilder criteriaBuilder, Root<Product> productRoot, Join<Product, Category> products) {
        List<Predicate> predicateList = new ArrayList<>();

        Map<String, List<SearchCriteria>> mapForSearch = searchCriteria.stream()
                .collect(Collectors.groupingBy(filter -> filter.getFilterBy()));

        System.out.println(mapForSearch);
        for (Map.Entry<String, List<SearchCriteria>> entry : mapForSearch.entrySet()) {

            if (entry.getKey().equals(PRODUCT_NAME)) {
                predicateList.add(criteriaBuilder.or(collectByName(criteriaBuilder, productRoot, entry).toArray(new Predicate[0])));
            }

            if (entry.getKey().equals(PRODUCT_PRICE)) {
                predicateList.add(criteriaBuilder.and(collectByPrice(criteriaBuilder, productRoot, entry).toArray(new Predicate[0])));
            }

            if (entry.getKey().equals(PRODUCT_CATEGORY_ID)) {
                predicateList.add(criteriaBuilder.or(collectByCategoryID(criteriaBuilder, products, entry).toArray(new Predicate[0])));
            }
        }
        return predicateList;
    }

    private List<Predicate> collectByName(CriteriaBuilder criteriaBuilder, Root<Product> productRoot, Map.Entry<String, List<SearchCriteria>> entry) {

        return entry.getValue().stream()
                .map(searchCriteria -> switch (searchCriteria.getOperation()) {
                    case LIKE ->
                            criteriaBuilder.like(productRoot.get(searchCriteria.getFilterBy()), "%" + searchCriteria.getValue() + "%");
                    case LIKE_START ->
                            criteriaBuilder.like(criteriaBuilder.lower(productRoot.get(searchCriteria.getFilterBy())),
                                    "%" + searchCriteria.getValue().toString().toLowerCase() + "%");
                    case EQUAL ->
                            criteriaBuilder.equal(productRoot.get(searchCriteria.getFilterBy()), searchCriteria.getValue().toString());
                    default ->
                            throw new SearchCriteriaInvalidException(ExceptionMessages.INVALID_SEARCH_CRITERIA.getErrorMessage(),
                                    ExceptionMessages.INVALID_SEARCH_CRITERIA.getHttpStatusCode());
                })
                .collect(Collectors.toList());
    }

    private List<Predicate> collectByPrice(CriteriaBuilder criteriaBuilder, Root<Product> productRoot, Map.Entry<String, List<SearchCriteria>> entry) {

        return entry.getValue().stream()
                .map(searchCriteria -> switch (searchCriteria.getOperation()) {
                    case GREATER_THAN ->
                            criteriaBuilder.greaterThan(productRoot.get(searchCriteria.getFilterBy()), searchCriteria.getValue().toString());
                    case GREATER_THAN_EQUAL ->
                            criteriaBuilder.greaterThanOrEqualTo(productRoot.get(searchCriteria.getFilterBy()), searchCriteria.getValue().toString());
                    case LESS_THAN ->
                            criteriaBuilder.lessThan(productRoot.get(searchCriteria.getFilterBy()), searchCriteria.getValue().toString());
                    case LESS_THAN_EQUAL ->
                            criteriaBuilder.lessThanOrEqualTo(productRoot.get(searchCriteria.getFilterBy()), searchCriteria.getValue().toString());
                    default ->
                            throw new SearchCriteriaInvalidException(ExceptionMessages.INVALID_SEARCH_CRITERIA.getErrorMessage(),
                                    ExceptionMessages.INVALID_SEARCH_CRITERIA.getHttpStatusCode());
                })
                .collect(Collectors.toList());
    }

    private List<Predicate> collectByCategoryID(CriteriaBuilder criteriaBuilder, Join<Product, Category> products, Map.Entry<String, List<SearchCriteria>> entry) {
        return entry.getValue().stream()
                .map(searchCriteria -> switch (searchCriteria.getOperation()) {
                    case EQUAL ->
                            criteriaBuilder.equal(products.get(searchCriteria.getFilterBy()), searchCriteria.getValue().toString());
                    default ->
                            throw new SearchCriteriaInvalidException(ExceptionMessages.INVALID_SEARCH_CRITERIA.getErrorMessage(),
                                    ExceptionMessages.INVALID_SEARCH_CRITERIA.getHttpStatusCode());
                })
                .collect(Collectors.toList());
    }

}
