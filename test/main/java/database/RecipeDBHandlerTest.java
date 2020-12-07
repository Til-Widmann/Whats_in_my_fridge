package main.java.database;

import main.java.database.dataObjects.Recipe;
import org.junit.jupiter.api.*;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class RecipeDBHandlerTest {

    private String testRezept = "TestRezept";
    private int rezeptId;


    @BeforeEach
    void setUp() {
        Recipe recipe = new Recipe(testRezept);
        rezeptId = RecipeDBHandler.insertRecipe(recipe);
    }

    @AfterEach
    void tearDown() {
        if (recipeIsInDB()) {
            RecipeDBHandler.removeRecipe(testRezept);
        }
    }

    @Test
    void insertRecipe() {
        Recipe recipe = RecipeDBHandler.getRecipe(testRezept);
        assertEquals(testRezept, recipe.getName());
        assertEquals(rezeptId, recipe.getRecipeId());
    }
    @Test
    void getRecipe() {
        Recipe recipe = RecipeDBHandler.getRecipe(testRezept);
        assertNotNull(recipe);
    }
    @Test
    void getAllRecipes() {
        assertNotEquals(0, RecipeDBHandler.getAllRecipes());
    }

    @Test
    void removeRecipe() {
        RecipeDBHandler.removeRecipe(testRezept);
        assertFalse(recipeIsInDB());
    }

    private boolean recipeIsInDB() {
        return RecipeDBHandler.getAllRecipes().stream()
                .anyMatch(a -> a.getName().equals(testRezept));
    }
}