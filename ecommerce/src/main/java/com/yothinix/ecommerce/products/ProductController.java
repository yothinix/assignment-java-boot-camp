package com.yothinix.ecommerce.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ProductListResponse getProductList(@RequestParam String name) {
        return productService.searchBy(name);
    }

    @GetMapping("/products/{id}")
    public ProductDetailResponse getProductDetail(@PathVariable(value = "id") Integer productId) {
        return productService.getBy(productId);
    }
}
