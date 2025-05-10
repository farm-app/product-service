package ru.rtln.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.rtln.common.security.model.AuthenticatedUserModel;
import ru.rtln.productservice.dto.ProductPictureInfo;
import ru.rtln.productservice.entity.Product;
import ru.rtln.productservice.entity.ProductPicture;
import ru.rtln.productservice.enums.BucketType;
import ru.rtln.productservice.exception.ProductException;
import ru.rtln.productservice.mapper.ProductPictureMapper;
import ru.rtln.productservice.repository.ProductPictureRepository;
import ru.rtln.productservice.repository.ProductRepository;
import ru.rtln.productservice.service.ProductPictureService;
import ru.rtln.productservice.service.ObjectStoreService;
import ru.rtln.productservice.service.PermissionsService;

import java.util.Optional;

import static ru.rtln.productservice.enums.BucketType.PRODUCT_PICTURE;
import static ru.rtln.productservice.exception.ProductPictureException.Code.BAD_EXTENSION;
import static ru.rtln.productservice.exception.ProductException.Code.WRONG_PRODUCT_ACTION;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductPictureServiceImpl implements ProductPictureService {

    private final ProductPictureRepository productPictureRepository;

    private final ObjectStoreService objectStorage;

    private final ProductPictureMapper productPictureMapper;

    private final ProductRepository productRepository;

    private final PermissionsService permissionsService;

    @Transactional(readOnly = true)
    @Override
    public Optional<ProductPicture> getProductPicturesById(Long productId) {
        var pictures = productPictureRepository.findAllByProductId(productId);
        if (pictures == null || pictures.isEmpty()) {
            return Optional.empty();
        }
        if (pictures.size() > 1) {
            log.error("Found more than 1 images for a product with ID: [{}]", productId);
        }
        return Optional.of(pictures.get(0));
    }

    @Transactional
    public ProductPictureInfo addPictureToProduct(MultipartFile picture, Long bookId, AuthenticatedUserModel authUser) {
        Product product = productRepository.findById(bookId)
                .orElseThrow(() -> ProductException.Code.PRODUCT_NOT_FOUND.getWith(String.format("Book with ID: [%s] not found", bookId))
                        .setMessageParam(String.valueOf(bookId)));

        var hasModerationRights = permissionsService.checkLibraryManagementPermission(authUser);
        if (!hasModerationRights) {
            throw WRONG_PRODUCT_ACTION.getWith("A product owner can't update picture an approved or canceled product");
        }

        var extension = FileNameUtils.getExtension(picture.getOriginalFilename());
        if (extension == null) {
            throw BAD_EXTENSION.getWith("Extension is null");
        }
        var productPictures = productPictureRepository.findAllByProductId(bookId);
        productPictureRepository.deleteAll(productPictures);

        var transientProductPicture = getTransientProductPicture(picture, product, extension);
        var persistentFile = productPictureRepository.save(transientProductPicture);
        var productPictureUrl = objectStorage.uploadFile(Long.valueOf(String.valueOf(persistentFile.getId())), picture, PRODUCT_PICTURE.getName());
        persistentFile.setUrl(productPictureUrl);

        return productPictureMapper.fromProductPictureToProductPictureInfoDto(persistentFile);
    }

    private static ProductPicture getTransientProductPicture(MultipartFile productPicture, Product product, String extension) {
        return ProductPicture.builder()
                .product(product)
                .fileExtension(extension)
                .mimeType(productPicture.getContentType())
                .fileName(productPicture.getOriginalFilename())
                .size((int) productPicture.getSize())
                .build();
    }
}
