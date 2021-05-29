package RestaurantSim;

import java.util.ArrayList;

public class Dish
{
    private ArrayList<Ingredient> ingredients= new ArrayList<>();
    private String name;

    public Dish(ArrayList<Ingredient> ingredients, String name) {
        this.ingredients = ingredients;
        this.name = name;
    }

    public Dish(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
}
