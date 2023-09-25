package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.LineItem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcLineItemDaoTest extends BaseDaoTests{

    private static final LineItem LINE_ITEM_1 = new LineItem(1,1,1,1,"Product 1", new BigDecimal("9.99"));
    private static final LineItem LINE_ITEM_2 = new LineItem(2,1,2,1,"Product 2", new BigDecimal("10.00"));
    private static final LineItem LINE_ITEM_3 = new LineItem(3,1,4,1,"Product 4", new BigDecimal("0.99"));
    private static final LineItem LINE_ITEM_4 = new LineItem(4,2,4,10,"Product 4", new BigDecimal("0.99"));
    private static final LineItem LINE_ITEM_5 = new LineItem(5,2,1,10,"Product 1", new BigDecimal("9.99"));
    private static final LineItem LINE_ITEM_6 = new LineItem(6,3,1,100,"Product 1", new BigDecimal("9.99"));


    private JdbcLineItemDao jdbcLineItemDao;
    @Before
    public void setup(){
        jdbcLineItemDao = new JdbcLineItemDao(dataSource);
    }
    @Test
    public void getLineItemsBySaleId() {

        List<LineItem> lineItemList =jdbcLineItemDao.getLineItemsBySaleId(1);
        Assert.assertEquals(3, lineItemList.size());
    }
}