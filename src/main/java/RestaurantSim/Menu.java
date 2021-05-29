package RestaurantSim;


import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Menu {
 public Dictionary<String,Dish> availableDishes= new Hashtable<>();
 public Dictionary<String,Ingredient> availableIngredients= new Hashtable<>();

 public Menu(Dictionary<String,Dish> availableDishes,Dictionary<String,Ingredient> availableIngredients)
 {

     this.availableDishes=availableDishes;
     this.availableIngredients=availableIngredients;
 }
 private void PopluateIngredients()
 {
     Ingredient ingredientHotSauce= new Ingredient(2,"Hot sauce");
     availableIngredients.put(ingredientHotSauce.getName(),ingredientHotSauce);

     Ingredient ingredientMildSauce= new Ingredient(2,"Mild sauce");
     availableIngredients.put(ingredientMildSauce.getName(),ingredientMildSauce);

     Ingredient ingredientChicken= new Ingredient(6,"Chicken");
     availableIngredients.put(ingredientChicken.getName(),ingredientChicken);

     Ingredient ingredientBeef= new Ingredient(7,"Beef");
     availableIngredients.put(ingredientBeef.getName(),ingredientBeef);

     Ingredient ingredientLamb= new Ingredient(8,"Lamb");
     availableIngredients.put(ingredientLamb.getName(),ingredientLamb);

     Ingredient ingredientFrenchFries= new Ingredient(4,"French fries");
     availableIngredients.put(ingredientFrenchFries.getName(),ingredientFrenchFries);

     Ingredient ingredientCheese= new Ingredient(5,"Cheese");
     availableIngredients.put(ingredientCheese.getName(),ingredientCheese);

     Ingredient ingredientPineapple= new Ingredient(5,"Pineapple");
     availableIngredients.put(ingredientPineapple.getName(),ingredientPineapple);


 }
    private void PopluateDishes()
    {
        ArrayList<Ingredient> ingredientsKebab= new ArrayList<>();
        ArrayList<Ingredient> ingredientsPizza= new ArrayList<>();
        ArrayList<Ingredient> ingredientsSalad= new ArrayList<>();

        ingredientsKebab.add(availableIngredients.get("Hot sauce"));
        ingredientsKebab.add(availableIngredients.get("Lamb"));
        ingredientsKebab.add(availableIngredients.get("French fries"));

        Dish dishKebab = new Dish(ingredientsKebab,"Kebab");

        ingredientsPizza.add(availableIngredients.get("Mild sauce"));
        ingredientsPizza.add(availableIngredients.get("Chicken"));
        ingredientsPizza.add(availableIngredients.get("Cheese"));
        ingredientsPizza.add(availableIngredients.get("Pineapple"));

        Dish dishPizza= new Dish(ingredientsPizza,"Pizza");

        ingredientsSalad.add(availableIngredients.get("Mild sauce"));
        ingredientsSalad.add(availableIngredients.get("Lamb"));
        ingredientsSalad.add(availableIngredients.get("Cheese"));

        Dish dishSalad= new Dish(ingredientsSalad,"Salad");

        availableDishes.put(dishKebab.getName(),dishKebab);
        availableDishes.put(dishPizza.getName(),dishPizza);
        availableDishes.put(dishSalad.getName(),dishSalad);


    }

    public Dictionary<String, Dish> getAvailableDishes() {
        return availableDishes;
    }

    public Dictionary<String, Ingredient> getAvailableIngredients() {
        return availableIngredients;
    }
}
