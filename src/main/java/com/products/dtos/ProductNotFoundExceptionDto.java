package com.products.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductNotFoundExceptionDto {
    Long id;
    String message;
    public ProductNotFoundExceptionDto(String message,Long id) {
        this.message = message;
        this.id = id;
    }

    public ProductNotFoundExceptionDto() {

    }
}
