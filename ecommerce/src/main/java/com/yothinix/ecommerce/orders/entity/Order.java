package com.yothinix.ecommerce.orders.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnoreProperties
    private Integer userId;

    @JsonIgnoreProperties
    private Integer paymentId;

    @JsonIgnoreProperties
    private Integer shippingId;

    private Double totalAmount;
    private Date transactionDate;
    private Date expiredDate;
    private String orderStatus;

}
