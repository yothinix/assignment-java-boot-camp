package com.yothinix.ecommerce.products.repository;

import com.yothinix.ecommerce.products.entity.ProductImage;
import com.yothinix.ecommerce.products.repository.ProductImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductImageRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ProductImageRepository productImageRepository;

    @Test
    void findProductImageByProductIdWhenProductImageIsFoundTest() {
        ProductImage productImage = new ProductImage();
        productImage.setId(1);
        productImage.setProductId(2);
        productImage.setImagePath("/media/example.jpg");
        entityManager.persist(productImage);

        List<ProductImage> actual = productImageRepository.findProductImageByProductId(2);

        assertFalse(actual.isEmpty());
        assertEquals("/media/example.jpg", actual.get(0).getImagePath());
    }

    @Test
    void findProductImageByProductIdWhenProductImageIsNotFoundTest() {
        List<ProductImage> actual = productImageRepository.findProductImageByProductId(10);

        assertTrue(actual.isEmpty());
    }
}