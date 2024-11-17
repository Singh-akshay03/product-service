package com.products.services;

import com.products.models.Product;

public interface IProductService {
    Product getProductById(Long id);
}
