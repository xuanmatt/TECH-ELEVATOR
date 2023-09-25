package com.techelevator.controller;

import com.techelevator.model.ShoppingCart;
import com.techelevator.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/cart")
//@PreAuthorize("isAuthenticated()")
@PreAuthorize("permitAll")
public class CartController {
    //TODO Missing a few methods

    @Autowired
    CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }



    @RequestMapping(path ="", method = RequestMethod.GET)
    public ShoppingCart getShoppingCartByUser(Principal userPrincipal){
        return cartService.getShoppingCartByUser(userPrincipal);
    }

}
