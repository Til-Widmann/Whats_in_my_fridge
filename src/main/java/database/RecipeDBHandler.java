package main.java.database;

import com.j256.ormlite.dao.Dao;
import main.java.database.dataObjects.Recipe;

public class RecipeDBHandler extends DBHandler<Recipe> {

    @Override
    Dao getDao() {
        return DBConnect.getRecipeDao();
    }
}
