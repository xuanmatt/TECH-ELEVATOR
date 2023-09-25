package com.techelevator.dao.DTOs;

import java.math.BigDecimal;

public class CartItemDto {
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Integer cartItemId;
    private String name;
    private String imageName;
    private BigDecimal unitPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Integer cartItemId) {
        this.cartItemId = cartItemId;
    }

    @Override
    public String toString() {
        return "CartItemDto{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", cartItemId=" + cartItemId +
                '}';
    }
}
