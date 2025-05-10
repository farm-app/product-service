package ru.rtln.productservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.rtln.productservice.dto.ProductPictureInfo;
import ru.rtln.productservice.entity.ProductPicture;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProductPictureMapper {

    @Mapping(target = "name", source = "fileName")
    ProductPictureInfo fromProductPictureToProductPictureInfoDto(ProductPicture productPicture);
}
