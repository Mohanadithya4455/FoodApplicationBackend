package com.example.onlinefood.controller;

import com.example.onlinefood.model.Restaurant;
import com.example.onlinefood.model.User;
import com.example.onlinefood.request.CreateRest;
import com.example.onlinefood.response.AdminRestaurantResponse;
import com.example.onlinefood.service.RestaurantService;
import com.example.onlinefood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
    @Autowired
    UserService userService;
    @Autowired
    RestaurantService restaurantService;
    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRest createRest, @RequestHeader("Authorization") String jwt){
        User user=userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(restaurantService.createReastaurant(createRest,user),HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable("id") Long id,@RequestBody CreateRest createRest,@RequestHeader("Authorization")
                                       String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(restaurantService.updateRestaurant(id,user,createRest),HttpStatus.OK);
    }
     @PutMapping("/update/status/{id}")
    public ResponseEntity<Restaurant> updateStatus(@PathVariable("id") Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.updateRestaurantStatus(id,user);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
 @DeleteMapping("/delete/{id}")
     public ResponseEntity<String> deleteRestaurant(@PathVariable("id") Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        restaurantService.deleteRestaurant(id,user);
        return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
   }

}
