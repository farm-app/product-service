package ru.rtln.productservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.rtln.common.security.model.AuthenticatedUserModel;
import ru.rtln.productservice.dto.ProductPictureInfo;
import ru.rtln.productservice.entity.ProductPicture;

import java.util.Optional;

public interface ProductPictureService {

    Optional<ProductPicture> getProductPicturesById(Long id);

    ProductPictureInfo addPictureToProduct(MultipartFile file, Long bookId, AuthenticatedUserModel authUser);
}
