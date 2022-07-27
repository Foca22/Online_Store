package com.project.store.controller.product;

import com.project.store.dto.product.ProductDto;
import com.project.store.exceptions.id.IdNullException;
import com.project.store.exceptions.messages.ExceptionMessages;
import com.project.store.mapper.ProductMapper;
import com.project.store.model.category.Category;
import com.project.store.model.product.Product;
import com.project.store.repository.filter.SearchCriteria;
import com.project.store.service.impl.CategoryServiceImpl;
import com.project.store.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;
    private final ProductMapper productMapper;
    private final CategoryServiceImpl categoryService;

    @PostMapping()
    public ProductDto addProduct(@Valid @RequestBody ProductDto productDto) {
        Category category = categoryService.getCategory(productDto.getCategoryId());
        Product product = productMapper.fromDtoToEntity(productDto, category);
        Product productToBeAdded = productService.addProduct(product);

        return productMapper.fromEntityToDto(productToBeAdded);
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Integer id) {
        Product product = productService.getProduct(id);

        return productMapper.fromEntityToDto(product);
    }

    @GetMapping()
    public List<ProductDto> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return productMapper.fromEntitiesToDtos(productList);
    }

    @GetMapping("/filter")
    public List<ProductDto> searchProducts(@Valid @RequestBody List<SearchCriteria> searchCriteria) {
        List<Product> productList = productService.searchProducts(searchCriteria);
        return productMapper.fromEntitiesToDtos(productList);
    }

    @PutMapping()
    public ProductDto updateProduct(@Valid @RequestBody ProductDto productDto) {
        if(productDto.getId() == null){
            throw new IdNullException(ExceptionMessages.NULL_ID.getErrorMessage(),
                    ExceptionMessages.NULL_ID.getHttpStatusCode());
        }
        Product productToBeUpdated = productMapper.fromDtoToEntity(productDto);
        Product updatedProduct = productService.updateProduct(productToBeUpdated);

        return productMapper.fromEntityToDto(updatedProduct);
    }

    @PutMapping("/product-category")
    public ProductDto updateProductCategory(@Valid @RequestBody ProductDto productDto) {
        if(productDto.getId() == null){
            throw new IdNullException(ExceptionMessages.NULL_ID.getErrorMessage(),
                    ExceptionMessages.NULL_ID.getHttpStatusCode());
        }
        Category category = categoryService.getCategory(productDto.getCategoryId());
        Product productUpdatedWithNewCategory = productMapper.fromDtoToEntity(productDto, category);
        Product updatedProductCategory = productService.updateProductCategory(productUpdatedWithNewCategory);

        return productMapper.fromEntityToDto(updatedProductCategory);
    }

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }


}
