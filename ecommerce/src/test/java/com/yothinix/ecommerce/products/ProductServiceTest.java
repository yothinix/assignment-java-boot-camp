package com.yothinix.ecommerce.products;

import com.yothinix.ecommerce.exceptions.ProductNotFoundException;
import com.yothinix.ecommerce.products.entity.Product;
import com.yothinix.ecommerce.products.entity.ProductImage;
import com.yothinix.ecommerce.products.entity.ProductReview;
import com.yothinix.ecommerce.products.entity.ProductVariant;
import com.yothinix.ecommerce.products.repository.ProductImageRepository;
import com.yothinix.ecommerce.products.repository.ProductRepository;
import com.yothinix.ecommerce.products.repository.ProductReviewRepository;
import com.yothinix.ecommerce.products.repository.ProductVariantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductImageRepository productImageRepository;

    @Mock
    ProductVariantRepository productVariantRepository;

    @Mock
    ProductReviewRepository productReviewRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void searchProductByNameShouldReturnProductWhenQueryFoundTest() {
        Product product = new Product();
        product.setName("Adidas");
        when(productRepository.findProductByNameContains("Adidas"))
                .thenReturn(List.of(product));

        ProductListResponse actual = productService.searchBy("Adidas");

        assertEquals(1, actual.getProducts().size());
        assertEquals("Adidas", actual.getProducts().get(0).getName());
    }

    @Test
    void searchProductByNameShouldReturnEmptyListWhenQueryNotFoundTest() {
        when(productRepository.findProductByNameContains("Adidas"))
                .thenReturn(Collections.emptyList());

        ProductListResponse actual = productService.searchBy("Adidas");

        assertEquals(0, actual.getProducts().size());
    }

    @Test
    void getByProductIdShouldReturnProductDetailResponseTest() {
        Integer productId = 5;

        Product product = new Product();
        product.setId(productId);
        product.setName("Nike Air jordan");
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

        ProductDetailResponse actual = productService.getBy(productId);

        assertEquals(productId, actual.getId());
        assertEquals("Nike Air jordan", actual.getName());
        assertEquals("/media/nike.jpg", actual.getProductImage().get(0).getImagePath());
        assertEquals("Size", actual.getProductVariant().get(0).getVariantName());
        assertEquals(5, actual.getProductReview().get(0).getRating());
    }

    @Test
    void getByProductIdShouldThrowProductNotFoundWhenProductIdNotMatchTest() {
        Integer productId = 5;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getBy(productId));
    }
}