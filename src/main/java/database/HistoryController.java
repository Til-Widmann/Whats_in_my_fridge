package main.java.database;

import main.java.database.dataObjects.FoodItem;
import main.java.database.dataObjects.History;

import java.util.ArrayList;

public class HistoryController {
    private static FoodItemDBHandler foodItemDBHandler = new FoodItemDBHandler();
    private static HistoryDBHandler historyDBHandler= new HistoryDBHandler();

    public   static FoodItem getFoodItemFromId(int id){
        for (FoodItem foodItem: foodItemDBHandler.getAll()) {
            if (foodItem.getId() == id) return foodItem;
        }
        return null;
    }
    public static ArrayList<History> getHistoryFromId(int id) {
        FoodItem foodItem = getFoodItemFromId(id);
        return historyDBHandler.getHistories(foodItem);
    }
}
