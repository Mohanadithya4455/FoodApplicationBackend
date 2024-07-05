package com.example.onlinefood.controller;


import com.example.onlinefood.model.Restaurant;
import com.example.onlinefood.model.RestaurantDto;
import com.example.onlinefood.model.User;
import com.example.onlinefood.service.RestaurantService;
import com.example.onlinefood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserService userService;
    @GetMapping("/getall")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(@RequestHeader("Authorization") String jwt){
        User user=userService.findUserByJwtToken(jwt);
        List<Restaurant> list=restaurantService.getAllRestaurants(user);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@PathVariable("id") Long id) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant,HttpStatus.OK);
    }
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Restaurant> findRestaurantByOwnerId(@PathVariable("id") Long id) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantByUserId(id);
        return new ResponseEntity<>(restaurant,HttpStatus.OK);

    }

    @PutMapping("/add/favourites/{id}")
    public ResponseEntity<RestaurantDto> addFavourites(@PathVariable("id") Long id,@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        RestaurantDto dto=restaurantService.addFavourites(id,user);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> search(@RequestParam String str,@RequestHeader("Authorization") String jwt){
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurants=restaurantService.searchRestaurants(str);
        return new ResponseEntity<>(restaurants,HttpStatus.OK);
    }


}
