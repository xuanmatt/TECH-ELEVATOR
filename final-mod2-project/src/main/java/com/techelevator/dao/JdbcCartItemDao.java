package com.techelevator.dao;

import com.techelevator.dao.DTOs.CartItemDto;
import com.techelevator.exception.DaoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCartItemDao implements CartItemDao{

    private final JdbcTemplate jdbcTemplate;
    public JdbcCartItemDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }




    @Override
    public List<CartItemDto> getCartItemByUserId(int userId) {
        List<CartItemDto> itemList = new ArrayList<>();
        String sql = "SELECT cart_item_id, user_id, cart_item.product_id," +
                "quantity,product.product_id, product.product_sku, product.name, product.description" +
                "product.price, product.image_name FROM cart_item JOIN product ON cart_item.product_id = product.product_id WHERE cart_item.user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()){
            itemList.add(mapRowToCartItem(results));
        }
        return itemList;
    }

    //TODO You also need an updateCartDto method. Remember INSERT only allows you to create a database record, not update it.
    @Override
    public CartItemDto createCartDto(CartItemDto cartItemDto) {
        try {
            String sql = "INSERT INTO cart_item(cart_item_id, user_id, product_id, quantity) VALUES (?,?,?,?) RETURNING cart_item_id;";
            int new_cart_item_id = jdbcTemplate.queryForObject(sql, int.class, cartItemDto.getCartItemId(),
                    cartItemDto.getUserId(), cartItemDto.getProductId(), cartItemDto.getQuantity());

            CartItemDto createdCartItemDto = (CartItemDto) getCartItemByUserId(new_cart_item_id);
            return createdCartItemDto;

        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Error connections with database",e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Invalid Data for insert", e);
        } catch (Exception e){
            throw new DaoException("Unknown error", e);
        }
    }

    @Override
    public int deleteCartItemById(int cart_item_id) {
        try {
            int numberOfCartItemDeleted;

            String sql = "DELETE FROM cart_item WHERE cart_item_id = ?";
            jdbcTemplate.update(sql, cart_item_id);
            numberOfCartItemDeleted = jdbcTemplate.update(sql,cart_item_id);
            return numberOfCartItemDeleted;
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Error connections with database",e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Invalid Data for insert", e);
        } catch (Exception e){
            throw new DaoException("Unknown error", e);
        }
    }

    @Override
    public int deleteWholeCartItem() {
        return 0;
    }

    private CartItemDto mapRowToCartItem(SqlRowSet sqlRowSet) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCartItemId(sqlRowSet.getInt("cart_item_id"));
        cartItemDto.setUserId(sqlRowSet.getInt("user_id"));
        cartItemDto.setProductId(sqlRowSet.getInt("product_id"));
        cartItemDto.setQuantity(sqlRowSet.getInt("quantity"));
        cartItemDto.setName(sqlRowSet.getString("name"));
        cartItemDto.setImageName(sqlRowSet.getString("image_name"));
        cartItemDto.setUnitPrice(sqlRowSet.getBigDecimal("price"));
        return cartItemDto;
    }




}
