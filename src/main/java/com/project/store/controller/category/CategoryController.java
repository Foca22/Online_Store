package com.project.store.controller.category;

import com.project.store.dto.category.CategoryDto;
import com.project.store.dto.category.CategoryWithProductsDto;
import com.project.store.mapper.CategoryMapper;
import com.project.store.model.category.Category;
import com.project.store.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping()
    public CategoryDto saveCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.fromDtoToEntity(categoryDto);
        Category categoryToBeSaved = categoryService.saveCategory(category);

        return categoryMapper.fromEntityToDto(categoryToBeSaved);
    }

    @GetMapping("/{id}")
    public CategoryDto getProductCategory(@PathVariable Integer id) {
        Category category = categoryService.getCategory(id);

        return categoryMapper.fromEntityToDto(category);
    }

    @GetMapping()
    public List<CategoryDto> getAllCategories() {
        List<Category> categoryList = categoryService.getAllCategories();
        return categoryMapper.fromEntitiesToDtos(categoryList);
    }

    @GetMapping("/{id}/products")
    public CategoryWithProductsDto getCategoryWithProducts(@PathVariable Integer id) {
        Category category = categoryService.getCategory(id);

        return categoryMapper.fromEntityToDtoWithProducts(category);
    }

    @GetMapping("/categories-with-products")
    public List<CategoryWithProductsDto> getAllCategoriesWithProducts() {
        List<Category> categoryList = categoryService.getAllCategories();
        return categoryMapper.fromEntitiesToDtosWithProducts(categoryList);
    }

    @PutMapping()
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category categoryToBeUpdated = categoryMapper.fromDtoToEntity(categoryDto);
        Category updatedCategory = categoryService.updateCategory(categoryToBeUpdated);

        return categoryMapper.fromEntityToDto(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }


}
