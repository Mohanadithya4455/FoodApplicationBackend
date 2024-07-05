package com.example.onlinefood.service;

import com.example.onlinefood.model.Cart;
import com.example.onlinefood.model.CartItem;
import com.example.onlinefood.model.Food;
import com.example.onlinefood.model.User;
import com.example.onlinefood.repository.CartItemRepository;
import com.example.onlinefood.repository.CartRepository;
import com.example.onlinefood.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CartServiceImpl implements CartService{
    @Autowired
    UserService userService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    FoodService foodService;
    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Food food=foodService.findFoodById(req.getFoodId());
        Cart cart=cartRepository.findByCustomerId(user.getId());
        for(CartItem item:cart.getItem()){
            if(item.getFood().equals(food)){
               int newQuantity = item.getQuantity()+req.getQuantity();
                return updateCartItemQuantity(item.getId(),newQuantity);
            }
        }

        CartItem cartItem=new CartItem();
        cartItem.setFood(food);
        cartItem.setCart(cart);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setIngredients(req.getIngredients());
        cartItem.setTotalPrice(req.getQuantity()*food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        cart.getItem().add(savedCartItem);
        cartRepository.save(cart);
        return savedCartItem;

    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> item=cartItemRepository.findById(cartItemId);
        if(item.isEmpty()){
            throw new Exception("Cart item not found");
        }
        CartItem item1=item.get();
        item1.setQuantity(quantity);
        item1.setTotalPrice(item1.getFood().getPrice()*quantity);
        return cartItemRepository.save(item1);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        //Food food=foodService.findFoodById(req.getFoodId());
        Cart cart=cartRepository.findByCustomerId(user.getId());
        Optional<CartItem> item=cartItemRepository.findById(cartItemId);
        if(item.isEmpty()){
            throw new Exception("Cart item not found");
        }
        CartItem cartItem=item.get();
        cart.getItem().remove(cartItem);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public Long calculateCartTotals(Cart cart) {
        Long total=0L;
        for(CartItem item:cart.getItem()){
            total+=item.getFood().getPrice()*item.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> cart=cartRepository.findById(id);
        if(cart.isEmpty()){
            throw new Exception("Cart not found");
        }
        return cart.get();
    }

    @Override
    public Cart findCartByUserId(Long id) {
       // User user=userService.findUserByJwtToken(jwt);
        Cart cart=cartRepository.findByCustomerId(id);
        cart.setTotal(calculateCartTotals(cart));
        return cartRepository.save(cart);
    }

    @Override
    public Cart clearCart(Long id) {

        Cart cart=cartRepository.findByCustomerId(id);
        cart.getItem().clear();
        return cartRepository.save(cart);

    }
}
