package RestaurantSim;

import java.util.ArrayList;
import java.util.List;


public class Order
{
     private final List<Dish> dishes;

    public Order(List<Dish> dishes)
    {
        this.dishes = dishes;
    }

    public List<Dish> getDishes() 
    {
        return dishes;
    }
}
