package application.database.services;

import application.database.model.FoodItem;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service("fakeFoodItemService")
public class FakeFoodItemService implements FoodItemService{

    List<FoodItem> Database;

    public FakeFoodItemService() {
        Database = new LinkedList<>();
        FoodItem foodItem = new FoodItem();
        foodItem.setId(20);
        foodItem.setName("getAllTest");
        foodItem.setAmount(200);
        foodItem.setExpireDate(LocalDate.now());
        Database.add(foodItem);
    }

    @Override
    public FoodItem addFood(FoodItem foodItem) {
        Database.add(foodItem);
        return foodItem;
    }

    @Override
    public List<FoodItem> getAll() {
        return Database;
    }

    @Override
    public FoodItem getById(int id) {
        for (FoodItem item : Database) {
            if (item.getId() == id) return item;
        }
        return null;
    }

    @Override
    public void updateFoodItem(FoodItem foodItem) {

    }

    @Override
    public void delete(int id) {
        Database.removeIf(item -> item.getId() == id);
    }
}
