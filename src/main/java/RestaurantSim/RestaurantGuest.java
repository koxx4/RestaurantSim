package RestaurantSim;

public abstract class RestaurantGuest {
    private int orderID;
    private String name;
    private boolean waitingToBeServed;

    public RestaurantGuest() {
    }

    public RestaurantGuest(String name,int orderID,boolean waitingToBeServed )
    {
        this.name=name;
        this.orderID=orderID;
        this.waitingToBeServed=waitingToBeServed;

    }

    public RestaurantGuest(String name) {
        this.name=name;
    }

    public void InteractWithRestaurant(Restaurant restaurant)
    {

    }
    public boolean isWaitingToBeServed()
    {
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderID() {
        return orderID;
    }
    public void receiveOrder(Order order)
    {

    }
}
