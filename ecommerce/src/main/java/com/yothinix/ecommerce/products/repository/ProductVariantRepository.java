package com.yothinix.ecommerce.products.repository;

import com.yothinix.ecommerce.products.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    List<ProductVariant> findProductVariantByProductId(Integer productId);
}
