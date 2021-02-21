package application.database.controllers;

import application.database.model.Recipe;
import application.database.services.cooking.CookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public class CookingController {

    @Autowired
    CookingService cookingService;

    @GetMapping("/cookable")
    List<Recipe> allCookableRecipes(){
        return  cookingService.allCookableRecipes();
    }

    @RequestMapping("/cookable/{id}")
    Recipe cookRecipe(@PathVariable int id){
        return cookRecipe(id);
    }
}
