package com.thqu1et.e_commerces.service;

import com.thqu1et.e_commerces.exception.CartItemException;
import com.thqu1et.e_commerces.exception.UserException;
import com.thqu1et.e_commerces.model.Cart;
import com.thqu1et.e_commerces.model.CartItem;
import com.thqu1et.e_commerces.model.Product;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId , Long id , CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart , Product product , String size , Long userId);

    public void removeCartItem(Long userId , Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
