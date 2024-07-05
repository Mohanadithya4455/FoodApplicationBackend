package com.example.onlinefood.controller;

import com.example.onlinefood.model.IngredientCategory;
import com.example.onlinefood.model.IngredientsItem;
import com.example.onlinefood.request.IngredientCategoryRequest;
import com.example.onlinefood.request.IngredientRequest;
import com.example.onlinefood.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {
    @Autowired
    private IngredientsService ingredientsService;
    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req
            ) throws Exception {
        IngredientCategory item=ingredientsService.createIngredientCategory(req.getName(),req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }
    @PostMapping("/items")
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredientRequest req
    ) throws Exception {
        IngredientsItem item=ingredientsService.createIngredientItem(req.getRestaurantId(),req.getName(),req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItem> updateIngredientItem(
            @PathVariable Long id
    ) throws Exception {
        IngredientsItem item=ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientsItem> items=ingredientsService.findByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/restaurant{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategories(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientCategory> categories=ingredientsService.findCategoryItemsByRestaurantId(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
