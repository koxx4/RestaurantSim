package RestaurantSim;

public abstract class RestaurantGuest implements ITickableActionObject {
    private int patience;
    private String name;
    private boolean waitingToBeServed;
    private OrderReceipt orderReceipt;

    public RestaurantGuest()
    {
        waitingToBeServed = true;
        this.name = "Unnamed restaurantGuest";
    }

    public String getName()
    {
        return this.name;
    }
    public void setName( String name)
    {
        this.name = name;
    }
    public int getPatience()
    {
        return this.patience;
    }
    public void setPatience(int value) {
        this.patience = value;
    }
    public boolean isWaitingToBeServed()
    {
        return this.waitingToBeServed;
    }
    public void setOrderReceipt( OrderReceipt orderReceipt)
    {
        this.orderReceipt = orderReceipt;
    }
    public void setWaitingToBeServed( boolean value)
    {
        this.waitingToBeServed = value;
    }
    public OrderReceipt getOrderReceipt()
    {
        return this.orderReceipt;
    }
    @Override
    public String toString()
    {
        return "RestaurantGuest (" + this.name +"): ";
    }
    abstract public void interactWithRestaurant( Restaurant restaurant);
    abstract public void receiveOrder( PreparedOrder preparedOrder);
}
