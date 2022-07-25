package com.project.store.repository.filter;

import java.util.Objects;

public class SearchCriteria {

    private String filterBy;
    private SearchOperation operation;
    private Object value;
    private Object categoryId;

    public SearchCriteria(String filterBy, SearchOperation operation, String value, Object categoryId) {
        this.filterBy = filterBy;
        this.value = value;
        this.operation = operation;
        this.categoryId = categoryId;
    }

    public SearchCriteria(){}

    public String getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }

    public Object getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Object categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchCriteria that = (SearchCriteria) o;
        return Objects.equals(filterBy, that.filterBy) && operation == that.operation && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filterBy, operation, value);
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "filterBy='" + filterBy + '\'' +
                ", operation=" + operation +
                ", value=" + value +
                '}';
    }
}
