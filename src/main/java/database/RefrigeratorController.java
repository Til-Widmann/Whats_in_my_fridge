package main.java.database;

import main.java.database.dataObjects.FoodItem;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Til-W
 * @version 1.0
 *
 */
public class RefrigeratorController {

    private static FoodItemDBHandler foodItemDBHandler = new FoodItemDBHandler();
    private static HistoryDBHandler historyDBHandler= new HistoryDBHandler();

    public static  void addFood(String[] strings){
        foodItemDBHandler.addFoodItemAndHistory(getFoodItemFromString(strings));
    }
    private static FoodItem getFoodItemFromString(String[] foodItemStrings) {
        int amount =  Integer.parseInt(foodItemStrings[1]);
        return new FoodItem(
                foodItemStrings[0],
                amount,
                dateFormatter(foodItemStrings[2]));
    }
    private static LocalDate dateFormatter(String stringDatum){
        String[] stringDate = stringDatum.split("[-./]");
        return LocalDate.of(
                Integer.parseInt(stringDate[2]),
                Integer.parseInt(stringDate[1]),
                Integer.parseInt(stringDate[0]));
    }


    static boolean checkIfAmountAvailable(String name, int amount){
        ArrayList<FoodItem> foodItems = foodItemDBHandler.getAll();
        int availableAmount = foodItems
                .stream()
                .filter(a -> !a.getName().equals(name))
                .mapToInt(FoodItem::getAmount).sum();
        return amount >= availableAmount;
    }
    public static ArrayList<FoodItem> getAllFoodInFridge() {
        return foodItemDBHandler.getAll()
                .stream()
                .filter(a -> a.getAmount() > 0)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public static ArrayList<FoodItem> getAllUsedUp(){
        return foodItemDBHandler.getAll()
                .stream()
                .filter(a-> a.getAmount() <= 0)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static public List<FoodItem> expiredFood() {
        return expiresInLessThen(0);
    }

    static public ArrayList<FoodItem> expiresInLessThen(int days){
        LocalDate maxDate = LocalDate.now().plusDays(days);
        return foodItemDBHandler.getAll()
                .stream()
                .filter(a -> a.getExpireDate().isBefore(maxDate))
                .sorted(Comparator.comparing(FoodItem::getExpireDate))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static void removeAmountFromAllWith(String name, int amount) {
        ArrayList<FoodItem> foodItems = foodItemDBHandler.getAll();
        foodItems.sort(Comparator.comparing(FoodItem::getExpireDate));
        for (FoodItem item : foodItems) {
            if (!item.getName().equals(name)) break;

            if (item.getAmount() < amount){
                amount -= item.getAmount();
                foodItemDBHandler.updateAmount(item, 0);
            }else{
                foodItemDBHandler.updateAmount(item, item.getAmount() - amount);
                return;
            }
        }
    }
}