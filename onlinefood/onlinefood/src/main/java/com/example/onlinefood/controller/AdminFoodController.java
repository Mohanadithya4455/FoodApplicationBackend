package com.example.onlinefood.controller;

import com.example.onlinefood.model.Food;
import com.example.onlinefood.model.Restaurant;
import com.example.onlinefood.model.User;
import com.example.onlinefood.repository.RestaurantRepository;
import com.example.onlinefood.request.CreateFoodRequest;
import com.example.onlinefood.response.AuthResponse;
import com.example.onlinefood.service.FoodService;
import com.example.onlinefood.service.RestaurantService;
import com.example.onlinefood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/foods")
public class AdminFoodController {
    @Autowired
    UserService userService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    FoodService foodService;

    @PostMapping("/save")
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest createFoodRequest,@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(createFoodRequest.getRestaurantId());
        Food food=foodService.createFood(createFoodRequest,createFoodRequest.getCategory(),restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id,@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        foodService.deleteById(id);
        String str="Successfully deleted";
        return new ResponseEntity<>(str,HttpStatus.OK);
    }
    @PutMapping("/availability/{id}")
    public ResponseEntity<Food> updateAvailabilityStatus(@PathVariable("id") Long id,@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Food food=foodService.updateAvailabilityStatus(id);
        return new ResponseEntity<>(food,HttpStatus.OK);
    }
}
