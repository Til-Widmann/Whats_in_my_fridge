package main.java.database;

import main.java.database.dataObjects.Ingredient;
import main.java.database.dataObjects.Recipe;

import java.util.*;

import static main.java.database.IngredientDBHandler.*;
import static main.java.database.RecipeDBHandler.*;

/**
 * @author Til-W
 * @version 1.0
 *
 */
public class CookingController {

    /**
     * Adds Recipe to the database
     * @param name name of recipe
     * @param ingredients  a map of Ingredients that corrospond to this Recipe
     */
    public static void addRecipe(String name, Map<String, Integer> ingredients) {
        int recipeId = insertRecipe(new Recipe(name));

        ingredients.forEach((key, value) -> insertIngredient(new Ingredient(
                key,
                value,
                recipeId
        )));
    }

    /**
     * Get all Recipes which are cookable with stored FoodItems
     * @return List of all cookable Recipes
     */
    public static List<Recipe> cookableRecipes() {
        List<Recipe> recipes = getAllRecipes();
        List<Recipe> cookableRecipes = new LinkedList<>();

        for (Recipe recipe: recipes) {
            if (isCookable(recipe))
                cookableRecipes.add(recipe);
        }
        return cookableRecipes;
    }

    private static boolean isCookable(Recipe recipe) {
        List<Ingredient> ingredients = getAllIngredientsOf(recipe.getRecipeId());
        for (Ingredient ingredient: ingredients ) {
            if (!RefrigeratorController.amountAvailable(ingredient.getName(), ingredient.getAmount())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cooks given Recipe
     * @param name Recipe name to be cooked
     * @return returns if Recipe has been cooked correctly
     */
    public static boolean cookThisRecipe(String name) {
        Recipe recipe = getRecipe(name);
        if (recipe == null)
            return false;

        List<Ingredient> ingredients = getAllIngredientsOf(recipe.getRecipeId());
        for (Ingredient ingredient : ingredients) {
            if (!RefrigeratorController.removeAmountOfFoodItemIfAvailable(ingredient.getName(), ingredient.getAmount()))
                return false;
        }
        return true;
    }
}