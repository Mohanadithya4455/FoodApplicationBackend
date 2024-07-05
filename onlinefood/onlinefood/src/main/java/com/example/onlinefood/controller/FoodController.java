package com.example.onlinefood.controller;

import com.example.onlinefood.model.Food;
import com.example.onlinefood.model.User;
import com.example.onlinefood.service.FoodService;
import com.example.onlinefood.service.RestaurantService;
import com.example.onlinefood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    @Autowired
    FoodService foodService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    UserService userService;
    @GetMapping("/find/{id}")
    public ResponseEntity<Food> findById(@PathVariable("id") Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        Food food=foodService.findFoodById(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
    @GetMapping("getall/{id}")
    public ResponseEntity<List<Food>> getAllFoodsById(@PathVariable("id") Long id,@RequestHeader("Authorization") String jwt){
        User user=userService.findUserByJwtToken(jwt);
        List<Food> list=foodService.findFoodByRestaurantId(id);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Food>> search(@RequestParam String str,@RequestHeader("Authorization") String jwt){
        User user=userService.findUserByJwtToken(jwt);
        List<Food> list=foodService.searchFood(str);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/restaurants/restaurantId")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam boolean vegetarian,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonveg,
            @RequestParam(required = false) String food_category,
            @PathVariable("restaurantId") Long restaurantId,
            @RequestHeader("Authorization") String jwt){
        User user=userService.findUserByJwtToken(jwt);
        List<Food> foods=foodService.getRestaurantsFood(restaurantId,seasonal,vegetarian,nonveg,jwt);
        return new ResponseEntity<>(foods,HttpStatus.OK);
    }


}
