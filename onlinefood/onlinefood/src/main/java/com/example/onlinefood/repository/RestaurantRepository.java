package com.example.onlinefood.repository;

import com.example.onlinefood.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select r from Restaurant r where lower(r.name) like lower(concat('%',:str,'%')) or lower(r.cuisineType) like lower(concat('%',:str,'%'))")
    List<Restaurant> findBySearchQuery(@Param("str") String str);
    Restaurant findByOwnerId(Long id);
}
