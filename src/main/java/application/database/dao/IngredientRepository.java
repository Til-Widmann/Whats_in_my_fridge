package application.database.dao;

import application.database.model.Ingredient;
import application.database.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ingredientRepository")
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {


    List<Ingredient> findByRecipe(Recipe recipe);
}
