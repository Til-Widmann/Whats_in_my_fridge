package application.database.services.recipe;

import application.database.model.Recipe;

import java.util.List;

public interface RecipeService {

    public Recipe addRecipe(Recipe recipe);

    public  List<Recipe> getAll();
}
