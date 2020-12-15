package main.java.database;

import main.java.database.dataObjects.Recipe;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class RecipeDBHandlerTest {

    private static final String TEST_REZEPT = "TestRezept";
    private static int rezeptId;


    @BeforeEach
    void setUp() {
        Recipe recipe = new Recipe(TEST_REZEPT);
        rezeptId = RecipeDBHandler.insertRecipe(recipe);
    }

    @AfterEach
    void tearDown() {
        if (recipeIsInDB()) {
            RecipeDBHandler.removeRecipe(TEST_REZEPT);
        }
    }

    @Test
    void insertRecipe() {
        Recipe recipe = RecipeDBHandler.getRecipe(TEST_REZEPT);
        assertEquals(TEST_REZEPT, recipe.getName());
        assertEquals(rezeptId, recipe.getId());
    }
    @Test
    void getRecipe() {
        Recipe recipe = RecipeDBHandler.getRecipe(TEST_REZEPT);
        assertNotNull(recipe);
    }
    @Test
    void getAllRecipes() {
        assertNotEquals(0, RecipeDBHandler.getAllRecipes());
    }

    @Test
    void removeRecipe() {
        RecipeDBHandler.removeRecipe(TEST_REZEPT);
        assertFalse(recipeIsInDB());
    }

    private boolean recipeIsInDB() {
        return RecipeDBHandler.getAllRecipes().stream()
                .anyMatch(a -> a.getName().equals(TEST_REZEPT));
    }
}