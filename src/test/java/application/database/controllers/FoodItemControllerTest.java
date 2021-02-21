package application.database.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ContextConfiguration(locations = "./main/java")
class FoodItemControllerIntTest {

    @Autowired
    MockMvc mvc;

    @Test
    void addFoodItem() {
    }

    @Test
    void all() {
    }

    @Test
    void get() {
    }

    @Test
    void deleteFoodItem() {
    }

    @Test
    void setFoodItemService() {
    }
}