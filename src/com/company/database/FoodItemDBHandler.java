package com.company.database;

import com.company.database.dataObjects.FoodItem;
import com.company.database.dataObjects.History;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class FoodItemDBHandler extends SQLiteDBHandler{




    /**
     * Inserts a FoodItem into the Database.
     * @param foodItem The FoodItem that is to be stored in the database.
     * @return A int of the Auto-generated id in the database or -1 in case of a database Error.
     */
    int insertFoodItem(FoodItem foodItem) {
        try {
            c = DBConnect.getConnection();

            prepStm = c.prepareStatement("INSERT INTO FoodItem VALUES(?, ?, ?, ?)");
            prepStm.setString(2, foodItem.getName());
            prepStm.setInt(3, foodItem.getAmount());
            prepStm.setDate(4, Date.valueOf(foodItem.getExpireDate()));
            prepStm.execute();

            int foodItemId = prepStm.getGeneratedKeys().getInt(1);

            insertHistory(new History(
                    foodItemId,
                    LocalDateTime.now(),
                    foodItem.getAmount()
            ));

            return foodItemId;
        }catch (Exception e) {
            System.out.println("Error at insertFoodItems: " + e.getMessage());
            return -1;
        }finally {
            close();
        }
    }

    /**
     * Returns all FoodItems equal to input name.
     * The returned items are ordered by expire date.
     * @param name The variable name of Food items that are to be returned.
     * @return A LinkedList of all Matching FoodItems or null in case of a database Error.
     */
    LinkedList<FoodItem> getFoodItems(String name) {
        try{
            c = DBConnect.getConnection();

            prepStm = c.prepareStatement("SELECT * FROM FoodItem WHERE amount > 0 AND name = ?" +
                    "ORDER BY expireDate");
            prepStm.setString(1,name);

            ResultSet rs = prepStm.executeQuery();


            return getFoodItemsFromResultSet(rs);
        } catch (Exception e) {
            System.out.println("Error at getFoodItems " + e.getMessage());
            return null;
        }finally {
            close();
        }
    }
    /**
     * Returns All FoodItems which have a amount > 0.
     * @return A LinkedList of FoodItems or null in case of a database Error.
     */
    LinkedList<FoodItem> getAllExistingFoodItems() {
        try{
            c = DBConnect.getConnection();
            this.stm = c.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM FoodItem WHERE amount > 0");

            LinkedList<FoodItem> foodItems = getFoodItemsFromResultSet(rs);
            return foodItems;
        } catch (Exception e) {
            System.out.println("Error at getAllExistingFoodItems " + e.getMessage());
            return null;
        }finally {
            close();
        }
    }

    private LinkedList<FoodItem> getFoodItemsFromResultSet(ResultSet rs) throws SQLException {
        LinkedList<FoodItem> foodItems = new LinkedList<>();
        while (rs.next()) {
            foodItems.add(new FoodItem(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getDate(4).toLocalDate()
            ));
        }
        return foodItems;
    }

    /**
     * Changes the ammount of a specific FoodItem in the database and adds.
     * After changing the FoodItem it a History item to the database.
     * @param foodItem The Food item that is to be changed.
     * @param amount the amount that is added to the FoodItem (can be negative).
     */
    void changeFoodItemAmount(FoodItem foodItem, int amount) {
        try {
            c = DBConnect.getConnection();

            prepStm = c.prepareStatement("UPDATE FoodItem SET amount = amount + ?  WHERE id = ?");
            prepStm.setInt(1, amount);
            prepStm.setInt(2, foodItem.getFoodItemId());
            prepStm.execute();


            insertHistory(new History(
                    foodItem.getFoodItemId(),
                    LocalDateTime.now(),
                    amount
            ));

        }catch (Exception e) {
            System.out.println("Error at changeFoodItemAmount: " + e.getMessage());
        }finally {
            close();
        }
    }
}
