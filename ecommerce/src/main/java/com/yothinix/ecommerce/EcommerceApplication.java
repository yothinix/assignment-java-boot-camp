package com.yothinix.ecommerce;

import com.yothinix.ecommerce.products.entity.Product;
import com.yothinix.ecommerce.products.entity.ProductImage;
import com.yothinix.ecommerce.products.entity.ProductReview;
import com.yothinix.ecommerce.products.entity.ProductVariant;
import com.yothinix.ecommerce.products.repository.ProductImageRepository;
import com.yothinix.ecommerce.products.repository.ProductRepository;
import com.yothinix.ecommerce.products.repository.ProductReviewRepository;
import com.yothinix.ecommerce.products.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class EcommerceApplication {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductImageRepository productImageRepository;

	@Autowired
	ProductVariantRepository productVariantRepository;

	@Autowired
	ProductReviewRepository productReviewRepository;

	@PostConstruct
	public void initialData() {
		Product product = new Product();
		product.setId(100);
		product.setName("Nike AirJordan");
		product.setBrand("Nike");
		product.setCategory("Men Shoe");
		product.setDescription("Nike shoe");
		product.setPrice(Double.valueOf("399.0"));
		productRepository.save(product);

		ProductImage productImage1 = new ProductImage(101, 100, "/media/air-jordan1.jpg", true);
		ProductImage productImage2 = new ProductImage(102, 100, "/media/air-jordan2.jpg", false);
		ProductImage productImage3 = new ProductImage(103, 100, "/media/air-jordan3.jpg", false);
		productImageRepository.save(productImage1);
		productImageRepository.save(productImage2);
		productImageRepository.save(productImage3);

		ProductVariant productVariant1 = new ProductVariant(101, 100, "Size", "S", true);
		ProductVariant productVariant2 = new ProductVariant(102, 100, "Size", "M", false);
		ProductVariant productVariant3 = new ProductVariant(103, 100, "Size", "L", false);
		productVariantRepository.save(productVariant1);
		productVariantRepository.save(productVariant2);
		productVariantRepository.save(productVariant3);

		ProductReview productReview1 = new ProductReview(101, 101, 100, "Very Good", 5);
		ProductReview productReview2 = new ProductReview(101, 102, 100, "So so", 3);
		ProductReview productReview3 = new ProductReview(101, 103, 100, "Nah", 1);
		productReviewRepository.save(productReview1);
		productReviewRepository.save(productReview2);
		productReviewRepository.save(productReview3);
	}

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
