package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JdbcProductDaoTest extends BaseDaoTests{
    private static final Product PRODUCT_1 = new Product(1,"Product 1","Description 1", new BigDecimal("9.99"),"product-1.png");
    private static final Product PRODUCT_2 = new Product(2,"Product 2","Description 2", new BigDecimal("19.00"),"product-2.png");
    private static final Product PRODUCT_3 = new Product(3,"Product 3","Description 3", new BigDecimal("123.45"),"product-3.png");
    private static final Product PRODUCT_4 = new Product(4,"Product 4","Description 4", new BigDecimal("0.99"),"product-4.png");
    private static final Product PRODUCT_5 = new Product(5,"Product 5","fgsdfg", new BigDecimal("99.2"),"product-5.png");
    private JdbcProductDao jdbcProductDao;

    @Before
    public void setup(){
        jdbcProductDao = new JdbcProductDao(dataSource);
    }
    @Test
    public void getProductById_returns_should_return_null_if_id_doesnot_exist() {
        Assert.assertNull(jdbcProductDao.getProductById(10999));
    }

    @Test
    public void getProductById_returns_should_return_null_if_id_does_exist() {
        assertProductMatch(PRODUCT_1, jdbcProductDao.getProductById(1));
        assertProductMatch(PRODUCT_2, jdbcProductDao.getProductById(2));
        assertProductMatch(PRODUCT_3, jdbcProductDao.getProductById(3));
        assertProductMatch(PRODUCT_4, jdbcProductDao.getProductById(4));

    }

    @Test
    public void getProducts() {
        List<Product> productList = jdbcProductDao.getProducts();

        Assert.assertEquals(4, jdbcProductDao.getProducts().size());
    }

    @Test
    public void getProductsWithNoSales() {
        List<Product> products = jdbcProductDao.getProducts();
        Assert.assertEquals(1,jdbcProductDao.getProductsWithNoSales().size());
    }


    /*
    @Test
    public void createProduct() {
        Product input = new Product(0,"Product 5","fgsdfg", new BigDecimal(99.2),"product-5.png");
        Product expected = new Product(5,"Product 5","fgsdfg", new BigDecimal(99.2),"product-5.png");
        assertProductMatch(expected,jdbcProductDao.createProduct(expected));
    }

     */

    @Test
    public void createProduct_when_ID_retrived(){
        Product input = new Product(0,"Product 5","fgsdfg", new BigDecimal("99.22"),"product-5.png");
        Product expected = new Product(5,"Product 5","fgsdfg", new BigDecimal("99.22"),"product-5.png");
        Product actual = jdbcProductDao.createProduct(input);
        expected.setProductId(actual.getProductId());
        assertProductMatch(expected,actual);

    }

    @Test
    public void updateProduct() {
        Product input = new Product(4,"DSFDFS","POIUHYG",new BigDecimal("983.67"), "polkmdf");
        jdbcProductDao.updateProduct(input);
        Product actual= jdbcProductDao.getProductById(4);
        assertProductMatch(input, actual);
    }

    @Test
    public void deleteProductById() {
        jdbcProductDao.createProduct(PRODUCT_5);
        jdbcProductDao.deleteProductById(5);
        assertNull(jdbcProductDao.getProductById(5));
    }
    private void assertProductMatch(Product expected, Product input){
        assertEquals(expected.getProductId(), input.getProductId());
        assertEquals(expected.getName(), input.getName());
        assertEquals(expected.getDescription(), input.getDescription());
        assertEquals(expected.getPrice(), input.getPrice());

        assertEquals(expected.getImageName(), input.getImageName());

    }
}