package application.database.services.cooking;

import application.database.model.Recipe;

import java.util.List;


public interface CookingService {
    List<Recipe> allCookableRecipes();

    void cookRecipe(Recipe recipe);
}
