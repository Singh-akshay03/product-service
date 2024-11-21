package com.products.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductNotFoundException extends Exception{

    Long errorCode;

    public ProductNotFoundException(String message,Long errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
