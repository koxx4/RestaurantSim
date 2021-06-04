package RestaurantSim;

import java.util.ArrayList;
import java.util.List;
import java.util.Dictionary;
import java.util.Hashtable;

public class Menu 
{
    private Dictionary<String,Dish> availableDishes;
    private Dictionary<String,Ingredient> availableIngredients;

    public Menu(FoodData foodData)
    {
        PopulateIngredients(foodData);
        PopulateDishes(foodData);
    }

    private void PopulateIngredients(FoodData foodData)
    {
        this.availableIngredients = new Hashtable<>();
        for (var ingredient: foodData.GetIngredientsData())
        {
            availableIngredients.put(ingredient.GetName(), ingredient);
        }
    }

    private void PopulateDishes(FoodData foodData)
    {
        this.availableDishes = new Hashtable<>();
        for (var jsonDish: foodData.GetDishData())
        {
            List<Ingredient> dishIngredients = new ArrayList<>();

            for (var associatedIngredient: jsonDish.GetAssociatedIngredients())
                dishIngredients.add(availableIngredients.get(associatedIngredient));

            Dish newDish = new Dish(dishIngredients, jsonDish.GetName());
            availableDishes.put(newDish.getName(), newDish);
        }
    }

    public Dictionary<String, Dish> GetAvailableDishes()
    {
        return availableDishes;
    }

    public Dictionary<String, Ingredient> GetAvailableIngredients()
    {
        return availableIngredients;
    }
}
