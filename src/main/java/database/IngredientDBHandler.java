package main.java.database;

import com.j256.ormlite.dao.Dao;
import main.java.database.dataObjects.FoodItem;
import main.java.database.dataObjects.History;
import main.java.database.dataObjects.Ingredient;
import main.java.database.dataObjects.Recipe;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class IngredientDBHandler extends DBHandler<Ingredient>{

    IngredientDBHandler() {
        super(DBConnect.getIngredientDao());
    }
    public ArrayList<Ingredient> getIngredients(Recipe recipe){
        return super.getAll().stream()
                .filter(a -> a.getRecipe().equals(recipe))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    void removeAll(Recipe recipe){
        getIngredients(recipe).forEach(super::remove);
    }
}
