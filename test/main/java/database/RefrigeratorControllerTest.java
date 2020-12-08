package main.java.database;

import main.java.database.dataObjects.FoodItem;
import main.java.database.dataObjects.History;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.spi.DirObjectFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RefrigeratorControllerTest {
    private static final String TEST_FOOD_NAME = "Test Food";
    private static final int TEST_FOOD_AMOUNT = 500;

    @BeforeEach
    void setUp() {
        String[] foodItem= {TEST_FOOD_NAME, String.valueOf(TEST_FOOD_AMOUNT) ,"20.10.2022"};
        RefrigeratorController.addFoodItemWithHistory(foodItem);
    }

    @AfterEach
    void tearDown() {
        RefrigeratorController.getAllFoodItems(RefrigeratorController.SelectFoodItem.ALL).stream()
                .filter(a -> a.getName().equals(TEST_FOOD_NAME))
                .forEach( this::tearDownHelper);

    }
    private void tearDownHelper(FoodItem foodItem) {
        HistoryDBHandler.removeHistory(foodItem.getFoodItemId());
        FoodItemDBHandler.removeFoodItem(foodItem.getName());
    }

    @Test
    void addFoodItemWithHistory() {
        FoodItem foodItem = FoodItemDBHandler.getFoodItems(TEST_FOOD_NAME).getFirst();
        History history = HistoryDBHandler.getHistoryOf(foodItem.getFoodItemId()).getFirst();
        assertNotNull(foodItem);
        assertNotNull(history);
    }

    @Test
    void getFoodItemFromId() {
        FoodItem foodItem = FoodItemDBHandler.getFoodItems(TEST_FOOD_NAME).getFirst();
        FoodItem foodItemFromId = RefrigeratorController.getFoodItemFromId(foodItem.getFoodItemId());
        assertEquals(foodItem.getName(), foodItemFromId.getName());
        assertEquals(foodItem.getAmount(), foodItemFromId.getAmount());
        assertEquals(foodItem.getExpireDate(), foodItemFromId.getExpireDate());
    }

    @Test
    void removeAmountOfFoodItemIfAvailable() {
        assertFalse(RefrigeratorController.
                removeAmountOfFoodItemIfAvailable(
                        TEST_FOOD_NAME,
                        1000
                ));
        FoodItemDBHandler.insertFoodItem(new FoodItem(
                TEST_FOOD_NAME,
                TEST_FOOD_AMOUNT,
                LocalDate.ofEpochDay(20000)));

        assertTrue(RefrigeratorController.removeAmountOfFoodItemIfAvailable(
                TEST_FOOD_NAME,
                1000
        ));

        List<FoodItem> usedFoodItems = FoodItemDBHandler.getAllUsedUpFoodItems().stream()
                .filter(a -> a.getName().equals(TEST_FOOD_NAME))
                .collect(Collectors.toList());
        assertEquals(2, usedFoodItems.size());

    }

    @Test
    void expiredFood() {
        int expiredFoodItems = RefrigeratorController.expiredFood().size();
        FoodItem foodItem = new FoodItem(
                TEST_FOOD_NAME,
                TEST_FOOD_AMOUNT,
                LocalDate.ofEpochDay(10000));
        FoodItemDBHandler.insertFoodItem(foodItem);
        assertEquals(expiredFoodItems + 1, RefrigeratorController.expiredFood().size());
    }
    @Test
    void expiresInLessThen() {
        int days = 10;
        int epieringFoodItems = RefrigeratorController.expiresInLessThen(days).size();
        FoodItem foodItem = new FoodItem(
                TEST_FOOD_NAME,
                TEST_FOOD_AMOUNT,
                LocalDate.now().plusDays(5));

        FoodItemDBHandler.insertFoodItem(foodItem);
        assertEquals(epieringFoodItems + 1 , RefrigeratorController.expiresInLessThen(days).size());
    }

    @Test
    void getAllFoodItems() {

    }

    @Test
    void getHistoryFromId() {
    }

    @Test
    void amountAvailable() {
    }
}