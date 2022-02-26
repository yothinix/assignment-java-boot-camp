package com.yothinix.ecommerce.products.repository;

import com.yothinix.ecommerce.products.entity.ProductVariant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductVariantRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ProductVariantRepository productVariantRepository;

    @Test
    void findProductVariantByProductIdWhenProductVariantFoundTest() {
        ProductVariant productVariant1 = new ProductVariant(1, 2, "Size", "S", true);
        ProductVariant productVariant2 = new ProductVariant(2, 2, "Size", "M", false);
        ProductVariant productVariant3 = new ProductVariant(3, 2, "Size", "L", false);
        entityManager.persist(productVariant1);
        entityManager.persist(productVariant2);
        entityManager.persist(productVariant3);

        List<ProductVariant> actual = productVariantRepository.findProductVariantByProductId(2);

        assertEquals(3, actual.size());
        assertEquals("Size", actual.get(0).getVariantName());
        assertEquals("S", actual.get(0).getVariantChoice());
        assertEquals("M", actual.get(1).getVariantChoice());
        assertEquals("L", actual.get(2).getVariantChoice());
    }

    @Test
    void findProductVariantByProductIdWhenProductVariantNotFoundTest() {
        List<ProductVariant> actual = productVariantRepository.findProductVariantByProductId(2);

        assertEquals(0, actual.size());
    }
}