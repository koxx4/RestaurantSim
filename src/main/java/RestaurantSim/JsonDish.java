package RestaurantSim;

import java.util.ArrayList;
import java.util.List;

public class JsonDish
{
    private String name;
    private List<String> ingredients = new ArrayList<>();

    public JsonDish()
    {

    }

    public String GetName()
    {
        return this.name;
    }

    public List<String> GetAssociatedIngredients()
    {
        return this.ingredients;
    }

}
