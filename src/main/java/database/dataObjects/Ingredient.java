package main.java.database.dataObjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Til-W
 * @version 1.0
 *
 */
@DatabaseTable(tableName = "Ingredient")
public class Ingredient {
    @DatabaseField(id = true, generatedId = true)
    private int id;
    @DatabaseField(foreign = true)
    private Recipe recipe;
    @DatabaseField
    private String name;
    @DatabaseField
    private int amount;

    Ingredient() {}

    public Ingredient(String name, int amount, Recipe recipeId){
        this.name = name;
        this.amount = amount;
        this.recipe = recipeId;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String toString() {
        return name + ", " + amount;
    }
}
