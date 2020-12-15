package main.java.database.dataObjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Til-W
 * @version 1.0
 *
 */
@DatabaseTable(tableName = "Recipe")
public class Recipe {
    @DatabaseField(id = true, generatedId = true)
    private int id;
    @DatabaseField
    private String name;


    Recipe(){}

    public Recipe(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {

        return name;
    }
}
