package com.company.database;

import com.company.database.dataObjects.FoodItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class FoodItemDBHandlerTest {
    String testFoodName = "TestFood";

    @BeforeEach
    void setUp() {

        LocalDate time = LocalDate.now();
        FoodItem foodItem = new FoodItem(-1, testFoodName, 500, time);
        FoodItemDBHandler.insertFoodItem(foodItem);

        FoodItem usedUpFoodItem = new FoodItem(-2, testFoodName,0,time);
        FoodItemDBHandler.insertFoodItem(usedUpFoodItem);
    }

    @AfterEach
    void tearDown() {
        FoodItemDBHandler.removeFoodItem(testFoodName);
    }

    @Test
    void insertFoodItem() {
        assertEquals(1, FoodItemDBHandler.getAllExistingFoodItems().size());
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
        FoodItemDBHandler.changeFoodItemAmountTo(foodItem, 0);
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