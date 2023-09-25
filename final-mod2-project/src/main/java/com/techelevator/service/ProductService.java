package com.techelevator.service;

import com.techelevator.dao.ProductDao;
import com.techelevator.exception.DaoException;
import com.techelevator.dao.DTOs.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    //TODO Nice!

    @Autowired
    private ProductDao productDao;

    public ProductService(ProductDao productDao){
        this.productDao = productDao;
    }

    public List<ProductDto> getAllProducts(String productSku, String productName){
        if ( productSku.isEmpty() && productName.isEmpty()){
            return productDao.getAllProducts();
        }
        if (!productSku.isEmpty() ){
            List<ProductDto> productDtoList = new ArrayList<>();
            productDtoList.add(productDao.getBySku(productSku));
            return productDtoList;
        }
        return productDao.getByName(productName);
    }


    public ProductDto getProductById(int id) {
        return productDao.getProductById(id);

    }
}
