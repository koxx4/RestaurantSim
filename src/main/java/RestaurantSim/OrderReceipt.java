package RestaurantSim;

/**
 * This class represents a order receipt. It is usually used by the {@link Restaurant} - returns
 * object of this class to indicate that the request for order fall through successfully.
 * This class also contains unique id that should match with one of the objects
 * of type {@link PreparedOrder}.
 */
public class OrderReceipt
{
    /**
     * Unique ID for the order that will be prepared
     */
    private final int orderID;

    /**
     * Creates the object of this class
     * @param orderID Unique ID for the order that will be prepared in the future
     */
    public OrderReceipt(int orderID)
    {
        this.orderID = orderID;
    }

    /**
     * @return Integer of value of ID that this receipt was given.
     */
    public int getOrderID()
    {
        return orderID;
    }
}
