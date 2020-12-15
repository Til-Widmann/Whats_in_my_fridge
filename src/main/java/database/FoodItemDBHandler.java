package main.java.database;

import com.j256.ormlite.dao.Dao;
import main.java.database.dataObjects.FoodItem;

public class FoodItemDBHandler extends DBHandler<FoodItem>{

    @Override
    Dao getDao() {
        return DBConnect.getFoodItemDao();
    }
}
