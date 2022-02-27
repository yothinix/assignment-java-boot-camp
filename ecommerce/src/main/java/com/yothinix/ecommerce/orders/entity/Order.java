package com.yothinix.ecommerce.orders.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`ORDER`")
public class Order {
    @Id
    @GeneratedValue
    private Integer id;

    @JsonIgnoreProperties
    private Integer userId;

    @JsonIgnoreProperties
    private Integer paymentId;

    @JsonIgnoreProperties
    private Integer shippingId;

    private Double totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;

    private String orderStatus;

}
