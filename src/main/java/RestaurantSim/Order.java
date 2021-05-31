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

    public float GetTotalPrice()
    {
        float totalPrice = 0;
        for (var dish: dishes)
        {
            totalPrice += dish.GetPrice();
        }
        return totalPrice;
    }


    public List<Dish> GetDishes()
    {
        return dishes;
    }
}
