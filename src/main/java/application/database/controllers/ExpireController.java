package application.database.controllers;

import application.database.model.FoodItem;
import application.database.services.expire.ExpireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class ExpireController {

    @Autowired
    ExpireService expireService;

    @GetMapping("/expiredFood")
    public List<FoodItem> expiredFood(){
        return expireService.expiredFood();
    }

    @GetMapping("/expiredFood/{days}")
    public List<FoodItem> expiresInLessThen(@PathVariable int days){
        return expireService.expiresInLessThen(days);
    }
}