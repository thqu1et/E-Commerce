package com.thqu1et.e_commerces.controller;


import com.thqu1et.e_commerces.exception.ProductException;
import com.thqu1et.e_commerces.exception.UserException;
import com.thqu1et.e_commerces.model.Cart;
import com.thqu1et.e_commerces.model.User;
import com.thqu1et.e_commerces.request.addItemRequest;
import com.thqu1et.e_commerces.service.CartService;
import com.thqu1et.e_commerces.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.thqu1et.e_commerces.Response.ApiResponse;
@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Management" , description = "find user cart , add item to cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    @Operation(description = "find cart by user id")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Auhorization")String jwt) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<Cart>(cart , HttpStatus.OK);
    }

    @PutMapping("/add")
    @Operation(description = "add item to cart ")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody addItemRequest req ,
                                                     @RequestHeader("Authorization")String jwt) throws UserException , ProductException{
        User user = userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), req);

        ApiResponse res = new ApiResponse();
        res.setMessage("item added to cart");
        res.setStatus(true);

        return new ResponseEntity<>(res , HttpStatus.OK);
    }
}
