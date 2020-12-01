package com.company.database;

import com.company.database.dataObjects.Ingredient;

import java.sql.ResultSet;
import java.util.LinkedList;

public class IngredientDBHandler extends SQLiteDBHandler{
    /**
     * Inserts a new ingredient into the database.
     * @param ingredient ingredient object that is to be inserted.
     */
    static void insertIngredient (Ingredient ingredient) {
        try {
            c = DBConnect.getConnection();

            prepStm = c.prepareStatement("INSERT INTO Ingredient (id, name, amount, recipeId) " +
                    "VALUES (?, ?, ?, ?)");

            prepStm.setString(2, ingredient.getName());
            prepStm.setInt(3, ingredient.getAmount());
            prepStm.setInt(4, ingredient.getRecipeId());
            prepStm.execute();

        } catch (Exception e) {
            System.out.println("Error at insertIngredient : " + e.getMessage());
        }finally {
            close();
        }
    }

    /**
     * Returns all Ingredients of a specific Recipe.
     * @param recipeId id of Recipe used to search for corresponding Ingredients.
     * @return A LinkedList including all Ingredients of Recipe or null in case of database Error.
     */
    static LinkedList<Ingredient> getAllIngredientsOf(int recipeId) {
        try {
            c = DBConnect.getConnection();

            prepStm = c.prepareStatement("SELECT * FROM Ingredient WHERE recipeId = ?");
            prepStm.setInt(1, recipeId);

            ResultSet rs = prepStm.executeQuery();

            LinkedList<Ingredient> ingredients = new LinkedList<>();
            while (rs.next()) {
                ingredients.add(new Ingredient(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4)
                ));
            }
            return ingredients;
        }catch (Exception e) {
            System.out.println("Error at getAllIngredientsOf: " + e.getMessage());
            return null;
        }finally {
            close();
        }
    }
}
