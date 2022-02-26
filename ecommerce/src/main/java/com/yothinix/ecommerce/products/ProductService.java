package com.yothinix.ecommerce.products;

import com.yothinix.ecommerce.exceptions.ProductNotFoundException;
import com.yothinix.ecommerce.products.entity.Product;
import com.yothinix.ecommerce.products.repository.ProductImageRepository;
import com.yothinix.ecommerce.products.repository.ProductRepository;
import com.yothinix.ecommerce.products.repository.ProductReviewRepository;
import com.yothinix.ecommerce.products.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    ProductVariantRepository productVariantRepository;

    @Autowired
    ProductReviewRepository productReviewRepository;

    public ProductListResponse searchBy(String name) {
        List<Product> products = productRepository.findProductByNameContains(name);
        return new ProductListResponse(products);
    }

    public ProductDetailResponse getBy(Integer productId) {
        ProductDetailResponse response;
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product id: " + productId + " is not found");
        }

        Product product = optionalProduct.get();

        response = new ProductDetailResponse(product);
        response.setProductImage(productImageRepository.findProductImageByProductId(productId));
        response.setProductVariant(productVariantRepository.findProductVariantByProductId(productId));
        response.setProductReview(productReviewRepository.findProductReviewByProductId(productId));

        return response;
    }
}
