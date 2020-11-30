package com.company.database;

import com.company.database.dataObjects.FoodItem;
import com.company.database.dataObjects.History;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
/**
 * @author Til-W
 * @version 1.0
 *
 */
public class RefrigeratorController {


    /**
     * Insert a FoodItem into the Database
     * @param foodItem takes a String array that is to be inserted
     *                 [0] name
     *                 [1] amount
     *                 [2] expireDate
     */
    public void addFoodItem(String[] foodItem) {
        SQLiteDBHandler db = new SQLiteDBHandler();

        int amount =  Integer.parseInt(foodItem[1]);

        db.insertFoodItem(new FoodItem(
                -1,
                foodItem[0],
                amount,
                dateFormatter(foodItem[2])
        ));
    }

    /**
     * Gets a specific FoodItem foom all existing and usedUp FoodItems
     * @param id FoodItem id
     * @return  FoodItem with equal id or null if there is no such item
     */
    public FoodItem getFoodItemFromId(int id) {
        List<FoodItem> foodItems = getAllFoodItems(2);
        for(FoodItem item : foodItems) {
            if (item.getFoodItemId() == id)
                return item;
        }
        return null;
    }

    /**
     * Removes given amount from Food Item with same name sooner expiring items first.
     * @param name      Name of the FoodItems to be changed
     * @param amount    amount that should be changed (can be negative)
     * @return  true if there is enough in the storage so the change is successfull
     */
    public boolean removeAmountOfFoodItem(String name, int amount) {
        SQLiteDBHandler db = new SQLiteDBHandler();

        List<FoodItem> matchingFoodItems = db.getFoodItems(name);

        int availableAmount = matchingFoodItems.stream()
                .mapToInt(i -> i.getAmount())
                .sum();
        if (amount > availableAmount)
            return false;

        for (FoodItem item : matchingFoodItems) {
            if (item.getAmount() >= amount) {
                db.changeFoodItemAmount(item, -amount);
                break;
            }else {
                amount -= item.getAmount();
                db.changeFoodItemAmount(item, -item.getAmount());
            }
        }
        return true;
    }

    /**
     * Formates a String to a LocalDate
     * @param stringDatum String datum formated in (dd-mm-yyyy),(dd/mm/yyyy) and (dd.mm.yyyy)
     * @return LocalDate format
     */
    private LocalDate dateFormatter(String stringDatum){
        String[] stringDate = stringDatum.split("[-./]");

        return LocalDate.of(
                Integer.parseInt(stringDate[2]),
                Integer.parseInt(stringDate[1]),
                Integer.parseInt(stringDate[0]));
    }

    /**
     * Returns all expired Food
     * @return List of expired Food in storage
     */
    public List<FoodItem> expiredFood() {
        return expiresInLessThen(0);
    }

    /**
     * Return food that expires in given days
     * @param days  max days in which it will expire
     * @return list of food that will expire in given timeframe
     */
    public List<FoodItem> expiresInLessThen(int days){
        SQLiteDBHandler db = new SQLiteDBHandler();

        LocalDate maxDate = LocalDate.now().plusDays(days);

        return getAllFoodItems(0)
                .stream()
                .filter(a -> a.getExpireDate().isBefore(maxDate))
                .sorted(Comparator.comparing(FoodItem::getExpireDate))
                .collect(Collectors.toList());
    }

    /**
     * Returns foodItems in database
     * @param mode  Mode 0 = Food that is left in the Refrigerator (amount != 0)
     *              Mode 1 = Food that was in the Refrigerator but ist now used up (amount = 0)
     *              Mode 2 = Both Food that is or was in the refrigerator
     * @return A list of foodItems in database
     */
    public List<FoodItem> getAllFoodItems(int mode){
        SQLiteDBHandler db = new SQLiteDBHandler();
        LinkedList<FoodItem> list;
        switch (mode){
            case 0:
                list = db.getAllExistingFoodItems();
                break;
            case 1:
                list = db.getAllUsedUpFoodItems();
                break;
            case 2:
                list = db.getAllExistingFoodItems();
                list.addAll(db.getAllUsedUpFoodItems());
                break;
            default:
                return null;
        }
        return list;
    }

    /**
     * Returns all History events of given FoodItem
     * @param id id of FoodItem
     * @return  list of all matching history events
     */
    public LinkedList <History> getHistoryFromId(int id) {
        SQLiteDBHandler db = new SQLiteDBHandler();
        return db.getHistoryOf(id);
    }

    /**
     * Returns available amount of given food
     * @param name name of foodItems
     * @param amount amount that should be left
     * @return  true if there is enough left
     */
    public boolean amountAvailable(String name, int amount) {
        List<FoodItem> foodItems = getAllFoodItems(0);

        int availableAmount = foodItems.stream()
                .filter(a -> a.getName().equals(name))
                .mapToInt(a -> a.getAmount())
                .sum();

        return amount <=  availableAmount;
    }
}