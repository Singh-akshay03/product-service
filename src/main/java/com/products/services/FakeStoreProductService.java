package com.products.services;

import com.products.dtos.FakeStoreProductDto;
import com.products.models.Category;
import com.products.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class FakeStoreProductService implements IProductService {

    RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductDto fakeStoreProductDTO = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);
        if (fakeStoreProductDTO == null) {
            throw new IllegalArgumentException("fakeStoreProductDTO cannot be null");
        }
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDTO);
    }

    private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto fakeStoreProductDTO) {
        Product product = new Product();
        product.setId(fakeStoreProductDTO.getId());
        product.setTitle(fakeStoreProductDTO.getTitle());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setPrice(fakeStoreProductDTO.getPrice());

        Category category = new Category();
        category.setId(null); // Assuming the ID is not available in the DTO
        category.setTitle(fakeStoreProductDTO.getCategory());
        product.setCategory(category);

        return product;
    }


}
