package com.project.store.dto.product;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductDto {

    @NotNull(message = "product.id.cannot.be.null")
    private Integer id;

    @NotEmpty(message = "product.name.cannot.not.be.empty")
    private String name;

    @NotNull(message = "product.price.cannot.be.null")
    private Float price;

    @NotNull(message = "category.id.cannot.be.null")
    private Integer categoryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}
