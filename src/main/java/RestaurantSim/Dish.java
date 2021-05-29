package RestaurantSim;

import java.util.List;
import java.util.ArrayList;

public class Dish {
    private List<Ingredient> ingredients= new ArrayList<>();
    private String name;

    public Dish(List<Ingredient> ingredients, String name) {
        this.ingredients = ingredients;
        this.name = name;
    }

    public float GetPrice() {
        float price = 0;

        for(Ingredient ingred : ingredients) {
            price += ingred.getPrice();
        }
        return price;
    }

//    public Dish(List<Ingredient> ingredients) {
//        this.ingredients = ingredients;
//    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
