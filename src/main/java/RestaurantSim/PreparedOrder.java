package RestaurantSim;

import java.util.ArrayList;

public class PreparedOrder {
    private ArrayList<Dish> dishes= new ArrayList<>();
    private boolean ready;
    private int quality;
    private float totalPrice;


    private float CalculateTotalPrice()
    {
      return totalPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }
    public boolean isReady()
    {
        return ready;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
