package main.java.database;

import com.j256.ormlite.dao.Dao;
import main.java.database.dataObjects.Ingredient;
import main.java.database.dataObjects.Recipe;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeDBHandler extends DBHandler<Recipe> {

    private static IngredientDBHandler ingredientDBHandler = new IngredientDBHandler();

    RecipeDBHandler() {
        super(DBConnect.getRecipeDao());
    }



    public void addRecipeAndIngredients(Recipe recipe, ArrayList<Ingredient> ingredients) {
        this.add(recipe);
        ingredients.forEach(a -> ingredientDBHandler.add(a));
    }

    @Override
    void remove(Recipe Object) {
        super.remove(Object);
        ingredientDBHandler.removeAll(Object);
    }

}
