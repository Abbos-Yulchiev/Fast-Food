package com.demo.demo.repository.food;

import com.demo.demo.entity.Food;
import com.demo.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>, JpaSpecificationExecutor<Order> {

    Optional<Food> findFoodByName(String name);

    Optional<Food> findFoodByNameAndIdNot(String name, Long id);

    long countFoodByFoodStatus(String status);

    @Query(value = "Select * from foods where foods.food_status = ?1", nativeQuery = true)
    List<Food> findFoodsByFoodStatus(String status);
}
