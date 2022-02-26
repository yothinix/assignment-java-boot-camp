package com.yothinix.ecommerce.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    Optional<ProductImage> findProductImageByProductId(Integer productId);
}
