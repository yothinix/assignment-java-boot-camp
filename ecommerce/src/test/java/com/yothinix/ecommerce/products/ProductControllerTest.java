package com.yothinix.ecommerce.products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Product product = new Product();
        product.setId(1);
        product.setName("Nike zoom air");
        product.setBrand("Nike");
        product.setCategory("Mens Shoe");
        product.setDescription("Lorem ipsum dorosit amet");
        product.setPrice(Double.valueOf("399.0"));
        when(productRepository.findProductByNameContains("Nike")).thenReturn(List.of(product));
    }

    @Test
    void getProductControllerShouldReturnProductTest() {
        ProductResponse response = testRestTemplate.getForObject("/products?name=Nike", ProductResponse.class);
        Product actual = response.getProducts().get(0);

        assertEquals(1, response.getProducts().size());
        assertEquals(1, actual.getId());
        assertEquals("Nike zoom air", actual.getName());
        assertEquals("Nike", actual.getBrand());
        assertEquals("Mens Shoe", actual.getCategory());
        assertEquals("Lorem ipsum dorosit amet", actual.getDescription());
        assertEquals(Double.valueOf("399.0"), actual.getPrice());
    }

    @Test
    void getProductControllerShouldReturnEmptyListWhenProductNotFoundTest() {
        ProductResponse response = testRestTemplate.getForObject("/products?name=Adidas", ProductResponse.class);

        assertEquals(0, response.getProducts().size());
    }
}