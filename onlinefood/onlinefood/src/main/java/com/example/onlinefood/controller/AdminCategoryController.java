package com.example.onlinefood.controller;


import com.example.onlinefood.model.Category;
import com.example.onlinefood.model.User;
import com.example.onlinefood.service.CategoryService;
import com.example.onlinefood.service.RestaurantService;
import com.example.onlinefood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category")
public class AdminCategoryController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Category category1=categoryService.createCategory(category.getName(), user.getId());
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

}
