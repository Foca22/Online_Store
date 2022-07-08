package com.project.store.mapper;

import com.project.store.dto.category.ProductCategoryDto;
import com.project.store.model.category.ProductCategory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductCategoryMapper {

    public ProductCategoryDto fromEntityToDto(ProductCategory productCategoryEntity) {
        ProductCategoryDto productCategoryDto = new ProductCategoryDto();
        productCategoryDto.setId(productCategoryEntity.getId());
        productCategoryDto.setName(productCategoryEntity.getName());
        productCategoryDto.setType(productCategoryEntity.getType());

        return productCategoryDto;
    }

    public List<ProductCategoryDto> fromEntitiesToDtos(List<ProductCategory> productCategoryEntitiesList) {
        if (productCategoryEntitiesList.isEmpty()) {
            return Collections.emptyList();
        }
        return productCategoryEntitiesList.stream()
                .map(productCategoryEntity -> fromEntityToDto(productCategoryEntity))
                .collect(Collectors.toList());
    }

    public ProductCategory fromDtoToEntity(ProductCategoryDto productCategoryDto) {
        ProductCategory productCategoryEntity = new ProductCategory();
        productCategoryEntity.setId(productCategoryDto.getId());
        productCategoryEntity.setName(productCategoryDto.getName());
        productCategoryEntity.setType(productCategoryDto.getType());

        return productCategoryEntity;
    }

    public List<ProductCategory> fromDtosToEntities(List<ProductCategoryDto> productCategoryDtoList) {
        if (productCategoryDtoList.isEmpty()) {
            return Collections.emptyList();
        }
        return productCategoryDtoList.stream()
                .map(productCategoryDto -> fromDtoToEntity(productCategoryDto))
                .collect(Collectors.toList());
    }

}
