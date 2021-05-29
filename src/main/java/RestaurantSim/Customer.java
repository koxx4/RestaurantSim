package RestaurantSim;

import java.util.Dictionary;
import java.util.Random;

public class Customer extends RestaurantGuest{

    public Customer(String name,int orderID,boolean isWaitingToBeServed) {
        super(name,orderID,isWaitingToBeServed);
    }
    public Order ComposeOrder(Dictionary<String,Dish> availableDishes, Order order)
    {
        return order;
    }
    public Order RateRestaurant(Order order)
    {
        return order;
    }

    @Override
    public void InteractWithRestaurant(Restaurant restaurant) {
        super.InteractWithRestaurant(restaurant);
    }

    @Override
    public boolean isWaitingToBeServed() {
        return super.isWaitingToBeServed();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public int getOrderID() {
        return super.getOrderID();
    }

    @Override
    public void receiveOrder(Order order) {
        super.receiveOrder(order);
    }
}
