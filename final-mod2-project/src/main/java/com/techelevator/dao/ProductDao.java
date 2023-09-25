package com.techelevator.dao;

import com.techelevator.dao.DTOs.ProductDto;

import java.util.List;

public interface ProductDao {
    //CRUD
    //ProductDto create(ProductDto item);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(int product_id);
    //ProductDto update(ProductDto Product);

    //public int deleteById(int id);

    List<ProductDto> getByName(String productName);

    ProductDto getBySku(String productSku);

    //List<ProductDto> getProductByNameAndSku(String productName, String productSku);
}
