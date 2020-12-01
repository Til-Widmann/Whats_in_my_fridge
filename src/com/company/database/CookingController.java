package com.company.database;

import com.company.database.dataObjects.Ingredient;
import com.company.database.dataObjects.Recipe;

import java.util.*;
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
    public void addRecipe(String name, Map<String, Integer> ingredients) {
        SQLiteDBHandler db = new SQLiteDBHandler();

        int recipeId = db.insertRecipe(new Recipe(-1,  name));

        ingredients.entrySet().forEach(a ->
                db.insertIngredient(new Ingredient(
                        -1,
                        a.getKey(),
                        a.getValue(),
                        recipeId
                ))
        );
    }

    /**
     * Get all Recipes which are cookable witrh stored FoodItems
     * @return List of all cookable Recipes
     */
    public List<Recipe> cookableRecipes() {
        SQLiteDBHandler db = new SQLiteDBHandler();
        List<Recipe> recipes = db.getAllRecipes();
        RefrigeratorController refrigeratorController = new RefrigeratorController();
        List<Recipe> cookableRecipes = new LinkedList<>();

        for (Recipe recipe: recipes) {
            List<Ingredient> ingredients = db.getAllIngredientsOf(recipe.getRecipeId());
            boolean cookable = true;
            for (Ingredient ingredient: ingredients ) {
                if (!refrigeratorController.amountAvailable(ingredient.getName(), ingredient.getAmount())) {
                    cookable = false;
                    break;
                }
            }
            if (cookable)
                cookableRecipes.add(recipe);
        }

        return cookableRecipes;
    }

    /**
     * Cooks given Recipe
     * @param name Recipe name to be cooked
     * @return returns if Recipe has been cooked correctly
     */
    public boolean cookThisRecipe(String name) {
        SQLiteDBHandler db = new SQLiteDBHandler();
        Recipe recipe = db.getRecipe(name);
        if (recipe == null)
            return false;
        RefrigeratorController refrigeratorController = new RefrigeratorController();

        List<Ingredient> ingredients = db.getAllIngredientsOf(recipe.getRecipeId());
        for (Ingredient ingredient : ingredients) {
            if (!refrigeratorController.removeAmountOfFoodItem(ingredient.getName(), ingredient.getAmount()))
                return false;
        }
        return true;
    }
}