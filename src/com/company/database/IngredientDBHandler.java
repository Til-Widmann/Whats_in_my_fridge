package com.company.database;

import com.company.database.dataObjects.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class IngredientDBHandler extends SQLiteDBHandler{
    /**
     * Inserts a new ingredient into the database.
     * @param ingredient ingredient object that is to be inserted.
     */
    static void insertIngredient (Ingredient ingredient) {
        try {
            c = DBConnect.getConnection();

            insertIngredientQuery(ingredient);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }
    }

    private static void insertIngredientQuery(Ingredient ingredient) throws SQLException {
        prepStm = c.prepareStatement("INSERT INTO Ingredient (id, name, amount, recipeId) " +
                "VALUES (?, ?, ?, ?)");
        prepStm.setString(2, ingredient.getName());
        prepStm.setInt(3, ingredient.getAmount());
        prepStm.setInt(4, ingredient.getRecipeId());
        prepStm.execute();
    }

    /**
     * Returns all Ingredients of a specific Recipe.
     * @param recipeId id of Recipe used to search for corresponding Ingredients.
     * @return A LinkedList including all Ingredients of Recipe or null in case of database Error.
     */
    static LinkedList<Ingredient> getAllIngredientsOf(int recipeId) {
        try {
            c = DBConnect.getConnection();

            ResultSet rs = getAllIngredientsOfQuery(recipeId);

            return getIngredientsFrom(rs);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            close();
        }
    }

    private static ResultSet getAllIngredientsOfQuery(int recipeId) throws SQLException {
        prepStm = c.prepareStatement("SELECT * FROM Ingredient WHERE recipeId = ?");
        prepStm.setInt(1, recipeId);

        return prepStm.executeQuery();
    }

    private static LinkedList<Ingredient> getIngredientsFrom(ResultSet rs) throws SQLException {
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
    }
}
