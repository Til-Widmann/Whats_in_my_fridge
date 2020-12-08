package main.java.database;

import main.java.database.dataObjects.FoodItem;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class FoodItemDBHandlerTest {
    private static final String TEST_FOOD_NAME = "TestFood";
    private static LinkedList<FoodItem> savedDBState;
    private static final LocalDate TIME = LocalDate.ofEpochDay(20000);




    @BeforeAll
    static void beforeAll() {
        savedDBState = new LinkedList<>();
        savedDBState.addAll(FoodItemDBHandler.getAllExistingFoodItems());
        savedDBState.addAll(FoodItemDBHandler.getAllUsedUpFoodItems());
        savedDBState.forEach(a -> FoodItemDBHandler.removeFoodItem(a.getName()));
    }

    @AfterAll
    static void afterAll() {
        savedDBState.forEach(FoodItemDBHandler::insertFoodItem);

    }

    @BeforeEach
    void setUp() {


        FoodItem foodItem = new FoodItem(TEST_FOOD_NAME, 500, TIME);
        FoodItemDBHandler.insertFoodItem(foodItem);

        FoodItem usedUpFoodItem = new FoodItem(TEST_FOOD_NAME,0, TIME);
        FoodItemDBHandler.insertFoodItem(usedUpFoodItem);
    }

    @AfterEach
    void tearDown() {
        FoodItemDBHandler.removeFoodItem(TEST_FOOD_NAME);
    }

    @Test
    void insertFoodItem() {
        FoodItem foodItem = FoodItemDBHandler.getAllExistingFoodItems().getFirst();
        assertEquals(TEST_FOOD_NAME, foodItem.getName());
        assertEquals(500, foodItem.getAmount());
        assertEquals(TIME, foodItem.getExpireDate());
    }

    @Test
    void getFoodItems() {
        assertEquals(1, FoodItemDBHandler.getFoodItems(TEST_FOOD_NAME).size());
    }

    @Test
    void getAllExistingFoodItems() {
        assertEquals(1, FoodItemDBHandler.getAllExistingFoodItems().size());
    }

    @Test
    void changeFoodItemAmount() {
        FoodItem foodItem = FoodItemDBHandler.getFoodItems(TEST_FOOD_NAME).getFirst();
        FoodItemDBHandler.changeFoodItemAmountTo(foodItem.getFoodItemId(), 0);

        assertEquals(2, FoodItemDBHandler.getAllUsedUpFoodItems().size());
    }

    @Test
    void getAllUsedUpFoodItems() {
        assertEquals(1, FoodItemDBHandler.getAllUsedUpFoodItems().size());
    }

    @Test
    void removeFoodItem() {
        FoodItemDBHandler.removeFoodItem(TEST_FOOD_NAME);
        LinkedList<FoodItem> foodItems = FoodItemDBHandler.getAllExistingFoodItems();
        foodItems.addAll(FoodItemDBHandler.getAllUsedUpFoodItems());
        assertEquals(0, foodItems.size());
    }
}