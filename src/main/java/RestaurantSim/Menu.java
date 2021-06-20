package RestaurantSim;

import RestaurantSim.SimulationSystem.FoodData;
import RestaurantSim.SimulationSystem.SimulationUtilities;

import java.util.*;

public class Menu 
{
    private Map<String,Dish> availableDishes;
    private Map<String,Ingredient> availableIngredients;

    public Menu(FoodData foodData) {
        populateIngredients(foodData);
        populateDishes(foodData);
    }

    private void populateIngredients(FoodData foodData) {
        this.availableIngredients = new Hashtable<>();
        for (var ingredient: foodData.getIngredientsData()) {
            availableIngredients.put(ingredient.getName(), ingredient);
        }
    }

    private void populateDishes(FoodData foodData) {
        this.availableDishes = new Hashtable<>();
        for (var jsonDish: foodData.getDishData()) {
            List<Ingredient> dishIngredients = new ArrayList<>();

            for (var associatedIngredient: jsonDish.getAssociatedIngredients())
                dishIngredients.add(availableIngredients.get(associatedIngredient));

            Dish newDish = new Dish(dishIngredients, jsonDish.getName());
            availableDishes.put(newDish.getName(), newDish);
        }
    }

    public Map<String, Dish> getAvailableDishes() {
        return availableDishes;
    }

    public Map<String, Ingredient> getAvailableIngredients() {
        return availableIngredients;
    }

    public Dish getRandomDish() {
        var dishIterator =  availableDishes.values().iterator();
        int randomIndex = SimulationUtilities.getRandomInt(availableDishes.values().size());

        for(int i = 0; i < randomIndex; i++){
            dishIterator.next();
        }

        return dishIterator.next();
    }

    public Ingredient getRandomIngredient() {
        var ingredIterator =  availableIngredients.values().iterator();
        int randomIndex = SimulationUtilities.getRandomInt(availableIngredients.values().size());

        for(int i = 0; i < randomIndex; i++){
            ingredIterator.next();
        }

        return ingredIterator.next();
    }
}
