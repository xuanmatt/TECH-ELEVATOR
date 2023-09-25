package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.List;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcCustomerDaoTest extends BaseDaoTests {

    private static final Customer CUSTOMER_1 = new Customer(1,"Customer 1", "Addr 1-1", "Addr 1-2", "City 1", "S1", "11111");
    private static final Customer CUSTOMER_2 = new Customer(2,"Customer 2", "Addr 2-1", "Addr 2-2", "City 2", "S2", "22222");
    private static final Customer CUSTOMER_3 = new Customer(3,"Customer 3", "Addr 3-1", null, "City 3", "S3", "33333");
    private static final Customer CUSTOMER_4 = new Customer(4,"Customer 4", "Addr 4-1", null, "City 4", "S4", "44444");
    private static final Customer CUSTOMER_5 = new Customer(5,"Matt", "4321 Sunset Street", null, "NYC", "NY","11101");

    private JdbcCustomerDao jdbcCustomerDao;
    @Before
    public void setup(){
        jdbcCustomerDao = new JdbcCustomerDao(dataSource);
    }


    @Test
    public void getCustomerById_returns_should_return_null_if_id_doesnot_exist() {
        Assert.assertNull(jdbcCustomerDao.getCustomerById(10999));
    }

    @Test
    public void getCustomerById_returns_should_return_null_if_id_does_exist() {
        assertCustomerMatch(CUSTOMER_1, jdbcCustomerDao.getCustomerById(1));
        assertCustomerMatch(CUSTOMER_2, jdbcCustomerDao.getCustomerById(2));
        assertCustomerMatch(CUSTOMER_3, jdbcCustomerDao.getCustomerById(3));
        assertCustomerMatch(CUSTOMER_4, jdbcCustomerDao.getCustomerById(4));

    }


    @Test
    public void getCustomers() {
        List<Customer> customerList = jdbcCustomerDao.getCustomers();

        Assert.assertEquals(4, jdbcCustomerDao.getCustomers().size());

    }


    @Test
    public void createCustomer() {
        Customer input = new Customer(0,"Matt", "4321 Sunset Street", null, "NYC", "NY","11101");
        Customer expected = new Customer(5,"Matt", "4321 Sunset Street", null, "NYC", "NY","11101");
        assertCustomerMatch(expected, jdbcCustomerDao.createCustomer(input));
    }



    /*
    @Test
    public void createCustomer_when_ID_is_retrived() {
        Customer input = new Customer(0,"Matt", "4321 Sunset Street", null, "NYC", "NY","11101");
        Customer expected = new Customer(5,"Matt", "4321 Sunset Street", null, "NYC", "NY","11101");
        jdbcCustomerDao.createCustomer(input);
        Customer actual =jdbcCustomerDao.getCustomerById(5);
        assertCustomerMatch(expected, actual);
    }

     */

    @Test
    public void updateCustomer() {
        Customer input = new Customer(4, "fds", "dfs",
                "ogf", "pq", "oe", "24313");
        jdbcCustomerDao.updateCustomer(input);
        Customer actual = jdbcCustomerDao.getCustomerById(4);
        assertCustomerMatch(input,actual);
    }

    private void assertCustomerMatch(Customer expected, Customer input){
        assertEquals(expected.getCustomerId(), input.getCustomerId());
        assertEquals(expected.getName(), input.getName());
        assertEquals(expected.getStreetAddress1(), input.getStreetAddress1());
        assertEquals(expected.getStreetAddress2(), input.getStreetAddress2());
        assertEquals(expected.getCity(), input.getCity());
        assertEquals(expected.getState(), input.getState());
        assertEquals(expected.getZipCode(), input.getZipCode());

    }
}