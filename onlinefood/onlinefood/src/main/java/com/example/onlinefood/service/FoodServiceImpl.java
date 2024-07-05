package com.example.onlinefood.service;

import com.example.onlinefood.model.Category;
import com.example.onlinefood.model.Food;
import com.example.onlinefood.model.Restaurant;
import com.example.onlinefood.repository.FoodRepository;
import com.example.onlinefood.repository.RestaurantRepository;
import com.example.onlinefood.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class FoodServiceImpl implements FoodService{
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Override
    public Food createFood(CreateFoodRequest createFoodRequest, Category category, Restaurant restaurant) {
        Food food=new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(createFoodRequest.getDescription());
        food.setImages(createFoodRequest.getImages());
        food.setName(createFoodRequest.getName());
        food.setPrice(createFoodRequest.getPrice());
        food.setIngredientsItems(createFoodRequest.getIngrediants());
        food.setSeasonal(createFoodRequest.isSeasonal());
        food.setVegetarian(createFoodRequest.isVegetarian());
        Food savedFood=foodRepository.save(food);
        restaurant.getFood().add(savedFood);
        restaurantRepository.save(restaurant);
        return savedFood;
    }

    @Override
    public Food findFoodById(Long id) throws Exception {
        Optional<Food> food=foodRepository.findById(id);
        if(food.isPresent()){
            return food.get();
        }
        throw new Exception("Not found");

    }

    @Override
    public List<Food> findFoodByRestaurantId(Long id) {
        return foodRepository.findByRestaurantId(id);
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isSeasonal, boolean isVegetarian, boolean isNonVeg, String foodCategory) {
        List<Food> foods=foodRepository.findByRestaurantId(restaurantId);
        if(isVegetarian){
            foods=filterByVegetarian(foods,isVegetarian);
        }
        if(isNonVeg){
            foods=filterByNonVeg(foods,isNonVeg);
        }
        if(isSeasonal){
            foods=filterBySeasonal(foods,isSeasonal);
        }
        if(foodCategory!=null&&!foodCategory.equals("")){
            foods=filterByCategory(foods,foodCategory);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food->{
            if(food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food->food.isSeasonal()==isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food->food.isVegetarian()==false).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food->food.isVegetarian()==isVegetarian).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long foodId) throws Exception {
        Food food=findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food=findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        foodRepository.save(food);
        return food;
    }
}
