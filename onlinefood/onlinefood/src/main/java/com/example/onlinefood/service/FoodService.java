package com.example.onlinefood.service;

import com.example.onlinefood.model.Category;
import com.example.onlinefood.model.Food;
import com.example.onlinefood.model.Restaurant;
import com.example.onlinefood.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest createFoodRequest, Category category, Restaurant restaurant);
    public Food findFoodById(Long id) throws Exception;
    public List<Food> findFoodByRestaurantId(Long id);
    public List<Food> getRestaurantsFood(Long restaurantId,boolean isSeasonal,boolean isVegetarian,boolean isNonVeg,String foodCategory);

    public void deleteById(Long foodId) throws Exception;

    public List<Food> searchFood(String keyword);

    public Food updateAvailabilityStatus(Long foodId) throws Exception;

}
