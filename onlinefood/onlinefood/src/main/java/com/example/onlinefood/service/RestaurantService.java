package com.example.onlinefood.service;

import com.example.onlinefood.model.Restaurant;
import com.example.onlinefood.model.RestaurantDto;
import com.example.onlinefood.model.User;
import com.example.onlinefood.request.CreateRest;

import java.util.List;

public interface RestaurantService {

    Restaurant createReastaurant(CreateRest createRest, User user);

    List<Restaurant> getAllRestaurants(User user);

    Restaurant findRestaurantById(Long id) throws Exception;

    Restaurant findRestaurantByUserId(Long userId) throws Exception;

    void deleteRestaurant(Long id,User user) throws Exception;

    Restaurant updateRestaurant(Long id,User user,CreateRest rest) throws Exception;

    Restaurant updateRestaurantStatus(Long id,User user) throws Exception;

    RestaurantDto addFavourites(Long id,User user) throws Exception;

    List<Restaurant> searchRestaurants(String str);
}
