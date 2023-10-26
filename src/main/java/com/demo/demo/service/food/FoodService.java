package com.demo.demo.service.food;

import com.demo.demo.core.exception.DataAlreadyExistException;
import com.demo.demo.core.exception.DataNotFoundException;
import com.demo.demo.core.exception.UnknownDataBaseException;
import com.demo.demo.dto.food.FoodCreateDTO;
import com.demo.demo.dto.food.FoodGetDTO;
import com.demo.demo.dto.food.FoodUpdateDTO;
import com.demo.demo.dto.order.OrderCreateDTO;
import com.demo.demo.entity.Food;
import com.demo.demo.entity.enums.FoodStatus;
import com.demo.demo.repository.food.FoodRepository;
import com.demo.demo.response.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class FoodService {

    private final FoodRepository repository;
    private final ModelMapper mapper;

    public static final int PREPARATION_TIME_UNIT = 5;
    public static final int DISHES_PER_PREPARATION_UNIT = 4;
    public static final double DELIVERY_TIME_PER_KM = 3;

    private Queue<Food> foodQueue = new LinkedList<>();

    public FoodService(FoodRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Data<FoodGetDTO> create(FoodCreateDTO dto) {

        Food food = new Food();
        mapper.map(dto, food);
        if (getUnderPreparationFoodsCount() < 5) {
            food.setFoodStatus(FoodStatus.PREPARING);
        } else {
            food.setFoodStatus(FoodStatus.WAITING);
            foodQueue.add(food);
        }
        return new Data<>(convertToFoodGetDTO(food));
    }

    @Transactional
    public Data<FoodGetDTO> update(FoodUpdateDTO dto) {

        Optional<Food> homeNumber = repository.findFoodByNameAndIdNot(dto.getName(), dto.getId());
        if (homeNumber.isPresent()) throw new DataAlreadyExistException
                ("Food with name: [%s] is already exist".formatted(dto.getName()));

        Food food = repository.findById(dto.getId()).orElseThrow(() ->
                new DataNotFoundException("Food with ID: [" + dto.getId() + "] was not found"));
        mapper.map(dto, food);
        return new Data<>(convertToFoodGetDTO(food));
    }

    public Data<String> delete(Long id) {
        Optional<Food> byId = repository.findById(id);
        byId.ifPresent(repository::delete);
        return new Data<>("Food was successfully deleted");
    }

    public Data<FoodGetDTO> get(Long id) {
        Food food = repository.findById(id).orElseThrow(() ->
                new UnknownDataBaseException("Food with ID: [%s] was not found".formatted(id)));
        return new Data<>(convertToFoodGetDTO(food));
    }

    public Data<List<FoodGetDTO>> getAll() {
        List<Food> foods = repository.findAll();
        List<FoodGetDTO> foodList = new ArrayList<>();
        for (Food food : foods)
            foodList.add(convertToFoodGetDTO(food));

        return new Data<>(foodList, foodList.size());
    }

    public Data<List<FoodGetDTO>> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Food> foods = repository.findAll(pageable);
        List<FoodGetDTO> foodList = new ArrayList<>();
        for (Food food : foods)
            foodList.add(convertToFoodGetDTO(food));
        return new Data<>(foodList, foodList.size());
    }

    private FoodGetDTO convertToFoodGetDTO(Food food) {
        Food saved = repository.save(food);
        FoodGetDTO foodGetDTO = new FoodGetDTO();
        mapper.map(saved, foodGetDTO);
        return foodGetDTO;
    }

    private long getUnderPreparationFoodsCount() {
        return repository.countFoodByFoodStatus(String.valueOf(FoodStatus.PREPARING));
    }

    public int calculateTotalPreparationTime(OrderCreateDTO order) {
        int totalFoods = order.getFoods().size();

        int totalPreparationUnits = (int) Math.ceil((double) totalFoods / DISHES_PER_PREPARATION_UNIT);
        return totalPreparationUnits * PREPARATION_TIME_UNIT;
    }


    @Scheduled(fixedRate = 5 * 60 * 1_000)
    public void processFoodQueue() {
        List<Food> underPreparationFoods = repository.findFoodsByFoodStatus(String.valueOf(FoodStatus.PREPARING));
        for (Food food : underPreparationFoods) {
            if (food.getPreparationTime() <= 0) {
                food.setFoodStatus(FoodStatus.READY);
                repository.save(food);
            } else {
                food.setPreparationTime(food.getPreparationTime() - 10);
                repository.save(food);
            }
        }
        while (!foodQueue.isEmpty() && getUnderPreparationFoodsCount() < 5) {
            Food waitingFood = foodQueue.poll();
            if (waitingFood != null) {
                waitingFood.setFoodStatus(FoodStatus.PREPARING);
                repository.save(waitingFood);
            }
        }
    }
}
