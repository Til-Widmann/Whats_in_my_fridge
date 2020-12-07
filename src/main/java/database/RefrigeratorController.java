package main.java.database;

import main.java.database.dataObjects.FoodItem;
import main.java.database.dataObjects.History;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static main.java.database.FoodItemDBHandler.*;

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
    public static void addFoodItemWithHistory(String[] foodItem) {

        int amount =  Integer.parseInt(foodItem[1]);

        int foodItemId = insertFoodItem(new FoodItem(
                foodItem[0],
                amount,
                dateFormatter(foodItem[2])
        ));
        HistoryDBHandler.insertHistory(new History(
                foodItemId,
                LocalDateTime.now(),
                amount
        ));
    }

    /**
     * Gets a specific FoodItem foom all existing and usedUp FoodItems
     * @param id FoodItem id
     * @return  FoodItem with equal id or null if there is no such item
     */
    public static FoodItem getFoodItemFromId(int id) {
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
    public static boolean removeAmountOfFoodItemIfAvailable(String name, int amount) {

        List<FoodItem> matchingFoodItems = getFoodItems(name);

        if (!checkIfAmountIsAvailable(amount, matchingFoodItems)) return false;

        for (FoodItem item : matchingFoodItems) {
            if (item.getAmount() >= amount) {
                changeFoodItemAndHistory(0, item);
                break;
            }else {
                amount -= item.getAmount();
                changeFoodItemAndHistory(0, item);
            }
        }
        return true;
    }

    private static void changeFoodItemAndHistory(int amount, FoodItem item) {
        changeFoodItemAmountTo(item.getFoodItemId(), -amount);
        History history = new History(item.getFoodItemId(), LocalDateTime.now(), amount);
        HistoryDBHandler.insertHistory(history);
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
    private static LocalDate dateFormatter(String stringDatum){
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
    public static List<FoodItem> expiredFood() {
        return expiresInLessThen(0);
    }

    /**
     * Return food that expires in given days
     * @param days  max days in which it will expire
     * @return list of food that will expire in given timeframe
     */
    public static List<FoodItem> expiresInLessThen(int days){
        SQLiteDBHandler db = new SQLiteDBHandler();

        LocalDate maxDate = LocalDate.now().plusDays(days);

        return getAllFoodItems(SelectFoodItem.EXISTING)
                .stream()
                .filter(a -> a.getExpireDate().isBefore(maxDate))
                .sorted(Comparator.comparing(FoodItem::getExpireDate))
                .collect(Collectors.toList());
    }


    public enum SelectFoodItem {
        ALL{
            @Override
            public List<FoodItem> getFoodItemWithMode(){
                LinkedList<FoodItem> list = getAllExistingFoodItems();
                assert list != null;
                list.addAll(getAllUsedUpFoodItems());
                return list;
            }
        },
        USED_UP{
            @Override
            public List<FoodItem> getFoodItemWithMode() {
                return getAllUsedUpFoodItems();
            }
        },
        EXISTING {
            @Override
            public List<FoodItem> getFoodItemWithMode() {
                return getAllExistingFoodItems();
            }
        };
        public abstract List<FoodItem> getFoodItemWithMode();
    }
    /**
     * Returns foodItems in database
     * @param mode  Mode EXISTING = Food that is left in the Refrigerator (amount != 0)
     *              Mode USED_UP = Food that was in the Refrigerator but ist now used up (amount = 0)
     *              Mode ALL = Both Food that is or was in the refrigerator
     * @return A list of foodItems in database
     */
    public static List<FoodItem> getAllFoodItems(SelectFoodItem mode){
        return SelectFoodItem.valueOf(mode.name()).getFoodItemWithMode();
    }

    /**
     * Returns all History events of given FoodItem
     * @param id id of FoodItem
     * @return  list of all matching history events
     */
    public static LinkedList <History> getHistoryFromId(int id) {
        return HistoryDBHandler.getHistoryOf(id);
    }

    /**
     * Returns available amount of given food
     * @param name name of foodItems
     * @param amount amount that should be left
     * @return  true if there is enough left
     */
    public static boolean amountAvailable(String name, int amount) {
        List<FoodItem> foodItems = getAllFoodItems(SelectFoodItem.EXISTING);

        int availableAmount = foodItems.stream()
                .filter(a -> a.getName().equals(name))
                .mapToInt(a -> a.getAmount())
                .sum();

        return amount <=  availableAmount;
    }
}