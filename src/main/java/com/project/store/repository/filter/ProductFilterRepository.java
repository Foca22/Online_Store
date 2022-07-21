package com.project.store.repository.filter;

import com.project.store.model.product.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ProductFilterRepository {

    private EntityManager entityManager;
    private final String PRODUCT_NAME = "name";
    private final String PRODUCT_PRICE = "price";

    public ProductFilterRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Product> filter(List<SearchCriteria> searchCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);

        List<Predicate> predicateList = new ArrayList<>();

        Map<String, List<SearchCriteria>> mapForSearch = searchCriteria.stream()
                .collect(Collectors.groupingBy(filter -> filter.getFilterBy()));

        for (Map.Entry<String, List<SearchCriteria>> entry : mapForSearch.entrySet()) {

            if (entry.getKey().equals(PRODUCT_NAME)) {
                predicateList.add(criteriaBuilder.or(collectByName(criteriaBuilder, productRoot, entry).toArray(new Predicate[0])));
            }

            if (entry.getKey().equals(PRODUCT_PRICE)) {
                predicateList.add(criteriaBuilder.and(collectByPrice(criteriaBuilder, productRoot, entry).toArray(new Predicate[0])));
            }
        }

        criteriaQuery.select(productRoot).where(predicateList.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
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

                    default -> throw new RuntimeException("Operation not supported yet!");
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

                    default -> throw new RuntimeException("Operation not supported yet!");
                })
                .collect(Collectors.toList());
    }
}
