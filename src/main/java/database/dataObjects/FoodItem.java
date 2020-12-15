package main.java.database.dataObjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDate;
/**
 * @author Til-W
 * @version 1.0
 *
 */
@DatabaseTable(tableName = "FoodItem")
 public class FoodItem {
    @DatabaseField(id = true, generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private int amount;
    @DatabaseField
    private LocalDate expireDate;

    FoodItem() {}

    public FoodItem(String name, int amount, LocalDate expireDate) {
        this.name = name;
        this.amount = amount;
        this.expireDate = expireDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String toString(){
        StringBuilder formatedFoodItem = new StringBuilder();
        formatedFoodItem.append("Id:            " + id + "\n");
        formatedFoodItem.append("Name:          " + name + "\n");
        formatedFoodItem.append("Amount:        " + amount + "g\n");
        formatedFoodItem.append("ExpireDate:    " + expireDate + "\n");
        formatedFoodItem.append("____________________________________________________ \n");

        return formatedFoodItem.toString();
    }
}