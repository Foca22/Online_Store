package com.project.store.dto.category;

import javax.validation.constraints.NotEmpty;

public class ProductCategoryDto {

    private Integer id;

    @NotEmpty(message = "product.category.name.cannot.be.empty")
    private String name;

    @NotEmpty(message = "product.category.type.cannot.be.empty")
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
