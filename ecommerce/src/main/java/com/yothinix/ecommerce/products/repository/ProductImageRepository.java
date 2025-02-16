package com.yothinix.ecommerce.products.repository;

import com.yothinix.ecommerce.products.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findProductImageByProductId(Integer productId);
}
