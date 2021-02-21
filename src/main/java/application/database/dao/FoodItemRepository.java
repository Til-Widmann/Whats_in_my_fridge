package application.database.dao;

import application.database.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("foodItemRepository")
public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {


    List<FoodItem> findByName(String name);

    FoodItem findById(int id);
}
