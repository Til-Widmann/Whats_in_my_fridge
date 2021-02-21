package main.java.database;

import main.java.database.dataObjects.Ingredient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientDBHandlerTest {

    @BeforeEach
    void setUp() {
        String ingredientName1 = "Rote Bete";
        String ingredientName2 = "Grüne Bete";
        Ingredient ingredient1 = new Ingredient(ingredientName1,500, -1);
        Ingredient ingredient2 = new Ingredient(ingredientName2, 1000, -1);
        IngredientDBHandler.insert(ingredient1);
        IngredientDBHandler.insert(ingredient2);
    }

    @AfterEach
    void tearDown() {
        IngredientDBHandler.remove(-1);
    }

    @Test
    void insertIngredient() {
        Ingredient ingredient1 = IngredientDBHandler.getAllOf(-1).getFirst();
        assertEquals("Rote Bete", ingredient1.getName());
        assertEquals(500, ingredient1.getAmount());
        assertEquals(-1, ingredient1.getRecipeId());

        Ingredient ingredient2 = IngredientDBHandler.getAllOf(-1).getLast();
        assertEquals("Grüne Bete", ingredient2.getName());
        assertEquals(1000, ingredient2.getAmount());
        assertEquals(-1, ingredient2.getRecipeId());
    }

    @Test
    void getAllIngredientsOf() {
        assertEquals(2, IngredientDBHandler.getAllOf(-1).size());
        assertEquals(0, IngredientDBHandler.getAllOf(-2).size());
    }

    @Test
    void removeIngredients() {
        IngredientDBHandler.remove(-1);
        assertEquals(0, IngredientDBHandler.getAllOf(-1).size());
    }
}