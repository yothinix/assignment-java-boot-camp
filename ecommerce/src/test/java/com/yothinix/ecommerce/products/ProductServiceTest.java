package com.yothinix.ecommerce.products;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void searchProductByNameShouldReturnProductWhenQueryFoundTest() {
        Product product = new Product();
        product.setName("Adidas");
        when(productRepository.findProductByNameContains("Adidas"))
                .thenReturn(List.of(product));

        ProductResponse actual = productService.searchBy("Adidas");

        assertEquals(1, actual.getProducts().size());
        assertEquals("Adidas", actual.getProducts().get(0).getName());
    }

    @Test
    void searchProductByNameShouldReturnEmptyListWhenQueryNotFoundTest() {
        when(productRepository.findProductByNameContains("Adidas"))
                .thenReturn(Collections.emptyList());

        ProductResponse actual = productService.searchBy("Adidas");

        assertEquals(0, actual.getProducts().size());
    }
}