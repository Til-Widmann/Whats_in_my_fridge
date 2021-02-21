package application.database.services;

import application.database.dao.HistoryRepository;
import application.database.model.FoodItem;
import application.database.model.History;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryServiceImpl implements HistoryService{

    @Autowired
    private HistoryRepository historyRepository;
    
    @Override
    public List<History> getHistoryFromFoodItem(FoodItem foodItem) {
        return historyRepository.findByFoodItem(foodItem);
    }

    @Override
    public void addHistoryTo(FoodItem foodItem){
        historyRepository.save(new History(foodItem));
    }

    public void delete(int id){
        Optional<History> history = historyRepository.findById(id);
        if (history.isPresent()){
            historyRepository.delete(history.get());
        }else {
            throw new ObjectNotFoundException(id, "History Object");
        }
    }

    @Override
    public void deleteAllOf(FoodItem foodItem){
        historyRepository.findByFoodItem(foodItem).forEach(historyRepository::delete);
    }
}
