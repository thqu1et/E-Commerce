package com.thqu1et.e_commerces.controller;

import com.thqu1et.e_commerces.exception.CartItemException;
import com.thqu1et.e_commerces.exception.UserException;
import com.thqu1et.e_commerces.model.CartItem;
import com.thqu1et.e_commerces.model.User;
import com.thqu1et.e_commerces.service.CartItemService;
import com.thqu1et.e_commerces.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import com.thqu1et.e_commerces.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;

    @DeleteMapping("/{cartItemId}")
    @Operation(description = "Remove Cart Item from cart")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Delete Item")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
                                                      @RequestHeader("Authorization")String jwt) throws UserException , CartItemException{
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("delete item to cart");
        res.setStatus(true);

        return new ResponseEntity<>(res , HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    @Operation(description = "Update Cart Item from cart")
    public ResponseEntity<CartItem> updateCartltem (
            @RequestBody CartItem cartItem, @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws UserException, CartItemException{
        User user=userService.findUserProfileByJwt(jwt);
        CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        return new ResponseEntity<>(updatedCartItem,HttpStatus.OK);
    }
}
