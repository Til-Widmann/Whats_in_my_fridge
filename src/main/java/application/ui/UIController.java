//package main.java.ui;
//
//import database.recipe.RecipeDataService;
//import database.services.HistoryService;
//import database.model.History;
//import database.model.Recipe;
//
//import java.util.*;
//
//import static database.recipe.RecipeCookingEventService.*;
//import static database.services.FoodItemService.*;
//
///**
// * @author Til-W
// * @version 1.0
// *
// */
//public class UIController {
//
//    private static final Scanner scanner = new Scanner(System.in);
//
//    public static void main(String[] args) {
//        startScanning();
//    }
//
//    private static void startScanning() {
//        System.out.println("Scanner ready");
//        while (scanner.hasNext()){
//            String command = scanner.next();
//;
//            switch (command){
//                case "addFood":
//                    addFoodComand();
//                    break;
//
//                case "addRecipe":
//                    addRecipeCommand();
//                    break;
//
//                case "cookable":
//                    allCookableRecipes().forEach(System.out::println);
//                    break;
//                case "cook":
//                    System.out.println("Recipe Name:    ");
//                    cookRecipe(new Recipe(scanner.next()));
//                    System.out.println("Rezept erfolgreich gekocht");
//                    break;
//                case "showAllFood":
//                    System.out.println("Foood in Fridge:");
//                    getAllFoodInFridge().forEach(System.out::println);
//                    System.out.println("Food that is used up:");
//                    getAllUsedUp().forEach(System.out::println);
//                    break;
//
//                case "showUsedUpFood":
//                    getAllUsedUp().forEach(System.out::println);
//                    break;
//
//                case "showHistory":
//                    System.out.println("Food Id:");
//                    showHistoryCommand();
//                    break;
//
//                case "expired":
//                    expiredFood().forEach(System.out::println);
//                    break;
//
//                case "expiresIn":
//                    System.out.println("In weniger als (tagen):");
//                    expiresInLessThen(scanner.nextInt()).forEach(System.out::println);
//                    break;
//
//                default:
//                    System.out.println("no valid command");
//            }
//        }
//        scanner.close();
//    }
//
//    private static void showHistoryCommand() {
//        int id = scanner.nextInt();
//        System.out.println(HistoryService.getFoodItemFromId(id));
//        ArrayList<History> histories = HistoryService.getHistoryFromFoodItemId(id);
//        System.out.println(histories);
//    }
//
//    private static void addRecipeCommand() {
//        scanner.nextLine();
//        System.out.println("Name:           ");
//        String name = scanner.nextLine();
//        System.out.println("Zum beenden der Zutateneingabe \"end\" eingeben");
//        HashMap<String, String> ingredients = readIngredientsForAddRecipe();
//        RecipeDataService.addRecipe(name, ingredients);
//        System.out.println("Done");
//    }
//
//    private static HashMap<String, String> readIngredientsForAddRecipe() {
//        HashMap<String, String> ingredients = new HashMap<>();
//        String nameOfIngredient;
//        while (true){
//            System.out.println("Name der Zutat: ");
//            nameOfIngredient = scanner.nextLine();
//            if (nameOfIngredient.equals("end")) break;
//            System.out.println("Menge in Gramm:");
//            String amount = scanner.next();
//            if (amount.equals("end")) break;
//            ingredients.put(nameOfIngredient, amount);
//            scanner.nextLine();
//        }
//        return ingredients;
//    }
//
//    private static void addFoodComand() {
//        String[] param = new String[3];
//        System.out.println("Name:           ");
//        param[0] = scanner.next();
//        System.out.println("Menge:          ");
//        param[1] = scanner.next();
//        System.out.println("Ablaufdatum:    ");
//        param[2] = scanner.next();
//        addFood(param);
//        System.out.println("Done");
//    }
//}
