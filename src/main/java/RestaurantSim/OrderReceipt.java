package RestaurantSim;

public class OrderReceipt
{
    private final int orderID;

    /**
     * Creates the object of this class
     * @param orderID Contains the ID of this object
     */
    public OrderReceipt(int orderID)
    {
        this.orderID = orderID;
    }

    /**
     * @return The ID of the Order
     */
    public int getOrderID()
    {
        return orderID;
    }
}
