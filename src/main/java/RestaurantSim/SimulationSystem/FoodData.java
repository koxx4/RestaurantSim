package RestaurantSim.SimulationSystem;

import RestaurantSim.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that wraps all the data related to food
 */
public class FoodData
{
    /**
     * List of all loaded ingredients
     */
    private final List<Ingredient> ingredientsData = new ArrayList<>();
    /*
     * List of all dishes loaded from json configuration file
     */
    private final List<JsonDish> dishesData = new ArrayList<>();

    /**
     * Returns all ingredients data.
     * @return Ingredients data
     */
    public List<Ingredient> getIngredientsData() {
        return  this.ingredientsData;
    }

    /**
     * Returns all dishes data
     * @return Dishes data
     */
    public List<JsonDish> getDishData() {
        return this.dishesData;
    }
}
