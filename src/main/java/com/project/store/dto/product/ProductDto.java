package com.project.store.dto.product;

import com.project.store.model.category.Category;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductDto {

    private Integer id;

    @NotEmpty(message = "product.name.cannot.not.be.empty")
    private String name;

    @NotNull(message = "product.price.cannot.be.null")
    private Float price;

    private Category category;


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
        return category.getId();
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
