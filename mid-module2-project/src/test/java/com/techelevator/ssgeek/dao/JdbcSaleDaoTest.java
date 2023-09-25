package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.exception.DaoException;
import com.techelevator.ssgeek.model.Customer;
import com.techelevator.ssgeek.model.Product;
import com.techelevator.ssgeek.model.Sale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.*;

public class JdbcSaleDaoTest extends BaseDaoTests{
    private static final Sale SALE_1 = new Sale(1,1, LocalDate.of(2022,01,01), null, "Customer 1");
    private static final Sale SALE_2 = new Sale(2,1, LocalDate.of(2022,02,01), LocalDate.of(2022,02,02), "Customer 1");
    private static final Sale SALE_3 = new Sale(3,2, LocalDate.of(2022,03,01), null, "Customer 2");
    private static final Sale SALE_4 = new Sale(4,2, LocalDate.of(2022,01,01), LocalDate.of(2022,01,02), "Customer 2");
    private static final Sale SALE_5 = new Sale(5,5, LocalDate.of(2022,8,04), null, "Matt");

    private JdbcSaleDao jdbcSaleDao;


    @Before
    public void setup(){
        jdbcSaleDao = new JdbcSaleDao(dataSource);
    }

    @Test
    public void getSaleById() {
        Assert.assertNull(jdbcSaleDao.getSaleById(10999));

    }

    @Test
    public void getSaleById_returns_should_return_null_if_id_does_exist() {
        assertSaleMatch(SALE_1, jdbcSaleDao.getSaleById(1));
        assertSaleMatch(SALE_2, jdbcSaleDao.getSaleById(2));
        assertSaleMatch(SALE_3, jdbcSaleDao.getSaleById(3));
        assertSaleMatch(SALE_4, jdbcSaleDao.getSaleById(4));

    }
    @Test
    public void getUnshippedSales() {
        List<Sale> sales = jdbcSaleDao.getUnshippedSales();
        Assert.assertEquals(2,jdbcSaleDao.getUnshippedSales().size());
    }

    @Test
    public void getSalesByCustomerId() {
        List<Sale> saleList1 = jdbcSaleDao.getSalesByCustomerId(1);
        Assert.assertEquals(2,saleList1.size());

    }

    @Test
    public void getSalesByProductId() {
        List<Sale> saleList2 = jdbcSaleDao.getSalesByProductId(1);
        Assert.assertEquals(3,saleList2.size());
    }

    @Test
    public void createSale() {
        Sale input = new Sale( 2, LocalDate.of(2022,8,04), null,"Matt");
        //input.setCustomerName("Matt");

        Sale expected = new Sale( 2, LocalDate.of(2022,8,04), null, "Matt");
        //expected.setSaleId(5);
        Sale methodCreateSale = jdbcSaleDao.createSale(input);
        int newSaleId = methodCreateSale.getSaleId();
        expected.setSaleId(newSaleId);
        assertSaleMatch(expected, methodCreateSale);
    }
    @Test
    public void createSale_when_ID_is_retrived() {
        Sale input = new Sale(0,1, LocalDate.of(2022,8,04), null, "Matt");
        Sale expected = new Sale(5,1, LocalDate.of(2022,8,04), null, "Matt");
        jdbcSaleDao.createSale(input);
        Sale actual =jdbcSaleDao.getSaleById(5);
        assertSaleMatch(expected, actual);
    }

    @Test
    public void updateSale() {
        Sale input = new Sale(4,1, LocalDate.of(2022,8,04), null, "fddsad");
        jdbcSaleDao.updateSale(input);
        Sale actual = jdbcSaleDao.getSaleById(4);
        assertSaleMatch(input,actual);
    }

    @Test
    public void deleteSaleById() {
        jdbcSaleDao.createSale(SALE_4);
        jdbcSaleDao.deleteSaleById(4);
        assertNull(jdbcSaleDao.getSaleById(4    ));
    }

    private void assertSaleMatch(Sale expected, Sale input){
        assertEquals(expected.getSaleId(), input.getSaleId());
        assertEquals(expected.getSaleId(), input.getSaleId());

        assertEquals(expected.getCustomerId(), input.getCustomerId());
        assertEquals(expected.getSaleDate(), input.getSaleDate());
        assertEquals(expected.getShipDate(), input.getShipDate());

    }
}