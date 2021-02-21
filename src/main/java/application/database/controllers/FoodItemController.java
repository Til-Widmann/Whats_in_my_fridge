package application.database.controllers;

import application.database.model.FoodItem;
import application.database.model.History;
import application.database.services.foodItem.FoodItemService;
import application.database.services.history.HistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FoodItemController {

    @Autowired
    @Qualifier("persistent")
    FoodItemService foodItemService;

    @Autowired
    HistoryServiceImpl historyService;

    @PostMapping("/foodItems")
    FoodItem addFoodItem(@RequestBody FoodItem foodItem){
        return foodItemService.addFood(foodItem);
    }

    @GetMapping("/foodItems")
    List<FoodItem> all(){
        return foodItemService.getAll();
    }

    @GetMapping("/foodItems/{id}")
    FoodItem get(@PathVariable int id){
        return foodItemService.getById(id);
    }

    @GetMapping("/foodItems/history/{id}")
    List<History> getHistory(@PathVariable int id){
        return historyService.getHistoryFromFoodItem(foodItemService.getById(id));
    }

    @DeleteMapping("/foodItems/{id}")
    void deleteFoodItem(@PathVariable int id){
        foodItemService.delete(id);
    }

}