package RestaurantSim.SimulationSystem;

import java.util.ArrayList;
import java.util.List;

public class JsonDish
{
    private String name;
    private final List<String> ingredients = new ArrayList<>();

    public String getName()
    {
        return this.name;
    }

    public List<String> getAssociatedIngredients()
    {
        return this.ingredients;
    }

}
