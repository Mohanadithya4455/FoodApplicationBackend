package com.example.onlinefood.controller;


import com.example.onlinefood.model.Category;
import com.example.onlinefood.model.RestaurantDto;
import com.example.onlinefood.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @GetMapping("getall/{id}")
    public ResponseEntity<List<Category>> findAll(@PathVariable("id") Long id){
        List<Category> list=categoryService.findAll(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("find/{id}")
    public ResponseEntity<Category> findRestaurant(@PathVariable("id") Long id) throws Exception {
        Category category=categoryService.findById(id);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }
}
