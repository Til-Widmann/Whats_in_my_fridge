package application.database.controllers;

import application.database.model.Recipe;
import application.database.services.recipe.RecipeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class RecipeController {

    @Autowired
    RecipeServiceImpl recipeService;

    @PostMapping("/recipe")
    Recipe addRecipe(@RequestBody Recipe recipe){
        return recipeService.addRecipe(recipe);
    }

    @GetMapping("/recipe")
    List<Recipe> all(){
        return recipeService.getAll();
    }
}
