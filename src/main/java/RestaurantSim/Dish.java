package RestaurantSim;

import java.util.List;

/**
 * Class that contains all of the information about the dish.
 */
public class Dish 
{
    /**
     * List of ingredients that this dish consists of
     */
    private final List<Ingredient> ingredients;
    /**
     * Name of this dish
     */
    private final String name;

    /**
     *  Creates dish which consist of ingredients and name
     * @param ingredients Ingredients that this dish should consist of
     * @param name Name of the dish.
     */
    public Dish(List<Ingredient> ingredients, String name) {
        this.ingredients = ingredients;
        this.name = name;
    }

    /**
     * Returns price of this dish. Price is a sum of
     * all the ingredients' prices.
     * @return Sum of all the ingredients', that this dish consists of, prices
     */
    public float getPrice() {
        float price = 0;

        for(Ingredient ingredient : ingredients) {
            price += ingredient.getPrice();
        }
        return price;
    }

    /**
     * Returns name of this dish
     * @return name of this dish
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns list of ingredients that this dish consists of.
     * @return List of ingredients.
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return "Dish{" + "ingredients=" + ingredients + ", name='" + name + '\'' + '}';
    }
}
