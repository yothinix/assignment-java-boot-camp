package com.yothinix.ecommerce.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {
    @Id
    private Integer id;
    private Integer userId;
    private Integer paymentId;
    private Integer shippingId;
    private Double totalAmount;
    private Date transactionDate;
    private Date expiredDate;
    private String orderStatus;

}
