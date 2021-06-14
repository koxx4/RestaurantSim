package RestaurantSim;

import RestaurantSim.SimulationSystem.SimulationUitilities;

import java.util.*;

public class Menu 
{
    private final FoodData loadedFoodData;
    private Map<String,Dish> availableDishes;
    private Map<String,Ingredient> availableIngredients;

    public Menu(FoodData foodData)
    {
        this.loadedFoodData = foodData;
        populateIngredients();
        populateDishes();
    }

    private void populateIngredients()
    {
        this.availableIngredients = new Hashtable<>();
        for (var ingredient: loadedFoodData.GetIngredientsData())
        {
            availableIngredients.put(ingredient.getName(), ingredient);
        }
    }

    private void populateDishes()
    {
        this.availableDishes = new Hashtable<>();
        for (var jsonDish: loadedFoodData.GetDishData())
        {
            List<Ingredient> dishIngredients = new ArrayList<>();

            for (var associatedIngredient: jsonDish.getAssociatedIngredients())
                dishIngredients.add(availableIngredients.get(associatedIngredient));

            Dish newDish = new Dish(dishIngredients, jsonDish.getName());
            availableDishes.put(newDish.getName(), newDish);
        }
    }

    public Map<String, Dish> getAvailableDishes()
    {
        return availableDishes;
    }

    public Map<String, Ingredient> getAvailableIngredients()
    {
        return availableIngredients;
    }

    public Dish getRandomDish() {
        int numberOfDishes = loadedFoodData.GetDishData().size();

        String randomDishName = loadedFoodData.GetDishData()
                .get(SimulationUitilities.getRandomInt(numberOfDishes)).getName();

        return this.availableDishes.get(randomDishName);
    }
}
