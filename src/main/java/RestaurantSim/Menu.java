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
        Ingredient ingredientHotSauce = new Ingredient("HotSauce",2, 2);
        availableIngredients.put(ingredientHotSauce.GetName(), ingredientHotSauce);

        Ingredient ingredientMildSauce = new Ingredient("MildSauce",2, 2);
        availableIngredients.put(ingredientMildSauce.GetName(), ingredientMildSauce);

        Ingredient ingredientChicken = new Ingredient("Chicken",6, 10);
        availableIngredients.put(ingredientChicken.GetName(), ingredientChicken);

        Ingredient ingredientBeef = new Ingredient("Beef",7, 12);
        availableIngredients.put(ingredientBeef.GetName(), ingredientBeef);

        Ingredient ingredientLamb = new Ingredient("Lamb",8, 13);
        availableIngredients.put(ingredientLamb.GetName(), ingredientLamb);

        Ingredient ingredientFrenchFries = new Ingredient("FrenchFries",4, 6);
        availableIngredients.put(ingredientFrenchFries.GetName(), ingredientFrenchFries);

        Ingredient ingredientCheese = new Ingredient("Cheese",5, 2);
        availableIngredients.put(ingredientCheese.GetName(), ingredientCheese);

        Ingredient ingredientPineapple = new Ingredient("Pineapple",5, 5);
        availableIngredients.put(ingredientPineapple.GetName(), ingredientPineapple);
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
