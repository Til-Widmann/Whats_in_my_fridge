package main.java.database;

import main.java.database.dataObjects.FoodItem;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class FoodItemDBHandler extends SQLiteDBHandler{

    /**
     * Inserts a FoodItem into the Database.
     * @param foodItem The FoodItem that is to be stored in the database.
     * @return A int of the Auto-generated id in the database or -1 in case of a database Error.
     */
    static int insertFoodItem(FoodItem foodItem) {
        try {
            c = DBConnect.getConnection();
            return insertFoodItemQuery(foodItem);
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }finally {
            close();

        }
    }

    private static int insertFoodItemQuery(FoodItem foodItem) throws SQLException {
        prepStm = c.prepareStatement("INSERT INTO FoodItem VALUES(?, ?, ?, ?)");
        prepStm.setString(2, foodItem.getName());
        prepStm.setInt(3, foodItem.getAmount());
        prepStm.setDate(4, Date.valueOf(foodItem.getExpireDate()));
        prepStm.execute();
        return prepStm.getGeneratedKeys().getInt(1);
    }

    /**
     * Returns all FoodItems equal to input name.
     * The returned items are ordered by expire date.
     * @param name The variable name of Food items that are to be returned.
     * @return A LinkedList of all Matching FoodItems or null in case of a database Error.
     */
    static LinkedList<FoodItem> getFoodItems(String name) {
        try{
            c = DBConnect.getConnection();
            return getFoodItemsQuery(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            close();
        }
    }

    private static LinkedList<FoodItem> getFoodItemsQuery(String name) throws SQLException {
        prepStm = c.prepareStatement("SELECT * FROM FoodItem WHERE amount > 0 AND name = ?" +
                "ORDER BY expireDate");
        prepStm.setString(1,name);
        ResultSet rs = prepStm.executeQuery();

        return getFoodItemObjectListFrom(rs);
    }

    /**
     * Returns All FoodItems which have a amount > 0.
     * @return A LinkedList of FoodItems or null in case of a database Error.
     */
    static LinkedList<FoodItem> getAllExistingFoodItems() {
        try{
            c = DBConnect.getConnection();
            return getAllExistingFoodItemsQuery();
        } catch (Exception e) {
            System.out.println("Error at getAllExistingFoodItems " + e.getMessage());
            return null;
        }finally {
            close();
        }
    }

    private static LinkedList<FoodItem> getAllExistingFoodItemsQuery() throws SQLException {
        prepStm = c.prepareStatement("SELECT * FROM FoodItem WHERE amount > 0");
        ResultSet rs = prepStm.executeQuery();

        return  getFoodItemObjectListFrom(rs);
    }

    static void changeFoodItemAmountTo(int foodItemId, int amount) {
        try {
            c = DBConnect.getConnection();

            changeFoodItemAmountToQuery(foodItemId, amount);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }
    }

    private static void changeFoodItemAmountToQuery(int foodItemId, int amount) throws SQLException {
        prepStm = c.prepareStatement("UPDATE FoodItem SET amount = ?  WHERE id = ?");
        prepStm.setInt(1, amount);
        prepStm.setInt(2, foodItemId);
        prepStm.execute();
    }

    /**
     * Returns all FoodItems in the database that are used up (amount == 0)
     * @return A LinkedList with all used up FoodItems
     */
    static LinkedList<FoodItem> getAllUsedUpFoodItems() {
        try{
            c = DBConnect.getConnection();
            ResultSet rs = getAllUsedUpFoodItemsQuery();

            return getFoodItemObjectListFrom(rs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            close();
        }
    }

    private static ResultSet getAllUsedUpFoodItemsQuery() throws SQLException {
        prepStm = c.prepareStatement("SELECT * FROM FoodItem WHERE amount = 0");
        return prepStm.executeQuery();
    }

    private static LinkedList<FoodItem> getFoodItemObjectListFrom(ResultSet rs) throws SQLException {
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
    static void removeFoodItem(String name){
        try {
            c = DBConnect.getConnection();

            removeFoodItemQuery(name);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }
    }
    private static void removeFoodItemQuery(String name) throws SQLException{

        prepStm = c.prepareStatement("DELETE FROM FoodItem WHERE name= ?");
        prepStm.setString(1, name);
        prepStm.execute();
    }

}
