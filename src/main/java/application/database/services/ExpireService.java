package application.database.services;

import application.database.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public interface ExpireService {
    List<FoodItem> expiredFood();

    ArrayList<FoodItem> expiresInLessThen(int days);
}
