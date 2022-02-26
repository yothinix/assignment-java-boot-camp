package com.yothinix.ecommerce.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductImage {
    @Id
    private Integer id;
    private Integer productId;
    private String imagePath;
    private Boolean isFeatureImage;
}
