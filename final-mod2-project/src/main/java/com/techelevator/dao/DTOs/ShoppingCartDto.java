package com.techelevator.dao.DTOs;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCartDto {
    private List<CartItemDto> shoppingCart;
    private BigDecimal cartSubtotal;
    private BigDecimal cartTotalPriceAfterTax;



    public ShoppingCartDto(List<CartItemDto> shoppingCart){
        this.shoppingCart = shoppingCart;

    }

    public List<CartItemDto> getShoppingCart(){
        return shoppingCart;
    }

    public void setShoppingCart(List<CartItemDto> shoppingCart){
        this.shoppingCart = shoppingCart;
    }

    public BigDecimal getCartSubtotal(){
        return cartSubtotal;
    }

    public void setCartSubtotal(BigDecimal cartSubtotal){
        this.cartSubtotal = cartSubtotal;
    }

    public BigDecimal getCartTotalPriceAfterTax(){
        return cartTotalPriceAfterTax;
    }

    public void setCartTotalPriceAfterTax(BigDecimal cartTotalPriceAfterTax){
        this.cartTotalPriceAfterTax = cartTotalPriceAfterTax;
    }


}
