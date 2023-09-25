package com.techelevator.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private BigDecimal taxRate;

    private List<ShoppingCartItem> shoppingCartItemList;
    private BigDecimal taxes;
    private BigDecimal subTotal;
    private  BigDecimal totalWithTaxes;

    public ShoppingCart(){
        this.taxRate = taxRate;
        subTotal = BigDecimal.ZERO;
        shoppingCartItemList = new ArrayList<>();
        taxes = BigDecimal.ZERO;
        totalWithTaxes = BigDecimal.ZERO;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public List<ShoppingCartItem> getShoppingCartItemList() {
        return shoppingCartItemList;
    }

    public void setShoppingCartItemList(List<ShoppingCartItem> shoppingCartItemList) {
        this.shoppingCartItemList = shoppingCartItemList;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getSubTotal(BigDecimal ten) {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTotalWithTaxes() {
        return totalWithTaxes;
    }

    public void setTotalWithTaxes(BigDecimal totalWithTaxes) {
        this.totalWithTaxes = totalWithTaxes;
    }

    private void calculateTotals(){
        subTotal = BigDecimal.ZERO;
        for (ShoppingCartItem item: shoppingCartItemList){
            subTotal = subTotal.add(item.getSubtotal());
        }
        taxes = subTotal.multiply(taxRate);
        totalWithTaxes = subTotal.add(taxes);

    }
}
