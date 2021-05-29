package RestaurantSim;

public abstract class RestaurantGuest {
    private int orderID;
    private String name;
    private boolean waitingToBeServed;

    public RestaurantGuest(String name)
    {
        waitingToBeServed = true;
        this.name = name;
    }

    public RestaurantGuest()
    {
        this("Unnamed Restaurant Guest");
    }

    public String GetName()
    {
        return this.name;
    }

    public void SetName(String name)
    {
        this.name = name;
    }
    public boolean IsWaitingToBeServed()
    {
        return this.waitingToBeServed;
    }
    public int GetOrderID()
    {
        return this.orderID;
    }
    public void SetOrderID(int orderID)
    {
        this.orderID = orderID;
    }
    public void SetWaitingToBeServed(boolean value)
    {
        this.waitingToBeServed = value;
    }

    abstract public void InteractWithRestaurant(Restaurant restaurant);
    abstract public void ReceiveOrder(PreparedOrder preparedOrder);
}
