package main.java.database;

import main.java.database.dataObjects.Recipe;
import main.java.database.DBConnect;
import main.java.database.SQLiteDBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class RecipeDBHandler extends SQLiteDBHandler {

    /**
     * Inserts a new Recipe to the databse.
     * @param recipe The Recipe that is to be added.
     * @return a int of the autogenerated key in the database or -1 in case of a database Error.
     */
    static int insertRecipe(Recipe recipe) {
        try {
            c = DBConnect.getConnection();

            return  insertRecipeQuery(recipe);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }finally {
            close();
        }
    }

    private static int insertRecipeQuery(Recipe recipe) throws SQLException {
        prepStm = c.prepareStatement("INSERT INTO Recipe (id, name) VALUES (?, ?)");
        prepStm.setString(2, recipe.getName());
        prepStm.execute();

        return prepStm.getGeneratedKeys().getInt(1);
    }

    /**
     * Returns the Recipe Object that equals the input name.
     * @param name Name of Recipe to be returned.
     * @return Recipe Object with the same name or null in case of database Error.
     */
    static Recipe getRecipe(String name) {
        try {
            c = DBConnect.getConnection();

            ResultSet rs = getRecipeQuery(name);
            return getRecipeFrom(rs).getFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            close();
        }
    }

    private static ResultSet getRecipeQuery(String name) throws SQLException {
        prepStm = c.prepareStatement("SELECT * FROM Recipe WHERE name = ?");
        prepStm.setString(1, name);
        return prepStm.executeQuery();
    }

    private static LinkedList<Recipe> getRecipeFrom(ResultSet rs) throws SQLException {

        LinkedList<Recipe> recipes = new LinkedList<>();
        while (rs.next())  {
            recipes.add(new Recipe(
                    rs.getInt("id"),
                    rs.getString("name")
            ));
        }
        return recipes;
    }

    /**
     * Gets all Recipes in the database.
     * @return LinkedList of all Recipe objects or null in case of database Error.
     */
    static LinkedList<Recipe> getAllRecipes() {
        try {
            c = DBConnect.getConnection();

            ResultSet rs = getAllRecipesQuery();
            return getRecipeFrom(rs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            close();
        }
    }

    private static ResultSet getAllRecipesQuery() throws SQLException {
        prepStm = c.prepareStatement("SELECT * FROM Recipe");
        return prepStm.executeQuery();
    }

    static void removeRecipe(String name){
        try {
            c = DBConnect.getConnection();

            removeRecipeQuery(name);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }
    }

    private static void removeRecipeQuery(String name) throws SQLException{
        prepStm = c.prepareStatement("DELETE FROM Recipe WHERE name = ?");
        prepStm.setString(1, name);
        prepStm.execute();
    }


}
