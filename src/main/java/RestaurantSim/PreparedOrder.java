package RestaurantSim;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PreparedOrder
{
    private final List<Dish> dishes;
    private final int id;
    private final PreparedOrderQuality quality;
    private float totalPrice;

    /**
     * Creates the object of this class
     * @param listOfDishes Contains list of Dishes
     * @param id Contains the Order ID
     * @param quality Contains the quality of the Order
     */
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

    /**
     * @return The quality of the Order
     */
    public PreparedOrderQuality getQuality()
    {
        return quality;
    }

    /**
     * @return The ID of the Order
     */
    public int getID()
    {
        return this.id;
    }
}
