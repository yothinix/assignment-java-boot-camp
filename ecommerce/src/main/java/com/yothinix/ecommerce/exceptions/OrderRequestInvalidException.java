package com.yothinix.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderRequestInvalidException extends RuntimeException {
    public OrderRequestInvalidException(String message) {
        super(message);
    }
}
