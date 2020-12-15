package main.java.database;

import main.java.database.dataObjects.FoodItem;
import main.java.database.dataObjects.History;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Til-W
 * @version 1.0
 *
 */
public class RefrigeratorController {
    FoodItemDBHandler foodItemDBHandler = new FoodItemDBHandler();
    HistoryDBHandler historyDBHandler = new HistoryDBHandler();


    public void addFoodItemAndHistory(String[] foodItemStrings) {

        FoodItem foodItem = getFoodItemFromString(foodItemStrings);

        foodItemDBHandler.add(foodItem);

        historyDBHandler.add(createHistoryWith(foodItem));
    }

    private FoodItem getFoodItemFromString(String[] foodItemStrings) {
        int amount =  Integer.parseInt(foodItemStrings[1]);
        return new FoodItem(
                foodItemStrings[0],
                Integer.parseInt(foodItemStrings[1]),
                dateFormatter(foodItemStrings[2]));
    }

    private static LocalDate dateFormatter(String stringDatum){
        String[] stringDate = stringDatum.split("[-./]");

        return LocalDate.of(
                Integer.parseInt(stringDate[2]),
                Integer.parseInt(stringDate[1]),
                Integer.parseInt(stringDate[0]));
    }

    private History createHistoryWith(FoodItem foodItem) {
        return new History(
                    foodItem,
                    LocalDateTime.now(),
                    foodItem.getAmount());
    }



    public void updateAmount(FoodItem foodItem, int amount){
        foodItem.setAmount(amount);
        foodItemDBHandler.update(foodItem);
    }
































    public static FoodItem getFoodItemFromId(int id) {
        List<FoodItem> foodItems = getAllFoodItems(SelectFoodItem.ALL);
        for(FoodItem item : foodItems) {
            if (item.getFoodItemId() == id)
                return item;
        }
        return null;
    }


    public static boolean removeAmountOfFoodItemIfAvailable(String name, int amount) {

        List<FoodItem> matchingFoodItems = getFoodItems(name);

        if (!checkIfAmountIsAvailable(amount, matchingFoodItems)) return false;

        for (FoodItem item : matchingFoodItems) {
            if (item.getAmount() >= amount) {
                changeFoodItemAndHistory(item.getAmount() - amount, item);
                break;
            }else {
                amount -= item.getAmount();
                changeFoodItemAndHistory(0, item);
            }
        }
        return true;
    }

    private static void changeFoodItemAndHistory(int amount, FoodItem item) {
        changeAmountTo(item.getFoodItemId(), amount);
        History history = new History(item.getFoodItemId(), LocalDateTime.now(), amount);
        HistoryDBHandler.insertHistory(history);
    }

    private static boolean checkIfAmountIsAvailable(int amount, List<FoodItem> matchingFoodItems) {
        int availableAmount = matchingFoodItems.stream()
                .mapToInt(i -> i.getAmount())
                .sum();
        return amount <= availableAmount;
    }




    public static List<FoodItem> expiredFood() {
        return expiresInLessThen(0);
    }


    public static List<FoodItem> expiresInLessThen(int days){

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
                List<FoodItem> list = new LinkedList<>();
                list.addAll(getAllExisting());
                list.addAll(getAllUsedUp());
                return list;
            }
        },
        USED_UP{
            @Override
            public List<FoodItem> getFoodItemWithMode() {
                return getAllUsedUp();
            }
        },
        EXISTING {
            @Override
            public List<FoodItem> getFoodItemWithMode() {
                return getAllExisting();
            }
        };
        public abstract List<FoodItem> getFoodItemWithMode();
    }

    public static List<FoodItem> getAllFoodItems(SelectFoodItem mode){
        return SelectFoodItem.valueOf(mode.name()).getFoodItemWithMode();
    }


    public static LinkedList <History> getHistoryFromId(int id) {
        return HistoryDBHandler.getHistoryOf(id);
    }

}