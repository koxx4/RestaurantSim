package RestaurantSim;

import java.util.List;

public class PreparedOrder
{
    private final List<Dish> dishes;
    private final int id;
    private final PreparedOrderQuality quality;
    private float totalPrice;

    public PreparedOrder(List<Dish> listOfDishes, int id, PreparedOrderQuality quality)
    {
        this.dishes = listOfDishes;
        this.id = id;
        this.quality = quality;
    }

    public PreparedOrderQuality GetQuality()
    {
        return quality;
    }
    public int GetID()
    {
        return this.id;
    }
}
