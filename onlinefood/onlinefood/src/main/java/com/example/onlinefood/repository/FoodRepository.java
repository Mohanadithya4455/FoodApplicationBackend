package com.example.onlinefood.repository;

import com.example.onlinefood.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {

    public List<Food> findByRestaurantId(Long restaurantId);
    @Query("select f from Food f where f.name like %:keyword% or f.foodCategory.name like %:keyword%")
    public List<Food> searchFood(@Param("keyword") String keyword);

}
