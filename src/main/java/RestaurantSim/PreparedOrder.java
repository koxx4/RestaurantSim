package RestaurantSim;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Class that resembles the order that is fully finished and can be received by proper {@link RestaurantGuest}
 */
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
    public PreparedOrder(List<Dish> listOfDishes, int id, PreparedOrderQuality quality) {
        this.dishes = listOfDishes;
        this.id = id;
        this.quality = quality;
    }

    /**
     * Creates the object of this class
     * @param sourceOrder Order that this PreparedOrder bases on.
     * @param id Id of this order
     * @param quality Quality of this prepared order
     */
    public PreparedOrder(@NotNull Order sourceOrder, int id, PreparedOrderQuality quality) {
        this(sourceOrder.getDishes(), id, quality);
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
