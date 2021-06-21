package RestaurantSim.SimulationSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Dish data that is deserialized from a json file.
 */
public class JsonDish {
    private String name;
    private final List<String> ingredients = new ArrayList<>();

    /**
     * Returns name of this dish
     * @return String with name of this dish
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns list of ingredients that this dish should include
     * @return List of strings of names of ingredients that this dish include
     */
    public List<String> getAssociatedIngredients() {
        return this.ingredients;
    }

}
