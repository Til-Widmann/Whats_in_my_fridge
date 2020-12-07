package main.java.database;

import main.java.database.dataObjects.FoodItem;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class FoodItemDBHandlerTest {
    private final String testFoodName = "TestFood";
    private static LinkedList<FoodItem> savedDBState;
    private final LocalDate time = LocalDate.ofEpochDay(20000);




    @BeforeAll
    static void beforeAll() {
        savedDBState = new LinkedList<>();
        savedDBState.addAll(FoodItemDBHandler.getAllExistingFoodItems());
        savedDBState.addAll(FoodItemDBHandler.getAllUsedUpFoodItems());
        savedDBState.forEach(a -> FoodItemDBHandler.removeFoodItem(a.getName()));
    }

    @AfterAll
    static void afterAll() {
        savedDBState.forEach(a -> FoodItemDBHandler.insertFoodItem(a));

    }

    @BeforeEach
    void setUp() {


        FoodItem foodItem = new FoodItem(testFoodName, 500, time);
        FoodItemDBHandler.insertFoodItem(foodItem);

        FoodItem usedUpFoodItem = new FoodItem(testFoodName,0,time);
        FoodItemDBHandler.insertFoodItem(usedUpFoodItem);
    }

    @AfterEach
    void tearDown() {
        FoodItemDBHandler.removeFoodItem(testFoodName);
    }

    @Test
    void insertFoodItem() {

        FoodItem foodItem = FoodItemDBHandler.getAllExistingFoodItems().getFirst();
        assertEquals(testFoodName, foodItem.getName());
        assertEquals(500, foodItem.getAmount());
        assertEquals(time, foodItem.getExpireDate());
    }

    @Test
    void getFoodItems() {
        assertEquals(1, FoodItemDBHandler.getFoodItems(testFoodName).size());
    }

    @Test
    void getAllExistingFoodItems() {
        assertEquals(1, FoodItemDBHandler.getAllExistingFoodItems().size());
    }

    @Test
    void changeFoodItemAmount() {
        FoodItem foodItem = FoodItemDBHandler.getFoodItems(testFoodName).getFirst();
        FoodItemDBHandler.changeFoodItemAmountTo(foodItem.getFoodItemId(), 0);

        assertEquals(2, FoodItemDBHandler.getAllUsedUpFoodItems().size());
    }

    @Test
    void getAllUsedUpFoodItems() {
        assertEquals(1, FoodItemDBHandler.getAllUsedUpFoodItems().size());
    }

    @Test
    void removeFoodItem() {
        FoodItemDBHandler.removeFoodItem(testFoodName);
        LinkedList<FoodItem> foodItems = FoodItemDBHandler.getAllExistingFoodItems();
        foodItems.addAll(FoodItemDBHandler.getAllUsedUpFoodItems());
        assertEquals(0, foodItems.size());
    }
}