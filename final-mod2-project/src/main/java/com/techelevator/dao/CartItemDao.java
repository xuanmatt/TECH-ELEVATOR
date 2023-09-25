package com.techelevator.dao;

import com.techelevator.dao.DTOs.CartItemDto;

import java.util.List;

public interface CartItemDao {
    //CRUD


    //public CartItemDto getCartItemByUserId(int userId);
    List<CartItemDto> getCartItemByUserId(int userId);

    CartItemDto createCartDto(CartItemDto cartItemDto);

    int deleteCartItemById(int cart_item_id);

    int deleteWholeCartItem();




    /**
     * Get all cart items for a specific user
     * @param userId - id of user
     * @return List of CartItems
     */

}
