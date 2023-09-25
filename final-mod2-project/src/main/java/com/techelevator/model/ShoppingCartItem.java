package com.techelevator.model;

import com.techelevator.dao.DTOs.CartItemDto;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCartItem {
    private String name;
    private String image;
    private String description;
    private Integer quantity;
    private BigDecimal subtotal;

    public ShoppingCartItem(CartItemDto item) {
        this.image = item.getImageName();
        this.name = item.getName();
        this.quantity = item.getQuantity();
        this.subtotal = item.getUnitPrice(). multiply(BigDecimal.valueOf(quantity));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}

