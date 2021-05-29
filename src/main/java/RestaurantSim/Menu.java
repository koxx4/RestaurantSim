package RestaurantSim;

import java.util.List;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Menu 
{
    public Dictionary<String,Dish> availableDishes= new Hashtable<>();
    public Dictionary<String,Ingredient> availableIngredients= new Hashtable<>();

    public Menu() 
    {
        PopulateIngredients();
        PopulateDishes();
    }
    private void PopulateIngredients()
    {
        Ingredient ingredientHotSauce = new Ingredient(2,"HotSauce");
        availableIngredients.put(ingredientHotSauce.getName(), ingredientHotSauce);

        Ingredient ingredientMildSauce = new Ingredient(2,"MildSauce");
        availableIngredients.put(ingredientMildSauce.getName(), ingredientMildSauce);

        Ingredient ingredientChicken = new Ingredient(6,"Chicken");
        availableIngredients.put(ingredientChicken.getName(), ingredientChicken);

        Ingredient ingredientBeef = new Ingredient(7,"Beef");
        availableIngredients.put(ingredientBeef.getName(), ingredientBeef);

        Ingredient ingredientLamb = new Ingredient(8,"Lamb");
        availableIngredients.put(ingredientLamb.getName(), ingredientLamb);

        Ingredient ingredientFrenchFries = new Ingredient(4,"FrenchFries");
        availableIngredients.put(ingredientFrenchFries.getName(), ingredientFrenchFries);

        Ingredient ingredientCheese = new Ingredient(5,"Cheese");
        availableIngredients.put(ingredientCheese.getName(), ingredientCheese);

        Ingredient ingredientPineapple = new Ingredient(5,"Pineapple");
        availableIngredients.put(ingredientPineapple.getName(), ingredientPineapple);
    }

    private void PopulateDishes() 
    {
        List<Ingredient> ingredientsKebab = new ArrayList<>();
        List<Ingredient> ingredientsPizza = new ArrayList<>();
        List<Ingredient> ingredientsSalad = new ArrayList<>();

        ingredientsKebab.add(availableIngredients.get("HotSauce"));
        ingredientsKebab.add(availableIngredients.get("Lamb"));
        ingredientsKebab.add(availableIngredients.get("FrenchFries"));

        Dish dishKebab = new Dish(ingredientsKebab, "Kebab");

        ingredientsPizza.add(availableIngredients.get("MildSauce"));
        ingredientsPizza.add(availableIngredients.get("Chicken"));
        ingredientsPizza.add(availableIngredients.get("Cheese"));
        ingredientsPizza.add(availableIngredients.get("Pineapple"));

        Dish dishPizza = new Dish(ingredientsPizza, "Pizza");

        ingredientsSalad.add(availableIngredients.get("MildSauce"));
        ingredientsSalad.add(availableIngredients.get("Lamb"));
        ingredientsSalad.add(availableIngredients.get("Cheese"));

        Dish dishSalad = new Dish(ingredientsSalad, "Salad");

        availableDishes.put(dishKebab.getName(), dishKebab);
        availableDishes.put(dishPizza.getName(), dishPizza);
        availableDishes.put(dishSalad.getName(), dishSalad);
    }

    public Dictionary<String, Dish> getAvailableDishes()
    {
        return availableDishes;
    }

    public Dictionary<String, Ingredient> getAvailableIngredients()
    {
        return availableIngredients;
    }
}
