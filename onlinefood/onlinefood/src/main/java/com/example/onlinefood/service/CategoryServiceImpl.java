package com.example.onlinefood.service;

import com.example.onlinefood.model.Category;
import com.example.onlinefood.model.Restaurant;
import com.example.onlinefood.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RestaurantService restaurantService;
    @Override
    public Category createCategory(String str, Long userId) throws Exception {
        Category category=new Category();
        category.setName(str);
        Restaurant restaurant=restaurantService.findRestaurantByUserId(userId);
        category.setRestaurant(restaurant);
        return category;
    }

    @Override
    public List<Category> findAll(Long restaurantId) {
        List<Category> categories=categoryRepository.findByRestaurantId(restaurantId);
        return categories;
    }

    @Override
    public Category findById(Long id) throws Exception {
        Optional<Category> category=categoryRepository.findById(id);
        if(category.isPresent()){
            return category.get();
        }
        throw new Exception("Category not founded with this "+id);
    }

}
