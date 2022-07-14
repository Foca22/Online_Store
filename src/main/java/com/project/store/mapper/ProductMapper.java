package com.project.store.mapper;

import com.project.store.dto.product.ProductDto;
import com.project.store.model.category.Category;
import com.project.store.model.product.Product;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public Product fromDtoToEntity(ProductDto productDto, Category category) {
        Product productEntity = new Product();
        productEntity.setId(productDto.getId());
        productEntity.setName(productDto.getName());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setCategory(category);
        return productEntity;
    }

    public Product fromDtoToEntity(ProductDto productDto) {
        Product productEntity = new Product();
        productEntity.setId(productDto.getId());
        productEntity.setName(productDto.getName());
        productEntity.setPrice(productDto.getPrice());

        return productEntity;
    }

    public List<Product> fromDtosToEntities(List<ProductDto> productDtoList, Category category) {
        if(productDtoList.isEmpty()){
            return Collections.emptyList();
        }


        return productDtoList.stream()
                .map(productDto -> fromDtoToEntity(productDto, category))
                .collect(Collectors.toList());
    }

    public ProductDto fromEntityToDto(Product productEntity) {
        ProductDto productDto = new ProductDto();
        productDto.setId(productEntity.getId());
        productDto.setName(productEntity.getName());
        productDto.setPrice(productEntity.getPrice());
        productDto.setCategoryId(productEntity.getCategory().getId());

        return productDto;
    }

    public List<ProductDto> fromEntitiesToDtos (List<Product> productEntitiesList) {
        if(productEntitiesList.isEmpty()){
            return Collections.emptyList();
        }

        return productEntitiesList.stream()
                .map(productEntity -> fromEntityToDto(productEntity))
                .collect(Collectors.toList());
    }
}
