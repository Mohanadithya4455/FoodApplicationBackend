package com.example.onlinefood.service;

import com.example.onlinefood.model.IngredientCategory;
import com.example.onlinefood.model.IngredientsItem;
import com.example.onlinefood.model.Restaurant;
import com.example.onlinefood.model.RestaurantDto;
import com.example.onlinefood.repository.IngredientCategoryRepository;
import com.example.onlinefood.repository.IngredientsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class IngredientsServiceImpl implements IngredientsService{
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    IngredientsItemRepository ingredientsItemRepository;
    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        IngredientCategory ingredientCategory=new IngredientCategory();
        ingredientCategory.setName(name);
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        ingredientCategory.setRestaurant(restaurant);
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public List<IngredientCategory> findCategoryItemsByRestaurantId(Long restaurantId) {
             List<IngredientCategory> list=ingredientCategoryRepository.findByRestaurantId(restaurantId);
             return list;
    }

    @Override
    public IngredientCategory findIngredientById(Long id) throws Exception {
        Optional<IngredientCategory> ingredientsItem=ingredientCategoryRepository.findById(id);
        if(ingredientsItem.isPresent()){
            return ingredientsItem.get();
        }
        throw new Exception("Ingredient Category not found");
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String name, Long categoryId) throws Exception {
        IngredientCategory category=findIngredientById(categoryId);
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        IngredientsItem item=new IngredientsItem();
        item.setName(name);
        item.setCategory(category);
        item.setRestaurant(restaurant);
        ingredientsItemRepository.save(item);
        return item;
    }

    @Override
    public List<IngredientsItem> findByRestaurantId(Long restaurantId) {
        List<IngredientsItem> list=ingredientsItemRepository.findByRestaurantId(restaurantId);
        return list;
    }

    @Override
    public IngredientsItem findIngredientsItemsById(Long id) throws Exception {
        Optional<IngredientsItem> item=ingredientsItemRepository.findById(id);
        if(item.isPresent())
        {
            return item.get();
        }
        throw new Exception("Ingredient Item not found");
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        IngredientsItem item=findIngredientsItemsById(id);
        if(item.isInStoke()){
            item.setInStoke(false);
        }
        else {
            item.setInStoke(true);
        }
        ingredientsItemRepository.save(item);
        return item;
    }
}
