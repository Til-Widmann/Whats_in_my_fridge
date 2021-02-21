package application.database.services.expire;

import application.database.model.FoodItem;
import application.database.services.foodItem.FoodItemService;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ExpireServiceImpl implements ExpireService {
    @Setter
    FoodItemService foodItemService;

    @Override
    public List<FoodItem> expiredFood() {
        return expiresInLessThen(0);
    }

    @Override
    public ArrayList<FoodItem> expiresInLessThen(int days){
        LocalDate maxDate = LocalDate.now().plusDays(days);
        return foodItemService.getAll()
                .stream()
                .filter(a -> a.getExpireDate().isBefore(maxDate))
                .sorted(Comparator.comparing(FoodItem::getExpireDate))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
