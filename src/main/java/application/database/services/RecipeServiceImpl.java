package application.database.services;

import application.database.dao.IngredientRepository;
import application.database.dao.RecipeRepository;
import application.database.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author Til-W
 * @version 1.0
 *
 */
@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Recipe addRecipe(Recipe recipe){
        recipeRepository.save(recipe);
        return recipe;
    }

    @Override
    public  List<Recipe> getAll(){
        return recipeRepository.findAll();
    }
}