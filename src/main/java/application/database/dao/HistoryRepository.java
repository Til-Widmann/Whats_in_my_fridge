package application.database.dao;

import application.database.model.FoodItem;
import application.database.model.History;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("historyRepository")
public interface HistoryRepository extends CrudRepository<History, Integer> {

    List<History> findByFoodItem(FoodItem foodItem);
}
