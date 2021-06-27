package RestaurantSim;

import RestaurantSim.SimulationSystem.ITickableActionObject;

/**
 * Objects that extend this class can interact with a {@link Restaurant}. This class
 * mimics the real world person that can come into a restaurant.
 */
public abstract class RestaurantGuest implements ITickableActionObject {
    private final Restaurant targetRestaurant;
    private int patience;
    private String name;
    private boolean waitingToBeServed;
    private OrderReceipt orderReceipt;

    /**
     * Creates the object of this class
     * @param name Name of this RestaurantGuest
     * @param patience Patience of this RestaurantGuest
     * @param targetRestaurant Restaurant object that this RestaurantGuest will interact with in the future
     */
    public RestaurantGuest(String name, int patience, Restaurant targetRestaurant) {
        this.targetRestaurant = targetRestaurant;
        this.name = name;
        this.patience = patience;
        waitingToBeServed = true;
    }

    /**
     * @return Name of this RestaurantGuest
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the name of this RestaurantGuest
     */
    public void setName( String name)
    {
        this.name = name;
    }

    /**
     * @return Patience of this RestaurantGuest
     */
    public int getPatience()
    {
        return this.patience;
    }

    public void setPatience(int value) {
        this.patience = value;
    }

    /**
     * Returns information whether RestaurantGuest is waiting to be served or not
     * @return  True if this RestaurantGuest is waiting to be served, otherwise false
     */
    public boolean isWaitingToBeServed() {
        return this.waitingToBeServed;
    }

    /**
     * Sets the OrderReceipt
     * @param orderReceipt unique OrderReceipt.
     */
    public void setOrderReceipt( OrderReceipt orderReceipt)
    {
        this.orderReceipt = orderReceipt;
    }

    /**
     * Sets information whether Customer is waiting to be served
     * @param value boolean - true if this customer is waiting to be served, false otherwise
     */
    public void setWaitingToBeServed( boolean value)
    {
        this.waitingToBeServed = value;
    }

    /**
     *
     * @return OrderReceipt that this RestaurantGuest has
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

    /**
     * This function is called when the restaurant handles this RestaurantGuest.
     * Each class that extend this class should define its own behaviour
     * while interacting with restaurant.
     */
    abstract public void interactWithRestaurant();

    /**
     * This function should be used to pass an {@link PreparedOrder} to the
     * RestaurantGuest.
     * Each class that extend this class should define its own behaviour
     * while receiving an order.
     * @param preparedOrder
     */
    abstract public void receiveOrder( PreparedOrder preparedOrder);

    /**
     * Returns a restaurant that this RestaurantGuest is linked to
     * @return {@link Restaurant} object that this RestaurantGuest has access to.
     */
    public Restaurant getTargetRestaurant() {
        return targetRestaurant;
    }
}
