package ru.rtln.productservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.rtln.productservice.annotation.HasProductManagementAuthority;
import ru.rtln.productservice.dto.ProductDto;
import ru.rtln.productservice.service.ProductService;

import java.util.List;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Products API")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    @Operation(summary = "Получение продукта")
    public ProductDto getProductById(@PathVariable("productId") Long productId) {
        return productService.getById(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление продукта")
    @HasProductManagementAuthority
    public ProductDto addProduct(@Validated @RequestBody ProductDto productDto) {
        return productService.create(productDto);
    }

    @GetMapping()
    @Operation(summary = "Получение всех продуктов")
    @HasProductManagementAuthority
    public List<ProductDto> getAllProducts() {
        return productService.getAll();
    }

}