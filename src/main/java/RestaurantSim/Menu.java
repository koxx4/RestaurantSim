package RestaurantSim;

import RestaurantSim.SimulationSystem.FoodData;
import RestaurantSim.SimulationSystem.SimulationUtilities;

import java.util.*;

/**
 * CLass that contains loaded data of all available dishes and ingredients
 */
public class Menu 
{
    private Map<String,Dish> availableDishes;
    private Map<String,Ingredient> availableIngredients;

    /**
     * Creates the object of this class
     * @param foodData Loaded food data that this menu will use
     */
    public Menu(FoodData foodData) {
        populateIngredients(foodData);
        populateDishes(foodData);
    }

    /**
     * Populates {@link this#availableIngredients} map with ingredients
     * from provided {@link FoodData}.
     * @param foodData Loaded food data
     */
    private void populateIngredients(FoodData foodData) {
        this.availableIngredients = new Hashtable<>();
        for (var ingredient: foodData.getIngredientsData()) {
            availableIngredients.put(ingredient.getName(), ingredient);
        }
    }

    /**
     * Populates {@link this#availableDishes} map with dishes
     * from provided {@link FoodData}.
     * @param foodData Loaded food data
     */
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

    /**
     * Returns map of loaded and available dishes that this menu has access to.
     * @return Map with values of {@link Dish} and keys that are names of the corresponding dishes
     */
    public Map<String, Dish> getAvailableDishes() {
        return availableDishes;
    }

    /**
     * Returns map of loaded and available ingredients that this menu has access to.
     * @return Map with values of {@link Ingredient} and keys that are names of the corresponding ingredients
     */
    public Map<String, Ingredient> getAvailableIngredients() {
        return availableIngredients;
    }

    /**
     * Returns random {@link Dish} that is menu has.
     * @return Random {@link Dish}
     */
    public Dish getRandomDish() {
        var dishIterator =  availableDishes.values().iterator();
        int randomIndex = SimulationUtilities.getRandomInt(availableDishes.values().size());

        for(int i = 0; i < randomIndex; i++){
            dishIterator.next();
        }

        return dishIterator.next();
    }

    /**
     * Returns random {@link Ingredient} that is menu has.
     * @return Random {@link Ingredient}
     */
    public Ingredient getRandomIngredient() {
        var ingredIterator =  availableIngredients.values().iterator();
        int randomIndex = SimulationUtilities.getRandomInt(availableIngredients.values().size());

        for(int i = 0; i < randomIndex; i++){
            ingredIterator.next();
        }

        return ingredIterator.next();
    }
}
