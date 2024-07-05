package com.example.onlinefood.controller;

import com.example.onlinefood.model.Cart;
import com.example.onlinefood.model.CartItem;
import com.example.onlinefood.model.User;
import com.example.onlinefood.request.AddCartItemRequest;
import com.example.onlinefood.request.updateCartItemRequest;
import com.example.onlinefood.service.CartService;
import com.example.onlinefood.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    UserService userService;
    @PostMapping("/save/item")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem=cartService.addItemToCart(req,jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }
    @PutMapping("/item/update")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody updateCartItemRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem item=cartService.updateCartItemQuantity(req.getCartItemId(),req.getQuantity());
        return new ResponseEntity<>(item,HttpStatus.OK);
    }
    @DeleteMapping("/items/remove/{id}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable("id") Long id,@RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart=cartService.removeItemFromCart(id,jwt);
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }
    @DeleteMapping("/items/clear")
    public ResponseEntity<Cart> clearItems(@RequestHeader("Authorization") String jwt){
        User user=userService.findUserByJwtToken(jwt);
        Cart cart=cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> getCart(@RequestHeader("Authorization") String jwt){
        User user=userService.findUserByJwtToken(jwt);
        Cart cart=cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }
}
