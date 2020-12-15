package main.java.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import main.java.database.dataObjects.FoodItem;
import main.java.database.dataObjects.History;
import main.java.database.dataObjects.Ingredient;
import main.java.database.dataObjects.Recipe;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Til-W
 * @version 1.0
 *
 *
 */
 class DBConnect {
    private static Dao<FoodItem, Integer> foodItemDao;
    private static Dao<Ingredient, Integer> ingredientDao;
    private static Dao<Recipe, Integer> recipeDao;
    private static Dao<History, Integer> historyDao;
    public DBConnect() {
        try {
            init();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void init() throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db");
        try {
            createAllDao(connectionSource);
        }finally {
            connectionSource.close();
        }
    }

    private void createAllDao(ConnectionSource connectionSource) throws java.sql.SQLException {
        foodItemDao = DaoManager.createDao(connectionSource, FoodItem.class);
        ingredientDao = DaoManager.createDao(connectionSource, Ingredient.class);
        recipeDao = DaoManager.createDao(connectionSource, Recipe.class);
        historyDao = DaoManager.createDao(connectionSource, History.class);
    }

    public static Dao<FoodItem, Integer> getFoodItemDao() {
        return foodItemDao;
    }

    public static Dao<Ingredient, Integer> getIngredientDao() {
        return ingredientDao;
    }

    public static Dao<Recipe, Integer> getRecipeDao() {
        return recipeDao;
    }

    public static Dao<History, Integer> getHistoryDao() {
        return historyDao;
    }
}
