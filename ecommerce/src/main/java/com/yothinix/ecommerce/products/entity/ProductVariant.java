package com.yothinix.ecommerce.products.entity;

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
public class ProductVariant {
    @Id
    private Integer id;
    private Integer productId;
    private String variantName;
    private String variantChoice;
    private Boolean isDefaultChoice;
}
