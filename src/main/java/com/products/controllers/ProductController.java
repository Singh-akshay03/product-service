package com.products.controllers;

import com.products.dtos.ProductNotFoundExceptionDto;
import com.products.exceptions.ProductNotFoundException;
import com.products.models.Product;
import com.products.services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    @GetMapping("")
    ResponseEntity<List<Product> >getAllProducts() {
        List<Product> list = productService.getAllProducts();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    ResponseEntity<Product> updateProductById(@PathVariable("id") Long id, @RequestBody Product product) {
        Product updateProductById=productService.updateProductById(id, product);
        return new ResponseEntity<>(updateProductById, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<Product> deleteProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product deletedProductById= productService.deleteProductById(id);
        return new ResponseEntity<>(deletedProductById, HttpStatus.OK);
    }

}
