package RestaurantSim;

import java.util.ArrayList;

public class Order {
     private ArrayList<Dish> dishes= new ArrayList<>();

    public Order(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }
}
