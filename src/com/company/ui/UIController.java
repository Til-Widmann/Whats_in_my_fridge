package com.company.ui;

import com.company.database.*;
import java.util.*;
/**
 * @author Til-W
 * @version 1.0
 *
 */
public class UIController {

    public static void main(String[] args) {
        startScanning();
    }

    private static void startScanning() {
        Scanner scanner = new Scanner(System.in);
        RefrigeratorController refrigeratorController = new RefrigeratorController();
        CookingController cookingController = new CookingController();
        System.out.println("Scanner ready");
        while (scanner.hasNext()){
            String command = scanner.next();
;
            switch (command){
                case "addFood":
                    String[] param = new String[3];
                    System.out.println("Name:           ");
                    param[0] = scanner.next();
                    System.out.println("Menge:          ");
                    param[1] = scanner.next();
                    System.out.println("Ablaufdatum:    ");
                    param[2] = scanner.next();
                    refrigeratorController.addFoodItem(param);
                    System.out.println("Done");
                    break;

                case "addRecipe":
                    scanner.nextLine();
                    System.out.println("Name:           ");
                    String name = scanner.nextLine();
                    Map<String, Integer> ingredients = new HashMap<>();
                    System.out.println("Zum beenden der Zutateneingabe \"end\" eingeben");
                    String nameOfIngredient = "a";
                    while (!nameOfIngredient.equals("end")){
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
                    cookingController.addRecipe(name, ingredients);
                    System.out.println("Done");
                    break;

                case "cookable":
                    cookingController.cookableRecipes()
                            .forEach(a -> System.out.println(a));
                    break;
                case "cook":
                    System.out.println("Recipe Name:    ");
                    if (cookingController.cookThisRecipe(scanner.next())){
                        System.out.println("Rezept erfolgreich gekocht");
                    }else{
                        System.out.println("Rezept nicht gefunden");
                    }
                    break;

                case "showAllFood":
                    refrigeratorController.getAllFoodItems(0)
                            .forEach(a -> System.out.println(a));
                    break;

                case "showUsedUpFood":
                    refrigeratorController.getAllFoodItems(1)
                            .forEach(a -> System.out.println(a));
                    break;

                case "showHistory":
                    System.out.println("Food Id:");
                    int id = scanner.nextInt();
                    System.out.println(refrigeratorController.getFoodItemFromId(id));

                    refrigeratorController.getHistoryFromId(id)
                            .forEach(a -> System.out.println(a));
                    break;

                case "expired":
                    refrigeratorController.expiredFood()
                            .forEach(a -> System.out.println(a));
                    break;

                case "expiresIn":
                    System.out.println("In weniger als (tagen):");
                    refrigeratorController.expiresInLessThen(scanner.nextInt())
                            .forEach(a -> System.out.println(a));
                    break;

                default:
                    System.out.println("no valid command");
            }
        }
        scanner.close();
    }
}
