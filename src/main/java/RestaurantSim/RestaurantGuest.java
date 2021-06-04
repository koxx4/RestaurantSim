package RestaurantSim;

public abstract class RestaurantGuest {
    private final int patience;
    private String name;
    private boolean waitingToBeServed;
    private OrderReceipt orderReceipt;

    public RestaurantGuest(String name, int patience)
    {
        waitingToBeServed = true;
        this.name = name;
        this.patience = patience;
    }

    public RestaurantGuest(String name)
    {
        this(name, SimulationUitilities.
                GetRandomInt(SimulationManager.instance.GetSettings().minClientPatience,
                        SimulationManager.instance.GetSettings().maxClientPatience));
    }
    public RestaurantGuest(int patience)
    {
        this("Unnamed Restaurant Guest", patience);
    }

    public RestaurantGuest()
    {
        this("Unnamed Restaurant Guest", SimulationUitilities.
            GetRandomInt(SimulationManager.instance.GetSettings().minClientPatience,
                    SimulationManager.instance.GetSettings().maxClientPatience));
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
    public void SetOrderReceipt(OrderReceipt orderReceipt)
    {
        this.orderReceipt = orderReceipt;
    }
    public void SetWaitingToBeServed(boolean value)
    {
        this.waitingToBeServed = value;
    }
    public OrderReceipt GetOrderReceipt()
    {
        return this.orderReceipt;
    }
    @Override
    public String toString()
    {
        return "RestaurantGuest (" + this.name +"): ";
    }
    abstract public void InteractWithRestaurant(Restaurant restaurant);
    abstract public void ReceiveOrder(PreparedOrder preparedOrder);
}
