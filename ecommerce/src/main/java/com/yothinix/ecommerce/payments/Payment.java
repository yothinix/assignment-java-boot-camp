package com.yothinix.ecommerce.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private String method;
    private String number;
    private String name;
    private String paymentExpiryDate;
    private String secureCode;
}
