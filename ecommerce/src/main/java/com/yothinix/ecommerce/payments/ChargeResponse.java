package com.yothinix.ecommerce.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargeResponse {
    private String statusCode;
    private String description;
    private String transactionId;
}
