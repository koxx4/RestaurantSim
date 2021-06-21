package RestaurantSim;

import java.util.List;

public class Dish 
{
    private final List<Ingredient> ingredients;
    private final String name;

    /**
     *  Creates dish which consist of ingredients and name
     * @param ingredients Contains exact ingredients
     * @param name Contains exact name
     */
    public Dish(List<Ingredient> ingredients, String name) 
    {
        this.ingredients = ingredients;
        this.name = name;
    }

    /**
     * Ges price of Dish
     * @return price
     */
    public float GetPrice() 
    {
        float price = 0;

        for(Ingredient ingredient : ingredients)
        {
            price += ingredient.getPrice();
        }
        return price;
    }

    /**
     * Gets name of Dish
     * @return name
     */
    public String getName() 
    {
        return name;
    }

    /**
     * Consists of list of ingredients
     * @return ingredients
     */
    public List<Ingredient> getIngredients() 
    {
        return ingredients;
    }

    @Override
    public String toString() {
        return "Dish{" + "ingredients=" + ingredients + ", name='" + name + '\'' + '}';
    }
}
