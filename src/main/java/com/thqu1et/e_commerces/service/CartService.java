package com.thqu1et.e_commerces.service;

import com.thqu1et.e_commerces.exception.ProductException;
import com.thqu1et.e_commerces.model.Cart;
import com.thqu1et.e_commerces.model.User;
import com.thqu1et.e_commerces.request.addItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId , addItemRequest req) throws ProductException;

    public Cart findUserCart(Long user_id);
}
