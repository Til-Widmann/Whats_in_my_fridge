package main.java.database.dataObjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDateTime;
/**
 * @author Til-W
 * @version 1.0
 *
 */
@DatabaseTable(tableName = "History")
public class History {
    @DatabaseField(id = true, generatedId = true)
    private int id;
    @DatabaseField(foreign = true)
    private FoodItem foodItem;
    @DatabaseField
    private  LocalDateTime useDate;
    @DatabaseField
    private int amount;

    History() {}

    public History(FoodItem foodItem, LocalDateTime useDate, int amount) {
        this.foodItem = foodItem;
        this.useDate = useDate;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public LocalDateTime getUseDate() {
        return useDate;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return  "Id             : " + id + "\n"+
                "Date of Use    : " + useDate + "\n"+
                "Amount added   : " + amount + "\n\n";
    }
}




