package com.project.store.controller.category;

import com.project.store.dto.category.CategoryDto;
import com.project.store.dto.category.CategoryWithProductsDto;
import com.project.store.exceptions.id.IdNullException;
import com.project.store.exceptions.messages.ExceptionMessages;
import com.project.store.mapper.CategoryMapper;
import com.project.store.model.category.Category;
import com.project.store.repository.filter.SearchCriteria;
import com.project.store.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/filter")
    private List<CategoryWithProductsDto> filter(@Valid @RequestBody List<SearchCriteria> searchCriteria){
        List<Category> categoryList = categoryService.filter(searchCriteria);
        return categoryMapper.fromEntitiesToDtosWithProducts(categoryList);
    }

    @PostMapping()
    public CategoryDto saveCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.fromDtoToEntity(categoryDto);
        Category categoryToBeSaved = categoryService.saveCategory(category);

        return categoryMapper.fromEntityToDto(categoryToBeSaved);
    }

    @GetMapping("/{id}")
    public CategoryDto getCategory(@PathVariable Integer id) {
        Category category = categoryService.getCategory(id);

        return categoryMapper.fromEntityToDto(category);
    }

    @GetMapping()
    public List<CategoryDto> getAllCategories() {
        List<Category> categoryList = categoryService.getAllCategories();
        return categoryMapper.fromEntitiesToDtos(categoryList);
    }

    @GetMapping(value = "/products")
    public List<CategoryWithProductsDto> getCategoryWithProducts(@RequestParam(required = false) Optional<Integer> id) {
        if (id.isPresent()) {
            Category category = categoryService.getCategory(id.get());
            List<Category> categoryList = List.of(category);
            return categoryMapper.fromEntitiesToDtosWithProducts(categoryList);
        }
        List<Category> categoryListWithProducts = categoryService.getAllCategories();
        return categoryMapper.fromEntitiesToDtosWithProducts(categoryListWithProducts);
    }

    @PutMapping()
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        if(categoryDto.getId() == null){
            throw new IdNullException(ExceptionMessages.NULL_ID.getErrorMessage(),
                    ExceptionMessages.NULL_ID.getHttpStatusCode());
        }
        Category categoryToBeUpdated = categoryMapper.fromDtoToEntity(categoryDto);
        Category updatedCategory = categoryService.updateCategory(categoryToBeUpdated);

        return categoryMapper.fromEntityToDto(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }


}
