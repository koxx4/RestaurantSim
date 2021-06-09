package RestaurantSim;

import org.jetbrains.annotations.NotNull;

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

    public PreparedOrder(@NotNull Order sourceOrder, int id, PreparedOrderQuality quality)
    {
        this(sourceOrder.GetDishes(), id, quality);
    }

    public PreparedOrderQuality getQuality()
    {
        return quality;
    }
    public int getID()
    {
        return this.id;
    }
}
