package main.java.database;


import main.java.database.dataObjects.Ingredient;
import main.java.database.dataObjects.Recipe;

import java.util.*;


/**
 * @author Til-W
 * @version 1.0
 *
 */
public class CookingController {
    private static IngredientDBHandler ingredientDBHandler = new IngredientDBHandler();
    private static RecipeDBHandler recipeDBHandler = new RecipeDBHandler();


    private static boolean isCookable(Recipe recipe) {
        ArrayList<Ingredient> ingredients = ingredientDBHandler.getIngredients(recipe);

        for (Ingredient ingredient : ingredients) {
            if (!RefrigeratorController.checkIfAmountAvailable(ingredient.getName(), ingredient.getAmount()))
                return false;
        }
        return true;
    }

    public static ArrayList<Recipe> allCookableRecipes() {
        ArrayList<Recipe> recipes = recipeDBHandler.getAll();
        recipes.removeIf(a -> !isCookable(a));
        return recipes;
    }

    public static void cookRecipe(Recipe recipe) {
        if (!isCookable(recipe)) throw new IllegalArgumentException();
        ArrayList<Ingredient> ingredients = ingredientDBHandler.getIngredients(recipe);

        ingredients.forEach(
                a -> RefrigeratorController.removeAmountFromAllWith(a.getName(), a.getAmount()));
    }
}