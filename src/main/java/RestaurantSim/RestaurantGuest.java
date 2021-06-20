package RestaurantSim;

import RestaurantSim.SimulationSystem.ITickableActionObject;

public abstract class RestaurantGuest implements ITickableActionObject {
    private final Restaurant targetRestaurant;
    private int patience;
    private String name;
    private boolean waitingToBeServed;
    private OrderReceipt orderReceipt;

    /**
     * Creates the object of this class
     * @param name Contains the name of Cook object
     * @param patience Contains random patience value
     * @param targetRestaurant Restaurant object that this object interacts with
     */
    public RestaurantGuest(String name, int patience, Restaurant targetRestaurant) {
        this.targetRestaurant = targetRestaurant;
        this.name = name;
        this.patience = patience;
        waitingToBeServed = true;
    }

    /**
     * @return Name of the RestaurantGuest
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Set the name of the RestaurantGuest
     */
    public void setName( String name)
    {
        this.name = name;
    }

    /**
     * @return Patience of the RestaurantGuest
     */
    public int getPatience()
    {
        return this.patience;
    }

    public void setPatience(int value) {
        this.patience = value;
    }

    /**
     * @return Information whether Customer is waiting or not
     */
    public boolean isWaitingToBeServed()
    {
        return this.waitingToBeServed;
    }

    /**
     * Sets the Order ID
     * @param orderReceipt Specific information about the Order ID
     */
    public void setOrderReceipt( OrderReceipt orderReceipt)
    {
        this.orderReceipt = orderReceipt;
    }

    /**
     * Sets information whether Customer is still waiting
     * @param value
     */
    public void setWaitingToBeServed( boolean value)
    {
        this.waitingToBeServed = value;
    }

    /**
     * @return Order ID
     */
    public OrderReceipt getOrderReceipt()
    {
        return this.orderReceipt;
    }

    @Override
    public String toString()
    {
        return "RestaurantGuest (" + this.name +"): ";
    }
    abstract public void interactWithRestaurant();
    abstract public void receiveOrder( PreparedOrder preparedOrder);

    public Restaurant getTargetRestaurant() {
        return targetRestaurant;
    }
}
