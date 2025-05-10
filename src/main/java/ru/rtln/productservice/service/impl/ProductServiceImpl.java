package ru.rtln.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtln.productservice.aop.annotation.Loggable;
import ru.rtln.productservice.dto.ProductDto;
import ru.rtln.productservice.dto.UnitDto;
import ru.rtln.productservice.entity.Product;
import ru.rtln.productservice.entity.Unit;
import ru.rtln.productservice.mapper.ProductMapper;
import ru.rtln.productservice.mapper.UnitMapper;
import ru.rtln.productservice.repository.ProductRepository;
import ru.rtln.productservice.service.ProductPictureService;
import ru.rtln.productservice.service.ProductService;
import ru.rtln.productservice.service.UnitService;

import java.util.List;
import java.util.Optional;

import static ru.rtln.productservice.exception.ProductException.Code.*;

/**
 * Реализация {@link ProductService}
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UnitService unitService;
    private final ProductMapper productMapper;
    private final UnitMapper unitMapper;
    private final ProductPictureService productPictureService;
    @Override
    @Transactional(readOnly = true)
    @Loggable
    public ProductDto getById(Long id) {
        return productRepository.findById(id)
                .map(product-> {
                    var dto = productMapper.toDto(product);
                    fillResponseWithProductPicture(dto);
                    return dto;
                })
                .orElseThrow(() -> PRODUCT_NOT_FOUND.getWith(String.format("Продукт c ID: [%s] не найден", id))
                        .setMessageParam(String.valueOf(id)));
    }

    @Override
    @Transactional
    @Loggable
    public ProductDto create(ProductDto productDto) {
        Unit unit = getUnitFromProductDto(productDto);
        Optional<Product> mayBeProduct = productRepository.findByNameIgnoreCaseAndUnit(
                productDto.getName(),
                unit);
        if (mayBeProduct.isPresent()) {
            throw PRODUCT_ALREADY_EXISTS.getWith(String.format("Продукт с названием: [%s] и единицей измерения: [%s] уже существует", productDto.getName(), unit.getName()))
                    .setMessageParam(String.valueOf(mayBeProduct.get().getId()));
        }
        Product product = productMapper.toEntity(productDto);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(product-> {
                            var dto = productMapper.toDto(product);
                            fillResponseWithProductPicture(dto);
                            return dto;
                        }
                )
                .toList();
    }

    private Unit getUnitFromProductDto(ProductDto productDto) {
        UnitDto unitDto = unitService.getByName(productDto.getUnit());
        return unitMapper.toEntity(unitDto);
    }

    private void fillResponseWithProductPicture(ProductDto productDto) {
        var cover = productPictureService.getProductPicturesById(productDto.getId());
        cover.ifPresent(file -> productDto.setProductPictureUrl(cover.get().getUrl()));
    }
}
