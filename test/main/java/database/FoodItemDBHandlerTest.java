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
        savedDBState.addAll(FoodItemDBHandler.getAllExisting());
        savedDBState.addAll(FoodItemDBHandler.getAllUsedUp());
        savedDBState.forEach(a -> FoodItemDBHandler.remove(a.getName()));
    }

    @AfterAll
    static void afterAll() {
        savedDBState.forEach(FoodItemDBHandler::insert);

    }

    @BeforeEach
    void setUp() {


        FoodItem foodItem = new FoodItem(TEST_FOOD_NAME, 500, TIME);
        FoodItemDBHandler.insert(foodItem);

        FoodItem usedUpFoodItem = new FoodItem(TEST_FOOD_NAME,0, TIME);
        FoodItemDBHandler.insert(usedUpFoodItem);
    }

    @AfterEach
    void tearDown() {
        FoodItemDBHandler.remove(TEST_FOOD_NAME);
    }

    @Test
    void insertFoodItem() {
        FoodItem foodItem = FoodItemDBHandler.getAllExisting().getFirst();
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
        assertEquals(1, FoodItemDBHandler.getAllExisting().size());
    }

    @Test
    void changeFoodItemAmount() {
        FoodItem foodItem = FoodItemDBHandler.getFoodItems(TEST_FOOD_NAME).getFirst();
        FoodItemDBHandler.changeAmountTo(foodItem.getFoodItemId(), 0);

        assertEquals(2, FoodItemDBHandler.getAllUsedUp().size());
    }

    @Test
    void getAllUsedUpFoodItems() {
        assertEquals(1, FoodItemDBHandler.getAllUsedUp().size());
    }

    @Test
    void removeFoodItem() {
        FoodItemDBHandler.remove(TEST_FOOD_NAME);
        LinkedList<FoodItem> foodItems = FoodItemDBHandler.getAllExisting();
        foodItems.addAll(FoodItemDBHandler.getAllUsedUp());
        assertEquals(0, foodItems.size());
    }
}