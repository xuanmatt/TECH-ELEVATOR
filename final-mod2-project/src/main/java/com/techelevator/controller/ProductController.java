package com.techelevator.controller;


import com.techelevator.dao.ProductDao;
import com.techelevator.exception.DaoException;
import com.techelevator.dao.DTOs.ProductDto;
import com.techelevator.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@PreAuthorize("permitAll")
@RequestMapping(value = "/products")
public class ProductController {
    //TODO Very good!  Nice and clean!

    @Autowired
    ProductService productService;

    @GetMapping()
    public List<ProductDto> listSearch(@RequestParam(name = "sku", defaultValue = "") String productSku,
                                       @RequestParam(name = "name", defaultValue = "") String productName){
        return productService.getAllProducts(productSku, productName);
    }




    @GetMapping(value = "/{id}")
    public ProductDto getProductById(@PathVariable int id){
        try {
            return productService.getProductById(id);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
