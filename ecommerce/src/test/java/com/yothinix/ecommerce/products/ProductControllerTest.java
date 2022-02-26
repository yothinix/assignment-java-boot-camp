package com.yothinix.ecommerce.products;

import com.yothinix.ecommerce.products.entity.Product;
import com.yothinix.ecommerce.products.entity.ProductImage;
import com.yothinix.ecommerce.products.entity.ProductReview;
import com.yothinix.ecommerce.products.entity.ProductVariant;
import com.yothinix.ecommerce.products.repository.ProductImageRepository;
import com.yothinix.ecommerce.products.repository.ProductRepository;
import com.yothinix.ecommerce.products.repository.ProductReviewRepository;
import com.yothinix.ecommerce.products.repository.ProductVariantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {
    private final Product product = new Product();

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ProductImageRepository productImageRepository;

    @MockBean
    ProductVariantRepository productVariantRepository;

    @MockBean
    ProductReviewRepository productReviewRepository;

    @BeforeEach
    void setUp() {
        product.setId(1);
        product.setName("Nike zoom air");
        product.setBrand("Nike");
        product.setCategory("Mens Shoe");
        product.setDescription("Lorem ipsum dorosit amet");
        product.setPrice(Double.valueOf("399.0"));
    }

    @Test
    void getProductListControllerShouldReturnProductTest() {
        when(productRepository.findProductByNameContains("Nike")).thenReturn(List.of(product));

        ProductListResponse response = testRestTemplate.getForObject("/products?name=Nike", ProductListResponse.class);
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
    void getProductListControllerShouldReturnEmptyListWhenProductNotFoundTest() {
        ProductListResponse response = testRestTemplate.getForObject("/products?name=Adidas", ProductListResponse.class);

        assertEquals(0, response.getProducts().size());
    }

    @Test
    void getProductDetailControllerShouldReturnProductTest() {
        Integer productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductImage productImage = new ProductImage();
        productImage.setId(1);
        productImage.setProductId(productId);
        productImage.setImagePath("/media/nike.jpg");
        when(productImageRepository.findProductImageByProductId(productId)).thenReturn(List.of(productImage));

        ProductVariant productVariant = new ProductVariant();
        productVariant.setId(1);
        productVariant.setProductId(productId);
        productVariant.setVariantName("Size");
        when(productVariantRepository.findProductVariantByProductId(productId)).thenReturn(List.of(productVariant));

        ProductReview productReview = new ProductReview();
        productReview.setId(1);
        productReview.setProductId(productId);
        productReview.setRating(5);
        when(productReviewRepository.findProductReviewByProductId(productId)).thenReturn(List.of(productReview));

        ProductDetailResponse actual = testRestTemplate.getForObject("/products/1", ProductDetailResponse.class);

        assertEquals(1, actual.getId());
        assertEquals("Nike zoom air", actual.getName());
        assertEquals("Nike", actual.getBrand());
        assertEquals("Mens Shoe", actual.getCategory());
        assertEquals("Lorem ipsum dorosit amet", actual.getDescription());
        assertEquals(Double.valueOf("399.0"), actual.getPrice());

        assertEquals("/media/nike.jpg", actual.getProductImage().get(0).getImagePath());
        assertEquals("Size", actual.getProductVariant().get(0).getVariantName());
        assertEquals(5, actual.getProductReview().get(0).getRating());
    }
}