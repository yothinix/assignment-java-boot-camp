package com.yothinix.ecommerce.orders;

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
public class OrderItem {
    @Id
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Double amount;
    private Double paymentPrice;
}
