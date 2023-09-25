package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.exception.DaoException;
import com.techelevator.ssgeek.model.Product;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Product getProductById(int productId) {
        Product product = null;
        try {
            String sql  = "SELECT product_id, name, description, price, image_name" +
                    " FROM product WHERE product_id = ? ;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, productId);
            if (results.next()){
                product = mapRowToProduct(results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return product;
    }


    @Override
    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();

        try {
            String sql  = "SELECT product_id, name, description, price, image_name" +
                    " FROM product ORDER BY product_id;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()){
                Product product = mapRowToProduct(results);
                productList.add(product);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return productList;
    }

    @Override
    public List<Product> getProductsWithNoSales() {
        List<Product> productListWithNoSale = new ArrayList<>();
        try{
            String sql = "SELECT product_id, name, description, price, image_name" +
                    " FROM product WHERE product_id NOT IN (SELECT product_id FROM line_item);";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()){
                Product product = mapRowToProduct(results);
                productListWithNoSale.add(product);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return productListWithNoSale;

    }

    @Override
    public Product createProduct(Product newProduct) {
        int newId;
        String sql = "INSERT INTO product(name, description, price, image_name)" +
                " VALUES (?,?,?,?) RETURNING product_id;";
        try {
            newId = jdbcTemplate.queryForObject(sql, int.class,newProduct.getName(), newProduct.getDescription(),
                    newProduct.getPrice(), newProduct.getImageName());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getProductById(newId);
    }

    @Override
    public Product updateProduct(Product updatedProduct) {
        try {
            String sql = "UPDATE product SET name = ?, description=?, price=?, image_name=? WHERE product_id=?;";
            jdbcTemplate.update(sql, updatedProduct.getName(), updatedProduct.getDescription(), updatedProduct.getPrice(),
                    updatedProduct.getImageName(), updatedProduct.getProductId());
            Product updateProduct = getProductById(updatedProduct.getProductId());
            //Department createdDepartment = getDepartmentById(generatedId);

            return updatedProduct;
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Error connections with database",e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Invalid Data for update", e);
        } catch (Exception e){
            throw new DaoException("Unknown error", e);
        }
    }


    @Override
    public int deleteProductById(int productId) {
        int numberOfRows = 5;
        String sql1 = "DELETE FROM line_item WHERE product_id=?;";
        String sql2 = "DELETE FROM product WHERE product_id=?;";

        try {
            jdbcTemplate.update(sql1, productId);
            jdbcTemplate.update(sql2,productId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return productId;

    }



    private Product mapRowToProduct(SqlRowSet results) {
        Product product = new Product();

        product.setProductId(results.getInt("product_id"));
        product.setName(results.getString("name"));
        product.setDescription(results.getString("description"));
        product.setPrice(results.getBigDecimal("price"));
        product.setImageName(results.getString("image_name"));
        return product;
    }
}
