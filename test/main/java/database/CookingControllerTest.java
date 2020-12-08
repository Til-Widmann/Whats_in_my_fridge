package main.java.database;

import main.java.database.dataObjects.FoodItem;
import main.java.database.dataObjects.Ingredient;
import main.java.database.dataObjects.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CookingControllerTest {
    private static final int AMOUNT = 500;
    private static final String RECIPE_NAME = "Test Recipe";
    private static final String TEST_FOOD_ITEM_NAME = "Test FoodItem";

    @BeforeEach
    void setUp() {
        Map<String, Integer> ingredients = Map.of(TEST_FOOD_ITEM_NAME, AMOUNT);
        CookingController.addRecipe(RECIPE_NAME, ingredients);
        insertUsedFoodItems();
    }

    @AfterEach
    void tearDown() {
        int recipeId = RecipeDBHandler.getRecipe(RECIPE_NAME).getRecipeId();
        RecipeDBHandler.removeRecipe(RECIPE_NAME);
        IngredientDBHandler.removeIngredients(recipeId);
        removeUsedFoodItems();
    }

    @Test
    void addRecipe() {
        Recipe recipe = RecipeDBHandler.getRecipe(RECIPE_NAME);
        LinkedList<Ingredient> ingredient = IngredientDBHandler.getAllIngredientsOf(recipe.getRecipeId());
        assertEquals(RECIPE_NAME, recipe.getName());
        assertEquals(1, ingredient.size());
        assertEquals(TEST_FOOD_ITEM_NAME, ingredient.getFirst().getName());
        assertEquals(AMOUNT, ingredient.getFirst().getAmount());
    }

    @Test
    void cookableRecipes() {
        List<Recipe> recipes = CookingController.cookableRecipes();
        assertTrue(recipes.stream().anyMatch(a -> a.getName().equals(RECIPE_NAME)));
    }

    @Test
    void cookThisRecipe() {
        assertTrue(CookingController.cookThisRecipe(RECIPE_NAME));
        List<FoodItem> foodItems = FoodItemDBHandler.getAllUsedUpFoodItems();
        boolean remainingAmount = foodItems.stream().anyMatch(a -> a.getName().equals(TEST_FOOD_ITEM_NAME));

        assertTrue(remainingAmount);
    }




    private void insertUsedFoodItems() {
        FoodItem foodItem = new FoodItem(
                TEST_FOOD_ITEM_NAME,
                500,
                LocalDate.ofEpochDay(20000));
        FoodItemDBHandler.insertFoodItem(foodItem);
    }
    private void removeUsedFoodItems(){
        List<FoodItem> foodItemId = FoodItemDBHandler.getAllUsedUpFoodItems().stream()
                .filter(a -> a.getName().equals(TEST_FOOD_ITEM_NAME))
                .collect(Collectors.toList());
        FoodItemDBHandler.removeFoodItem(TEST_FOOD_ITEM_NAME);
        foodItemId.forEach(a -> HistoryDBHandler.removeHistory(a.getFoodItemId()));
    }
}