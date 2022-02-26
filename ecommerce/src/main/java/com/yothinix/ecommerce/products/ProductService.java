package com.yothinix.ecommerce.products;

import com.yothinix.ecommerce.products.entity.Product;
import com.yothinix.ecommerce.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductListResponse searchBy(String name) {
        List<Product> products = productRepository.findProductByNameContains(name);
        return new ProductListResponse(products);
    }
}
