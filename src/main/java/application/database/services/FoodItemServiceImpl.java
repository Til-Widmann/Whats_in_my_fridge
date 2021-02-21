package application.database.services;

import application.database.dao.FoodItemRepository;
import application.database.model.FoodItem;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Til-W
 * @version 1.0
 *
 */
@Service("persistent")
public class FoodItemServiceImpl implements FoodItemService{

    @Setter
    @Autowired
    private FoodItemRepository foodItemRepository;

    @Setter
    @Autowired
    private HistoryService historyService;

    @Override
    public FoodItem addFood(FoodItem foodItem){
        FoodItem foodItemReturn = foodItemRepository.save(foodItem);
        historyService.addHistoryTo(foodItem);
        return foodItemReturn;
    }

    @Override
    public List<FoodItem> getAll(){
        return foodItemRepository.findAll();
    }

    @Override
    public FoodItem getById(int id){
        return foodItemRepository.getOne(id);
    }

    @Override
    public void delete(int id){
        historyService.deleteAllOf(foodItemRepository.findById(id));
        deleteWithoutHistory(id);
    }

    @Override
    public void updateFoodItem(FoodItem foodItem){
        foodItemRepository.save(foodItem);
        historyService.addHistoryTo(foodItem);
    }

    public void deleteWithoutHistory(int id){
        foodItemRepository.delete(getById(id));
    }
}