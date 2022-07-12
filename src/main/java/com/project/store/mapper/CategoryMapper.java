package com.project.store.mapper;

import com.project.store.dto.category.CategoryDto;
import com.project.store.dto.category.CategoryWithProductsDto;
import com.project.store.model.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    private final ProductMapper productMapper;

    @Autowired
    public CategoryMapper(ProductMapper productMapper){
        this.productMapper = productMapper;
    }


    public CategoryDto fromEntityToDto(Category categoryEntity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(categoryEntity.getId());
        categoryDto.setName(categoryEntity.getName());
        categoryDto.setType(categoryEntity.getType());

        return categoryDto;
    }

    public CategoryWithProductsDto fromEntityToDtoWithProducts(Category categoryEntity) {
        CategoryWithProductsDto productCategoryDto = new CategoryWithProductsDto();
        productCategoryDto.setId(categoryEntity.getId());
        productCategoryDto.setName(categoryEntity.getName());
        productCategoryDto.setType(categoryEntity.getType());
        productCategoryDto.setProducts(productMapper.fromEntitiesToDtos(categoryEntity.getProducts()));

        return productCategoryDto;
    }

    public List<CategoryDto> fromEntitiesToDtos(List<Category> categoryEntitiesList) {
        if (CollectionUtils.isEmpty(categoryEntitiesList)) {
            return Collections.emptyList();
        }
        return categoryEntitiesList.stream()
                .map(productCategoryEntity -> fromEntityToDto(productCategoryEntity))
                .collect(Collectors.toList());
    }

    public List<CategoryWithProductsDto> fromEntitiesToDtosWithProducts(List<Category> categoryEntitiesList) {
        if (CollectionUtils.isEmpty(categoryEntitiesList)) {
            return Collections.emptyList();
        }

        return categoryEntitiesList.stream()
                .map(category -> fromEntityToDtoWithProducts(category))
                .collect(Collectors.toList());
    }

    public Category fromDtoToEntity(CategoryDto categoryDto) {
        Category categoryEntity = new Category();
        categoryEntity.setId(categoryDto.getId());
        categoryEntity.setName(categoryDto.getName());
        categoryEntity.setType(categoryDto.getType());
        //categoryEntity.setProducts(productMapper.fromDtosToEntities(categoryDto.getProducts()));
        return categoryEntity;
    }

    public List<Category> fromDtosToEntities(List<CategoryDto> categoryDtoList) {
        if (CollectionUtils.isEmpty(categoryDtoList)) {
            return Collections.emptyList();
        }
        return categoryDtoList.stream()
                .map(categoryDto -> fromDtoToEntity(categoryDto))
                .collect(Collectors.toList());
    }

}
