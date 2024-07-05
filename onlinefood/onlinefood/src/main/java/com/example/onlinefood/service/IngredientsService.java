package com.example.onlinefood.service;

import com.example.onlinefood.model.IngredientCategory;
import com.example.onlinefood.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name,Long restaurantId) throws Exception;

    public List<IngredientCategory> findCategoryItemsByRestaurantId(Long restaurantId);

    public IngredientCategory findIngredientById(Long id) throws Exception;

    public IngredientsItem createIngredientItem(Long restaurantId,String name,Long categoryId) throws Exception;

    public List<IngredientsItem> findByRestaurantId(Long restaurantId);

    public IngredientsItem findIngredientsItemsById(Long id) throws Exception;

    public IngredientsItem updateStock(Long id) throws Exception;
}
