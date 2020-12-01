package com.company.database;

import com.company.database.dataObjects.FoodItem;
import com.company.database.dataObjects.History;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.company.database.FoodItemDBHandler.*;

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

        int amount =  Integer.parseInt(foodItem[1]);

        insertFoodItem(new FoodItem(
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
        List<FoodItem> foodItems = getAllFoodItems(SelectFoodItem.ALL);
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
    public boolean removeAmountOfFoodItemIfAvailable(String name, int amount) {

        List<FoodItem> matchingFoodItems = getFoodItems(name);

        if (!checkIfAmountIsAvailable(amount, matchingFoodItems)) return false;

        for (FoodItem item : matchingFoodItems) {
            if (item.getAmount() >= amount) {
                changeFoodItemAmount(item, -amount);
                break;
            }else {
                amount -= item.getAmount();
                changeFoodItemAmount(item, -item.getAmount());
            }
        }
        return true;
    }

    private static boolean checkIfAmountIsAvailable(int amount, List<FoodItem> matchingFoodItems) {
        int availableAmount = matchingFoodItems.stream()
                .mapToInt(i -> i.getAmount())
                .sum();
        if (amount > availableAmount)
            return false;
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

        return getAllFoodItems(SelectFoodItem.EXISTING)
                .stream()
                .filter(a -> a.getExpireDate().isBefore(maxDate))
                .sorted(Comparator.comparing(FoodItem::getExpireDate))
                .collect(Collectors.toList());
    }


    public enum SelectFoodItem {
        ALL,USED_UP,EXISTING
    }
    /**
     * Returns foodItems in database
     * @param mode  Mode EXISTING = Food that is left in the Refrigerator (amount != 0)
     *              Mode USED_UP = Food that was in the Refrigerator but ist now used up (amount = 0)
     *              Mode ALL = Both Food that is or was in the refrigerator
     * @return A list of foodItems in database
     */
    public List<FoodItem> getAllFoodItems(SelectFoodItem mode){
        LinkedList<FoodItem> list;
        switch (mode){
            case EXISTING:
                list = getAllExistingFoodItems();
                break;
            case USED_UP:
                list = getAllUsedUpFoodItems();
                break;
            case ALL:
                list = getAllExistingFoodItems();
                assert list != null;
                list.addAll(getAllUsedUpFoodItems());
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
        return HistoryDBHandler.getHistoryOf(id);
    }

    /**
     * Returns available amount of given food
     * @param name name of foodItems
     * @param amount amount that should be left
     * @return  true if there is enough left
     */
    public boolean amountAvailable(String name, int amount) {
        List<FoodItem> foodItems = getAllFoodItems(SelectFoodItem.EXISTING);

        int availableAmount = foodItems.stream()
                .filter(a -> a.getName().equals(name))
                .mapToInt(a -> a.getAmount())
                .sum();

        return amount <=  availableAmount;
    }
}