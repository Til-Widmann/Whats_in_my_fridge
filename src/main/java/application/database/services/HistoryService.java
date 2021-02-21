package application.database.services;

import application.database.model.FoodItem;
import application.database.model.History;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface HistoryService {

    List<History> getHistoryFromFoodItem(FoodItem foodItem);

    void addHistoryTo(FoodItem foodItem);

    void deleteAllOf(FoodItem foodItem);
}
