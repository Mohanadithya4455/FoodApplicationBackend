package com.example.onlinefood.request;

import com.example.onlinefood.model.Category;
import com.example.onlinefood.model.IngredientsItem;
import com.example.onlinefood.model.Restaurant;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private Long restaurantId;
    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images;
    private boolean isSeasonal;
    private boolean isVegetarian;
    private List<IngredientsItem> ingrediants;
}
