package com.project.store.dto.category;

import com.project.store.dto.product.ProductDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CategoryWithProductsDto {

    @NotNull(message = "category.id.cannot.be.null")
    private Integer id;

    @NotEmpty(message = "product.category.name.cannot.be.empty")
    private String name;

    @NotEmpty(message = "product.category.type.cannot.be.empty")
    private String type;

    private List<ProductDto> products;


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

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
