package com.project.store.controller.category;

import com.project.store.dto.category.ProductCategoryDto;
import com.project.store.mapper.ProductCategoryMapper;
import com.project.store.model.category.ProductCategory;
import com.project.store.service.impl.ProductCategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/category")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryServiceImpl productCategoryService;
    private final ProductCategoryMapper productCategoryMapper;

    @PostMapping()
    public ProductCategoryDto saveProductCategory(@Valid @RequestBody ProductCategoryDto productCategoryDto) {
        ProductCategory productCategory = productCategoryMapper.fromDtoToEntity(productCategoryDto);
        ProductCategory productCategoryToBeSaved = productCategoryService.saveProductCategory(productCategory);

        return productCategoryMapper.fromEntityToDto(productCategoryToBeSaved);
    }

    @GetMapping("/{id}")
    public ProductCategoryDto getProductCategory(@PathVariable Integer id) {
        ProductCategory productCategory = productCategoryService.getProductCategory(id);

        return productCategoryMapper.fromEntityToDto(productCategory);
    }

    @GetMapping()
    public List<ProductCategoryDto> getAllProductCategories() {
        List<ProductCategory> productCategoryList = productCategoryService.getAllProductCategories();
        return productCategoryMapper.fromEntitiesToDtos(productCategoryList);
    }

    @PutMapping()
    public ProductCategoryDto updateProductCategory(@Valid @RequestBody ProductCategoryDto productCategoryDto) {
        ProductCategory productCategoryToBeUpdated = productCategoryMapper.fromDtoToEntity(productCategoryDto);
        ProductCategory updatedProductCategory = productCategoryService.updateProductCategory(productCategoryToBeUpdated);

        return productCategoryMapper.fromEntityToDto(updatedProductCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteProductCategory(@PathVariable Integer id) {
        productCategoryService.deleteProductCategory(id);
    }


}
