package com.yothinix.ecommerce.products.repository;

import com.yothinix.ecommerce.products.entity.Product;
import com.yothinix.ecommerce.products.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository repository;

    @Test
    void findProductByNameContainsTextFoundProductTest() {
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("nike air jordan");
        entityManager.persist(product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("adidas ultra boost");
        entityManager.persist(product2);

        List<Product> actual = repository.findProductByNameContains("nike");

        assertEquals(1, actual.size());
        assertEquals("nike air jordan", actual.get(0).getName());
    }

    @Test
    void findProductByNameContainsTextNotFoundProductTest() {
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("nike air jordan");
        entityManager.persist(product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("adidas ultra boost");
        entityManager.persist(product2);

        List<Product> actual = repository.findProductByNameContains("reebok");

        assertEquals(0, actual.size());
    }
}