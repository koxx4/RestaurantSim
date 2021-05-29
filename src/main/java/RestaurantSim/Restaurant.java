package RestaurantSim;

import java.util.*;

public class Restaurant {
    private HashMap<Integer,PreparedOrder> activeOrders= new HashMap<>();
    private List<Cook> cooks = new ArrayList<>();
    private List<RestaurantGuest> restaurantGuestsToBeServed= new ArrayList<>();
    private List<RestaurantGuest> restaurantGuestsWaitingForOrder= new ArrayList<>();
    private List<Float> rates = new ArrayList<>();
    private Menu menu;

    public Restaurant(Menu menu)
    {
          this.menu=menu;
    }

    public void PopulateWithWorkers()
    {

    }

    public int ReceiveOrder(Order order)
    {
        return 1;
    }

    public Menu getMenu() {
        return menu;
    }

    public void AddGuestToQueue(RestaurantGuest restaurantGuest)
    {
        restaurantGuestsToBeServed.add(restaurantGuest);
    }

    public void RemoveGuestFromQueue(RestaurantGuest restaurantGuest)
    {
        restaurantGuestsToBeServed.remove(restaurantGuest);
    }
    public void GiveRate(float rate)

    {
        rates.add(rate);
    }
}
