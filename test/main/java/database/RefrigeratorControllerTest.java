package main.java.database;

import main.java.database.dataObjects.FoodItem;
import main.java.database.dataObjects.History;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RefrigeratorControllerTest {
    private static final String TEST_FOOD_NAME = "Test Food";
    private static final int TEST_FOOD_AMOUNT = 500;

    @BeforeEach
    void setUp() {
        String[] foodItem= {TEST_FOOD_NAME, String.valueOf(TEST_FOOD_AMOUNT) ,"20.10.2022"};
        RefrigeratorController.addFoodItemAndHistory(foodItem);
    }

    @AfterEach
    void tearDown() {
        RefrigeratorController.getAllFoodItems(RefrigeratorController.SelectFoodItem.ALL).stream()
                .filter(a -> a.getName().equals(TEST_FOOD_NAME))
                .forEach( this::tearDownHelper);

    }
    private void tearDownHelper(FoodItem foodItem) {
        HistoryDBHandler.removeHistory(foodItem.getFoodItemId());
        FoodItemDBHandler.remove(foodItem.getName());
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
        FoodItemDBHandler.insert(new FoodItem(
                TEST_FOOD_NAME,
                TEST_FOOD_AMOUNT,
                LocalDate.ofEpochDay(20000)));

        assertTrue(RefrigeratorController.removeAmountOfFoodItemIfAvailable(
                TEST_FOOD_NAME,
                1000
        ));

        long usedFoodItems = FoodItemDBHandler.getAllUsedUp().stream()
                .filter(a -> a.getName().equals(TEST_FOOD_NAME))
                .count();
        assertEquals(2, usedFoodItems);

    }

    @Test
    void expiredFood() {
        int expiredFoodItems = RefrigeratorController.expiredFood().size();
        FoodItem foodItem = new FoodItem(
                TEST_FOOD_NAME,
                TEST_FOOD_AMOUNT,
                LocalDate.ofEpochDay(10000));
        FoodItemDBHandler.insert(foodItem);
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

        FoodItemDBHandler.insert(foodItem);
        assertEquals(epieringFoodItems + 1 , RefrigeratorController.expiresInLessThen(days).size());
    }

    @Test
    void getAllFoodItems() {
        FoodItem foodItem = new FoodItem(TEST_FOOD_NAME, 0, LocalDate.ofEpochDay(20000));
        FoodItemDBHandler.insert(foodItem);
        testExistingFoodItems();

        testUsedUpFoodItems();

        testAllFoodItems();
    }

    private void testAllFoodItems() {
        List<FoodItem> allFoodItems = RefrigeratorController.getAllFoodItems(RefrigeratorController.SelectFoodItem.ALL);
        assertEquals( 2, allFoodItems.stream()
            .filter(a -> a.getName().equals(TEST_FOOD_NAME))
            .count());
    }

    private void testUsedUpFoodItems() {
        List<FoodItem> usedUpFood = RefrigeratorController.getAllFoodItems(RefrigeratorController.SelectFoodItem.USED_UP);
        assertEquals(1, usedUpFood.stream()
                .filter(a -> a.getName().equals(TEST_FOOD_NAME))
                .count());
    }

    private void testExistingFoodItems() {
        List<FoodItem> existingFood = RefrigeratorController.getAllFoodItems(RefrigeratorController.SelectFoodItem.EXISTING);
        assertEquals(1, (int) existingFood.stream()
                .filter(a -> a.getName().equals(TEST_FOOD_NAME))
                .count());
    }

}