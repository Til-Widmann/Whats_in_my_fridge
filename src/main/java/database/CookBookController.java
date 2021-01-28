package main.java.database;

import main.java.database.dataObjects.Ingredient;
import main.java.database.dataObjects.Recipe;

import java.util.ArrayList;
import java.util.HashMap;

public class CookBookController {
    private static RecipeDBHandler recipeDBHandler = new RecipeDBHandler();

    public static void addRecipe(String name, HashMap<String, String> ingredients){
        Recipe recipe = new Recipe(name);
        ArrayList<Ingredient> ingredientObjects = getIngredients(ingredients, recipe);

        recipeDBHandler.addRecipeAndIngredients(recipe, ingredientObjects);
    }
    private static ArrayList<Ingredient> getIngredients(HashMap<String, String> ingredients, Recipe recipe) {
        ArrayList<Ingredient> ingredientObjects = new ArrayList<>();
        ingredients.forEach((a,b) -> ingredientObjects.add(
                new Ingredient(a, Integer.parseInt(b) , recipe)));
        return ingredientObjects;
    }
}
