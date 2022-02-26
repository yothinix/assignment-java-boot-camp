package com.yothinix.ecommerce.products.repository;

import com.yothinix.ecommerce.products.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
    List<ProductReview> findProductReviewByProductId(Integer productId);
}
