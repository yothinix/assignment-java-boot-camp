package com.yothinix.ecommerce.products.repository;

import com.yothinix.ecommerce.products.entity.ProductReview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductReviewRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ProductReviewRepository productReviewRepository;

    @Test
    void findProductReviewByProductIdWhenProductReviewFoundTest() {

        ProductReview productReview1 = new ProductReview(1, 1, 1, "good product", 5);
        ProductReview productReview2 = new ProductReview(2, 2, 1, "nah product", 1);
        entityManager.persist(productReview1);
        entityManager.persist(productReview2);

        List<ProductReview> actual = productReviewRepository.findProductReviewByProductId(1);

        assertEquals(2, actual.size());
        assertEquals(5, actual.get(0).getRating());
        assertEquals(1, actual.get(1).getRating());
    }

    @Test
    void findProductReviewByProductIdWhenProductReviewIsNotFoundTest() {
        List<ProductReview> actual = productReviewRepository.findProductReviewByProductId(1);

        assertEquals(0, actual.size());
    }
}