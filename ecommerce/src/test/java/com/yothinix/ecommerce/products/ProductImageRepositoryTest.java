package com.yothinix.ecommerce.products;

import com.yothinix.ecommerce.products.entity.ProductImage;
import com.yothinix.ecommerce.products.repository.ProductImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

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

        Optional<ProductImage> actual = productImageRepository.findProductImageByProductId(2);

        assertTrue(actual.isPresent());
        assertEquals("/media/example.jpg", actual.get().getImagePath());
    }

    @Test
    void findProductImageByProductIdWhenProductImageIsNotFoundTest() {
        Optional<ProductImage> actual = productImageRepository.findProductImageByProductId(10);

        assertTrue(actual.isEmpty());
    }
}