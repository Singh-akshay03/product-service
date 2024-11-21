package com.products.services;

import com.products.dtos.FakeStoreProductDto;
import com.products.exceptions.ProductNotFoundException;
import com.products.models.Category;
import com.products.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FakeStoreProductService implements IProductService {

    RestTemplate restTemplate;
    String url = "https://fakestoreapi.com/products";

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDTO = restTemplate.getForObject(url+"/" + id, FakeStoreProductDto.class);
        if (fakeStoreProductDTO == null) {
            throw new ProductNotFoundException("Product Not Found with  the id "+id, 100L);
        }
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDTO);
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProducts=restTemplate.getForObject(url, FakeStoreProductDto[].class);
        if (fakeStoreProducts == null) {
            throw new IllegalArgumentException("fakeStoreProducts cannot be null");
        }
        return Arrays.stream(fakeStoreProducts)
                .map(this::convertFakeStoreProductDtoToProduct)
                .collect(Collectors.toList());
    }

    @Override
    public Product updateProductById(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDTO = convertProductToFakeStoreProductDto(product);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDTO, FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        FakeStoreProductDto updatedProduct= Objects
                .requireNonNull(restTemplate.execute(url + "/" + id, HttpMethod.PUT, requestCallback, responseExtractor))
                .getBody();
        restTemplate.put(url + "/" + id, fakeStoreProductDTO);
        if (updatedProduct == null) {
            throw new IllegalArgumentException("updatedProduct cannot be null");
        }
        return convertFakeStoreProductDtoToProduct(updatedProduct);
    }

    @Override
    public Product deleteProductById(Long id) throws ProductNotFoundException {
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto= restTemplate.exchange(url+"/"+id,HttpMethod.DELETE,null,FakeStoreProductDto.class).getBody();
        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("Product Not Found with  the id "+id, 100L);
        }
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto fakeStoreProductDTO) {
        Product product = new Product();
        product.setId(fakeStoreProductDTO.getId());
        product.setTitle(fakeStoreProductDTO.getTitle());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setPrice(fakeStoreProductDTO.getPrice());

        Category category = new Category();
        category.setId(null);
        category.setTitle(fakeStoreProductDTO.getCategory());
        product.setCategory(category);

        return product;
    }
    private FakeStoreProductDto convertProductToFakeStoreProductDto(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        return fakeStoreProductDto;
    }


}
