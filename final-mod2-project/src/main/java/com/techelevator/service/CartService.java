package com.techelevator.service;

import com.techelevator.client.TaxRateClient;
import com.techelevator.dao.CartItemDao;
import com.techelevator.dao.DTOs.CartItemDto;
import com.techelevator.dao.DTOs.ProductDto;
import com.techelevator.dao.ProductDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.ShoppingCart;
import com.techelevator.model.ShoppingCartItem;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static com.techelevator.dao.UserDao;

@Service
public class CartService {
    @Autowired
    CartItemDao cartItemDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    TaxRateClient taxRateClient;


    public CartService(CartItemDao cartItemDao, ProductDao productDao,
                       UserDao userDao, TaxRateClient taxRateClient){
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
        this.userDao = userDao;
        this.taxRateClient = taxRateClient;
    }




    public ShoppingCart getShoppingCartByUser(Principal userPrincipal) {

        String username = userPrincipal.getName();
        User user = userDao.getUserByUsername(username);
        List<CartItemDto> items = cartItemDao.getCartItemByUserId(user.getId());
        BigDecimal taxRate = taxRateClient.getTaxRate(user.getStateCode());
        ShoppingCart cart = new ShoppingCart();
        BigDecimal subTotal = BigDecimal.ZERO;
        List<ShoppingCartItem> shoppingCartItemsList = new ArrayList<>();
        for (CartItemDto cartItem: items){
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(cartItem);
            shoppingCartItemsList.add(shoppingCartItem);
            subTotal = subTotal.add(shoppingCartItem.getSubtotal());
        }


        BigDecimal taxes = subTotal.multiply(taxRate);
        BigDecimal totalWithTax = subTotal.add(taxes);

        cart.setShoppingCartItemList(shoppingCartItemsList);
        cart.setSubTotal(subTotal);
        cart.setTaxes(taxes);
        cart.setTotalWithTaxes(totalWithTax);

        return cart;
    }

    /*
    private CartItemDto mapCartItemToCart(CartItemDto cartItem){
        return new CartItemDto(cartItem);
    }

     */


    /*
    public ShoppingCart getShoppingCartByUser(Principal userPrincipal) {
        /*
        ShoppingCartItem item = new ShoppingCartItem();

        item.setName("Apple");

        ShoppingCart cart = new ShoppingCart();
//        cart.getSubTotal(BigDecimal.TEN);
//        cart.setShoppingCartItemList(Arrays.asList(item));
        String username = userPrincipal.getName();
        User user = userDao.getUserByUsername(username);
        List<CartItemDto> items = cartItemDao.getByCartItemUserId(user.getId());
        return cart;
    }

     */

}
