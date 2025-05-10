package ru.rtln.productservice.service;

import ru.rtln.productservice.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto getById(Long id);

    ProductDto create(ProductDto productDto);

    List<ProductDto> getAll();
}
