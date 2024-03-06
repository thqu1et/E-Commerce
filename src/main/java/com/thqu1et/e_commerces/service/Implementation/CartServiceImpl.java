package com.thqu1et.e_commerces.service.Implementation;

import com.thqu1et.e_commerces.exception.ProductException;
import com.thqu1et.e_commerces.model.Cart;
import com.thqu1et.e_commerces.model.CartItem;
import com.thqu1et.e_commerces.model.Product;
import com.thqu1et.e_commerces.model.User;
import com.thqu1et.e_commerces.repository.CartRepository;
import com.thqu1et.e_commerces.request.addItemRequest;
import com.thqu1et.e_commerces.service.CartItemService;
import com.thqu1et.e_commerces.service.CartService;
import com.thqu1et.e_commerces.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, addItemRequest req) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(req.getProductId());

        CartItem isPresent = cartItemService.isCartItemExist(cart , product , req.getSize(), userId);

        if(isPresent==null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantoty());
            cartItem.setUserId(userId);

            int price = req.getQuantoty()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }

        return "Item add to cart success";
    }

    @Override
    public Cart findUserCart(Long user_id) {
        Cart cart = cartRepository.findByUserId(user_id);

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for (CartItem cartItem : cart.getCartItems()){
            totalPrice = totalPrice+ cartItem.getPrice();
            totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
            totalItem = totalItem + cartItem.getQuantity();
        }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscounte(totalPrice-totalDiscountedPrice);

        return cartRepository.save(cart);
    }
}
