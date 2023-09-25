package com.techelevator.dao;

import com.techelevator.dao.DTOs.ProductDto;
import com.techelevator.exception.DaoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcProductDao(JdbcTemplate jdbcTemplate, JdbcTemplate jdbcTemplate1){

        this.jdbcTemplate = jdbcTemplate;
    }




    private ProductDto mapRowToProduct(SqlRowSet results) {
        ProductDto productDto =new ProductDto();
        //productDto.
        productDto.setProductId(results.getInt("product_id"));
        productDto.setProductSku(results.getString("product_sku"));
        productDto.setName(results.getString("name"));
        productDto.setDescription(results.getString("description"));
        productDto.setPrice(results.getBigDecimal("price"));
        productDto.setImageName(results.getString("image_name"));
        return productDto;
    }


    @Override
    public List<ProductDto> getAllProducts() {
        try {
            List<ProductDto> productDtoList = new ArrayList<>();
            String sql = "SELECT * FROM product;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                productDtoList.add(mapRowToProduct(results));
            }
            return productDtoList;
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Error connections with database",e);
        } catch (Exception e){
            throw new DaoException("Unknown error", e);
        }

    }

    @Override

    public ProductDto getProductById(int product_id) {
        try {
            ProductDto productDtoById = new ProductDto();
            String sql = "SELECT * FROM product WHERE product_id = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, product_id);
        while (results.next()) {
            productDtoById = (mapRowToProduct(results));
        }
        return productDtoById;
    } catch (CannotGetJdbcConnectionException e){
        throw new DaoException("Error connections with database",e);
    } catch (Exception e){
        throw new DaoException("Unknown error", e);
    }
    }

    @Override
    public List<ProductDto> getByName(String productName) {
        try {
            List<ProductDto> productDtoListByName = new ArrayList<>();

            String sql = "SELECT * FROM product WHERE LOWER(name) LIKE LOWER(?);";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + productName + "%");
            while (results.next()) {
                productDtoListByName.add(mapRowToProduct(results));
            }
            return productDtoListByName;
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Error connections with database",e);
        } catch (Exception e){
            throw new DaoException("Unknown error", e);
        }
    }

    @Override
    public ProductDto getBySku(String productSku) {
        try {
            ProductDto productDtoBySku = new ProductDto();
            String sql = "SELECT * FROM product WHERE product_sku = ?;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, productSku);
            while (results.next()) {
                productDtoBySku = (mapRowToProduct(results));
            }
            return productDtoBySku;
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Error connections with database",e);
        } catch (Exception e){
            throw new DaoException("Unknown error", e);
        }
    }
}
