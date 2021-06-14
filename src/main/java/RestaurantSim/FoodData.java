package RestaurantSim;

import java.util.ArrayList;
import java.util.List;

public class FoodData
{
    private final List<Ingredient> ingredientsData = new ArrayList<>();
    private final List<JsonDish> dishesData = new ArrayList<>();

    public FoodData()
    {

    }

    public List<Ingredient> GetIngredientsData()
    {
        return  this.ingredientsData;
    }

    public List<JsonDish> GetDishData()
    {
        return this.dishesData;
    }

}
