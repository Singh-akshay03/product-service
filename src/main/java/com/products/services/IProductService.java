package com.products.services;

import com.products.exceptions.ProductNotFoundException;
import com.products.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id) throws ProductNotFoundException;
    List<Product> getAllProducts();
    Product updateProductById(Long id, Product product);
    Product deleteProductById(Long id) throws ProductNotFoundException;
}
