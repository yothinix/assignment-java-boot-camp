package com.yothinix.ecommerce.products;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yothinix.ecommerce.products.entity.Product;
import com.yothinix.ecommerce.products.entity.ProductImage;
import com.yothinix.ecommerce.products.entity.ProductReview;
import com.yothinix.ecommerce.products.entity.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductDetailResponse extends Product {
    private List<ProductImage> productImage;
    private List<ProductVariant> productVariant;
    private List<ProductReview> productReview;

    ProductDetailResponse(Product product) {
        this.setId(product.getId());
        this.setName(product.getName());
        this.setBrand(product.getBrand());
        this.setCategory(product.getCategory());
        this.setDescription(product.getDescription());
        this.setPrice(product.getPrice());
    }
}
