package RestaurantSim;


import java.util.Dictionary;
import java.util.Hashtable;

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

 }
    private void PopluateDishes()
    {

    }

    public Dictionary<String, Dish> getAvailableDishes() {
        return availableDishes;
    }

    public Dictionary<String, Ingredient> getAvailableIngredients() {
        return availableIngredients;
    }
}
