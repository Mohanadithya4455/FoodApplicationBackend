package com.example.onlinefood.service;

import com.example.onlinefood.model.Category;
import com.example.onlinefood.model.User;

import java.util.List;

public interface CategoryService {
    public Category createCategory(String str, Long userId) throws Exception;

    public List<Category> findAll(Long restaurantId);

    public Category findById(Long id) throws Exception;

}
