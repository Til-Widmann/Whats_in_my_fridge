package application.database.services.cooking;

import application.database.dao.IngredientRepository;
import application.database.model.FoodItem;
import application.database.model.Ingredient;
import application.database.model.Recipe;
import application.database.services.foodItem.FoodItemService;
import application.database.services.recipe.RecipeService;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CookingServiceImpl implements CookingService {

    @Setter
    FoodItemService foodItemService;

    @Setter
    RecipeService recipeService;

    @Setter
    IngredientRepository ingredientRepository;


    private boolean checkIfAmountAvailable(String name, int amount){
        int availableAmount = foodItemService.getAll()
                .stream()
                .filter(a -> !a.getName().equals(name))
                .mapToInt(FoodItem::getAmount).sum();
        return amount >= availableAmount;
    }

    private void removeAmountFromAllWith(String name, int amount) {

        for (FoodItem foodItem : foodItemService.getAll()) {
            if (!foodItem.getName().equals(name)) break;

            if (foodItem.getAmount() < amount) {
                amount -= foodItem.getAmount();
                foodItem.setAmount(0);
                foodItemService.updateFoodItem(foodItem);
            } else {
                foodItem.setAmount(foodItem.getAmount() - amount);
                foodItemService.updateFoodItem(foodItem);
                return;
            }
        }
    }
    private boolean isCookable(Recipe recipe) {
        List<Ingredient> ingredients = ingredientRepository.findByRecipe(recipe);

        for (Ingredient ingredient : ingredients) {
            if (!checkIfAmountAvailable(ingredient.getName(), ingredient.getAmount()))
                return false;
        }
        return true;
    }

    @Override
    public List<Recipe> allCookableRecipes() {
        List<Recipe> recipeList = recipeService.getAll();
        recipeList.removeIf(a -> !isCookable(a));
        return recipeList;
    }

    @Override
    public void cookRecipe(Recipe recipe) {
        if (!isCookable(recipe)) throw new IllegalArgumentException();
        Iterable<Ingredient> ingredients = ingredientRepository.findByRecipe(recipe);

        ingredients.forEach(
                a -> removeAmountFromAllWith(a.getName(), a.getAmount()));
    }
}
