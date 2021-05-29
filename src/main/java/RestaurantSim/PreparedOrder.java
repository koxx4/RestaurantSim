package RestaurantSim;

import java.util.ArrayList;
import java.util.List;

public class PreparedOrder
{
    private List<Dish> dishes = new ArrayList<>();
    private boolean ready;
    private DishQuality quality;
    private float totalPrice;

    public float getTotalPrice()
    {
        return totalPrice;
    }
    public boolean isReady()
    {
        return ready;
    }

    public DishQuality getQuality()
    {
        return quality;
    }

    public void setQuality(DishQuality quality)
    {
        this.quality = quality;
    }
}
