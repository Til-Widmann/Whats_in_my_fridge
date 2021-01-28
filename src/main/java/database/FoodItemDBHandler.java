package main.java.database;

import com.j256.ormlite.dao.Dao;
import main.java.database.dataObjects.FoodItem;
import main.java.database.dataObjects.History;

import java.time.LocalDate;

public class FoodItemDBHandler extends DBHandler<FoodItem>{
    HistoryDBHandler historyDBHandler;

    FoodItemDBHandler(Dao<FoodItem, Integer> foodItemDao,
                      Dao<History, Integer> historyDao) {
        super(foodItemDao);
        historyDBHandler = new HistoryDBHandler(historyDao);
    }



    public void addFoodItemAndHistory(FoodItem foodItem) {
        this.add(foodItem);
        historyDBHandler.add(foodItem.createHistory());
    }

    public void updateAmount(FoodItem foodItem, int amount){
        foodItem.setAmount(amount);
        this.update(foodItem);
    }

    @Override
    void remove(FoodItem Object) {
        super.remove(Object);
        historyDBHandler.removeAll(Object);
    }

}
