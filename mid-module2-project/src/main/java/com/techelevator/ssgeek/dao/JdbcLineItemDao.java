package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.LineItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcLineItemDao implements LineItemDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcLineItemDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<LineItem> getLineItemsBySaleId(int saleId) {
        List<LineItem> lineItemList = new ArrayList<>();
        String sql ="SELECT line_item_id, sale_id, line_item.product_id, quantity,product.name,product.price FROM line_item " +
                "JOIN product ON line_item.product_id=product.product_id WHERE line_item.sale_id=?;";
        SqlRowSet results =jdbcTemplate.queryForRowSet(sql,saleId);
        while (results.next()){
            LineItem lineItem = new LineItem();

            lineItem.setLineItemId(results.getInt("line_item_id"));
            lineItem.setSaleId(results.getInt("sale_id"));
            lineItem.setProductId(results.getInt("product_id"));
            lineItem.setQuantity(results.getInt("quantity"));
            lineItem.setProductName(results.getString("name"));
            lineItem.setPrice(results.getBigDecimal("price"));
            lineItemList.add(lineItem);
        }
        return lineItemList;
    }
}
