package ru.rtln.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rtln.productservice.entity.ProductPicture;

import java.util.List;

@Repository
public interface ProductPictureRepository extends JpaRepository<ProductPicture, Long> {

    List<ProductPicture> findAllByProductId(Long productId);
}
