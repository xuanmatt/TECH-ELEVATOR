package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.exception.DaoException;
import com.techelevator.ssgeek.model.Customer;
import com.techelevator.ssgeek.model.Product;
import com.techelevator.ssgeek.model.Sale;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class JdbcSaleDao implements SaleDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcSaleDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @Override
    public Sale getSaleById(int saleId) {
        Sale sale = null;
        try {
            String sql  = "SELECT sale_id,sale.customer_id,name, sale_date, ship_date" +
                    " FROM sale JOIN customer ON customer.customer_id = sale.customer_id WHERE sale_id = ? ;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, saleId);
            while (results.next()){
                sale = mapRowToSale(results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return sale;
    }



    @Override
    public List<Sale> getUnshippedSales() {
        List<Sale> unShipSaleList = new ArrayList<>();

        try {
            String sql  = "SELECT sale_id, customer.customer_id, sale_date, ship_date, customer.name" +
                    " FROM sale JOIN customer ON customer.customer_id = sale.customer_id WHERE ship_date IS NULL ORDER BY sale_id;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()){
                Sale sale = mapRowToSale(results);
                unShipSaleList.add(sale);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return unShipSaleList;

    }

    @Override
    public List<Sale> getSalesByCustomerId(int customerId) {
        List<Sale> saleList1 = new ArrayList<>();

        try {
            String sql  = "SELECT sale_id, customer.customer_id, customer.name, sale_date, ship_date" +
                    " FROM sale JOIN customer ON sale.customer_id = customer.customer_id" +
                    " WHERE sale.customer_id = ? ORDER BY sale_id;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, customerId);
            while (results.next()){
                Sale sale = mapRowToSale(results);
                saleList1.add(sale);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return saleList1;
    }

    @Override
    public List<Sale> getSalesByProductId(int productId) {
        List<Sale> saleList2 = new ArrayList<>();

        try {
            String sql  = "SELECT sale.sale_id, customer.customer_id, sale_date, ship_date, customer.name" +
                    " FROM sale JOIN line_item ON sale.sale_id = line_item.sale_id" +
                    " JOIN product ON product.product_id = line_item.product_id" +
                    " JOIN customer ON customer.customer_id = sale.customer_id WHERE product.product_id = ? ORDER BY sale_id;";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, productId);
            while (results.next()){
                Sale sale = mapRowToSale(results);
                saleList2.add(sale);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return saleList2;

    }

    @Override
    public Sale createSale(Sale newSale) {
        int newId;
        String sql = "INSERT INTO sale(customer_id, sale_date, ship_date)" +
                " VALUES (?,?,?) RETURNING sale_id;";
        try {
            newId = jdbcTemplate.queryForObject(sql, int.class,newSale.getCustomerId(),
                    newSale.getSaleDate(), newSale.getShipDate());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getSaleById(newId);
    }



    @Override
    public Sale updateSale(Sale updatedSale) {
        try {
            String sql = "UPDATE sale SET customer_id = ?, sale_date=?, ship_date=? WHERE sale_id=?;";
            jdbcTemplate.update(sql, updatedSale.getCustomerId(), updatedSale.getSaleDate(),
                    updatedSale.getShipDate(), updatedSale.getSaleId());
            updatedSale = getSaleById(updatedSale.getSaleId());

            return updatedSale;
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Error connections with database",e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Invalid Data for update", e);
        } catch (Exception e){
            throw new DaoException("Unknown error", e);
        }
    }

    @Override
    public int deleteSaleById(int saleId) {
        int numberOfRows = 5;
        String sql1 = "DELETE FROM line_item WHERE sale_id=?;";
        String sql2 = "DELETE FROM sale WHERE sale_id=?;";

        try {
            jdbcTemplate.update(sql1,saleId);
            jdbcTemplate.update(sql2,saleId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return saleId;
    }

    private Sale mapRowToSale(SqlRowSet results) {
        Sale sale = new Sale();

        sale.setSaleId(results.getInt("sale_id"));
        sale.setCustomerId(results.getInt("customer_id"));
        sale.setCustomerName(results.getString("name"));
        Date sale_date = results.getDate("sale_date");
        if (sale_date != null) {
            sale.setSaleDate(results.getDate("sale_date").toLocalDate());
        }
        Date ship_date = results.getDate("ship_date");
        if (ship_date != null) {
            sale.setShipDate(results.getDate("ship_date").toLocalDate());
        }
        return sale;
    }
}
