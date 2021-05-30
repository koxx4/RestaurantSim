package RestaurantSim;

public abstract class RestaurantGuest {
    private int orderID;
    private final int patience;
    private String name;
    private boolean waitingToBeServed;

    public RestaurantGuest(String name, int patience)
    {
        waitingToBeServed = true;
        this.name = name;
        this.patience = patience;
    }

    public RestaurantGuest(String name)
    {
        this(name, SimulationUitilities.
                GetRandomInt(SimulationSettings.minClientPatience, SimulationSettings.maxClientPatience));
    }
    public RestaurantGuest(int patience)
    {
        this("Unnamed Restaurant Guest", patience);
    }

    public RestaurantGuest()
    {
        this("Unnamed Restaurant Guest", SimulationUitilities.
            GetRandomInt(SimulationSettings.minClientPatience, SimulationSettings.maxClientPatience));
    }

    public String GetName()
    {
        return this.name;
    }
    public void SetName(String name)
    {
        this.name = name;
    }
    public int GetPatience()
    {
        return this.patience;
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
