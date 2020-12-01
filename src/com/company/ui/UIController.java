package com.company.ui;

import com.company.database.*;
import java.util.*;

import static com.company.database.CookingController.*;
import static com.company.database.RefrigeratorController.*;

/**
 * @author Til-W
 * @version 1.0
 *
 */
public class UIController {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        startScanning();
    }

    public enum CommandEnum{

    }
    private static void startScanning() {
        System.out.println("Scanner ready");
        while (scanner.hasNext()){
            String command = scanner.next();
;
            switch (command){
                case "addFood":
                    addFoodComand();
                    break;

                case "addRecipe":
                    addRecipeCommand();
                    break;

                case "cookable":
                    cookableRecipes().forEach(System.out::println);
                    break;
                case "cook":
                    System.out.println("Recipe Name:    ");
                    if (cookThisRecipe(scanner.next())){
                        System.out.println("Rezept erfolgreich gekocht");
                    }else{
                        System.out.println("Rezept nicht gefunden");
                    }
                    break;
                case "showAllFood":
                    Objects.requireNonNull(getAllFoodItems(SelectFoodItem.EXISTING))
                            .forEach(System.out::println);
                    break;

                case "showUsedUpFood":
                    Objects.requireNonNull(getAllFoodItems(SelectFoodItem.USED_UP))
                            .forEach(System.out::println);
                    break;

                case "showHistory":
                    System.out.println("Food Id:");
                    showHistoryCommand();
                    break;

                case "expired":
                    expiredFood().forEach(System.out::println);
                    break;

                case "expiresIn":
                    System.out.println("In weniger als (tagen):");
                    expiresInLessThen(scanner.nextInt()).forEach(System.out::println);
                    break;

                default:
                    System.out.println("no valid command");
            }
        }
        scanner.close();
    }

    private static void showHistoryCommand() {
        int id = scanner.nextInt();
        System.out.println(getFoodItemFromId(id));
        getHistoryFromId(id).forEach(System.out::println);
    }

    private static void addRecipeCommand() {
        scanner.nextLine();
        System.out.println("Name:           ");
        String name = scanner.nextLine();
        System.out.println("Zum beenden der Zutateneingabe \"end\" eingeben");
        Map<String, Integer> ingredients = readIngredientsForAddRecipe();
        addRecipe(name, ingredients);
        System.out.println("Done");
    }

    private static Map<String, Integer> readIngredientsForAddRecipe() {
        Map<String, Integer> ingredients = new HashMap<>();
        String nameOfIngredient = "a";
        while (true){
            System.out.println("Name der Zutat: ");
            nameOfIngredient = scanner.nextLine();
            if (nameOfIngredient.equals("end"))
                break;
            System.out.println("Menge in Gramm:");
            String amount = scanner.next();
            if (amount.equals("end"))
                break;
            ingredients.put(nameOfIngredient, Integer.parseInt(amount));
            scanner.nextLine();
        }
        return ingredients;
    }

    private static void addFoodComand() {
        String[] param = new String[3];
        System.out.println("Name:           ");
        param[0] = scanner.next();
        System.out.println("Menge:          ");
        param[1] = scanner.next();
        System.out.println("Ablaufdatum:    ");
        param[2] = scanner.next();
        addFoodItem(param);
        System.out.println("Done");
    }
}
