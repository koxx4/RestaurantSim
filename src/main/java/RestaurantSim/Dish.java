package RestaurantSim;

import java.util.List;
import java.util.ArrayList;

public class Dish 
{
    private List<Ingredient> ingredients;
    private String name;

    public Dish(List<Ingredient> ingredients, String name) 
    {
        this.ingredients = ingredients;
        this.name = name;
    }

    public float GetPrice() 
    {
        float price = 0;

        for(Ingredient ingredient : ingredients)
        {
            price += ingredient.getPrice();
        }
        return price;
    }

    public String getName() 
    {
        return name;
    }

    public List<Ingredient> getIngredients() 
    {
        return ingredients;
    }
}
