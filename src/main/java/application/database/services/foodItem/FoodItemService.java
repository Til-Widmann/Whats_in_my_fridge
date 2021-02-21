package application.database.services.foodItem;

import application.database.model.FoodItem;

import java.util.List;

public interface FoodItemService {

    FoodItem addFood(FoodItem foodItem);

    List<FoodItem> getAll();

    FoodItem getById(int id);

    void updateFoodItem(FoodItem foodItem);

    void delete(int id);

}