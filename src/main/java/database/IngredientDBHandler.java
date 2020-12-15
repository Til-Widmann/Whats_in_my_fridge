package main.java.database;

import com.j256.ormlite.dao.Dao;
import main.java.database.dataObjects.Ingredient;

public class IngredientDBHandler extends DBHandler<Ingredient>{

    @Override
    Dao getDao() {
        return DBConnect.getIngredientDao();
    }
}
