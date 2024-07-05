package com.example.onlinefood.service;

import com.example.onlinefood.model.Cart;
import com.example.onlinefood.model.CartItem;
import com.example.onlinefood.model.User;
import com.example.onlinefood.request.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception;
    public CartItem updateCartItemQuantity(Long cartItemId,int quantity) throws Exception;
    public Cart removeItemFromCart(Long cartItemId,String jwt) throws Exception;
    public Long calculateCartTotals(Cart cart);
    public Cart findCartById(Long id) throws Exception;
    public Cart findCartByUserId(Long id);
    public Cart clearCart(Long id);

}
