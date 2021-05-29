package RestaurantSim;

import java.util.*;

public class Restaurant {
    private HashMap<Integer,PreparedOrder> activeOrders= new HashMap<>();
    private ArrayList<Cook> cooks = new ArrayList<>();
    private ArrayList<RestaurantGuest> restaurantGuestsToBeServed= new ArrayList<>();
    private ArrayList<RestaurantGuest> restaurantGuestsWaitingForOrder= new ArrayList<>();
    private ArrayList<Double>rates= new ArrayList<>();
    private Menu menu;

    public Restaurant(Menu menu)
    {
          this.menu=menu;
    }
    public void PopulateWithWorkers()
    {
           Cook cook1= new Cook("Cooker Dave");
           cooks.add(1,cook1);

           Cook cook2= new Cook("Cooker Josh");
           cooks.add(2,cook2);

           Cook cook3= new Cook("Cooker Sam");
           cooks.add(3,cook3);


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
    public void RemoveGuestToQueue(RestaurantGuest restaurantGuest)
    {
        restaurantGuestsToBeServed.remove(restaurantGuest);
    }
    public void GiveRate(Double rate)
    {
        rates.add(rate);
    }


}
