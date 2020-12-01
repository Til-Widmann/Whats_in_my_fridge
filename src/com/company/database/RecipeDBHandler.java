package com.company.database;

import com.company.database.dataObjects.Recipe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class RecipeDBHandler extends SQLiteDBHandler{

    /**
     * Inserts a new Recipe to the databse.
     * @param recipe The Recipe that is to be added.
     * @return a int of the autogenerated key in the database or -1 in case of a database Error.
     */
    static int insertRecipe(Recipe recipe) {
        try {
            c = DBConnect.getConnection();

            prepStm = c.prepareStatement("INSERT INTO Recipe (id, name) VALUES (?, ?)");
            prepStm.setString(2, recipe.getName());
            prepStm.execute();

            return  prepStm.getGeneratedKeys().getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }finally {
            close();
        }
    }

    /**
     * Returns the Recipe Object that equals the input name.
     * @param name Name of Recipe to be returned.
     * @return Recipe Object with the same name or null in case of database Error.
     */
    static Recipe getRecipe(String name) {
        try {
            c = DBConnect.getConnection();

            prepStm = c.prepareStatement("SELECT * FROM Recipe WHERE name = ?");
            prepStm.setString(1, name);
            ResultSet rs = prepStm.executeQuery();

            return getRecipeFromResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            close();
        }
    }

    private static Recipe getRecipeFromResultSet(ResultSet rs) throws SQLException {

        return new Recipe(rs.getInt(1), rs.getString(2));
    }

    /**
     * Gets all Recipes in the database.
     * @return LinkedList of all Recipe objects or null in case of database Error.
     */
    static LinkedList<Recipe> getAllRecipes() {
        try {
            c = DBConnect.getConnection();

            prepStm = c.prepareStatement("SELECT * FROM Recipe");
            ResultSet rs = prepStm.executeQuery();

            LinkedList<Recipe> recipes = new LinkedList<>();
            while (rs.next())  {
                recipes.add(new Recipe(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
            return recipes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            close();
        }
    }

}
