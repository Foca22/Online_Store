package com.project.store.repository.filter;

import com.project.store.exceptions.messages.ExceptionMessages;
import com.project.store.exceptions.product.SearchCriteriaInvalidException;
import com.project.store.model.category.Category;
import com.project.store.model.product.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CategoryFilterRepository {

    private EntityManager entityManager;
    private final String CATEGORY_NAME = "name";
    private final String CATEGORY_TYPE = "price";

    public CategoryFilterRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Category> filterCategory(List<SearchCriteria> searchCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = criteriaQuery.from(Category.class);

        List<Predicate> predicateList = new ArrayList<>();

        Map<String, List<SearchCriteria>> mapForSearch = searchCriteria.stream()
                .collect(Collectors.groupingBy(filter -> filter.getFilterBy()));

        for (Map.Entry<String, List<SearchCriteria>> entry : mapForSearch.entrySet()) {

            if (entry.getKey().equals(CATEGORY_NAME)) {
                predicateList.add(criteriaBuilder.or(collectByCategory(criteriaBuilder, categoryRoot, entry).toArray(new Predicate[0])));
            }

            if (entry.getKey().equals(CATEGORY_TYPE)) {
                predicateList.add(criteriaBuilder.and(collectByCategory(criteriaBuilder, categoryRoot, entry).toArray(new Predicate[0])));
            }
        }

        criteriaQuery.select(categoryRoot).where(predicateList.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private List<Predicate> collectByCategory(CriteriaBuilder criteriaBuilder, Root<Category> categoryRoot, Map.Entry<String, List<SearchCriteria>> entry) {
        return entry.getValue().stream()
                .map(criteria -> switch (criteria.getOperation()) {
                    case LIKE ->
                            criteriaBuilder.like(categoryRoot.get(criteria.getFilterBy()), criteria.getValue().toString());
                    default -> throw new SearchCriteriaInvalidException(ExceptionMessages.INVALID_SEARCH_CRITERIA.getErrorMessage(),
                            ExceptionMessages.INVALID_SEARCH_CRITERIA.getHttpStatusCode());
                }).collect(Collectors.toList());
    }
}
