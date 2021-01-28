package main.java.database;

import com.j256.ormlite.dao.Dao;
import main.java.database.dataObjects.FoodItem;
import main.java.database.dataObjects.History;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HistoryDBHandler extends DBHandler<History>{

    HistoryDBHandler(Dao<History, Integer> historyDao) {
        super(historyDao);
    }


    public ArrayList<History> getHistories(FoodItem foodItem){
        return super.getAll().stream()
                .filter(a -> a.getFoodItem().equals(foodItem))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    void removeAll(FoodItem foodItem){
        getHistories(foodItem).forEach(super::remove);
    }
}
