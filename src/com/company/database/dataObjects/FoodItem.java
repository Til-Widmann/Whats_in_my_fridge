package com.company.database.dataObjects;

import java.time.LocalDate;
/**
 * @author Til-W
 * @version 1.0
 *
 */
 public class FoodItem {
    private final int foodItemId;
    private final String name;
    private final int amount;
    private final LocalDate expireDate;


    /**
     * Represents a food item
     * @param foodItemId Id which is autogenerated by the database if object is created before its
     *                   saved in the database the id is to be -1.
     * @param name       Food item name.
     * @param amount     Food item amount in gramms.
     * @param expireDate Food item expire date.
     */
    public FoodItem(int foodItemId, String name, int amount,LocalDate expireDate) {
        this.foodItemId = foodItemId;
        this.name = name;
        this.amount = amount;

        this.expireDate = expireDate;
    }

    public int getFoodItemId() {
        return foodItemId;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }


    public LocalDate getExpireDate() {
        return expireDate;
    }


    public String toString(){
        StringBuilder formatedFoodItem = new StringBuilder();
        formatedFoodItem.append("Id:            " + foodItemId + "\n");
        formatedFoodItem.append("Name:          " + name + "\n");
        formatedFoodItem.append("Amount:        " + amount + "g\n");
        formatedFoodItem.append("ExpireDate:    " + expireDate + "\n");
        formatedFoodItem.append("____________________________________________________ \n");

        return formatedFoodItem.toString();
    }
}


    /*
    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateOfUseString = "not yet used";
        String expiryDateString = "no expiry date specified";
        if (dateOfUse != null)
            dateOfUseString = dateFormat.format(dateOfUse);
        if (expiryDate != null)
            expiryDateString = dateFormat.format(expiryDate);


        return "FoodItem{\n" +
                "ingredient=" + ingredient +
                ",\n dateOfStorage= " + dateFormat.format(dateOfStorage) +
                ",\n dateOfUse=     " + dateOfUseString +
                ",\n expiryDate=    " + expiryDateString +
                '}';
    }
}
*/