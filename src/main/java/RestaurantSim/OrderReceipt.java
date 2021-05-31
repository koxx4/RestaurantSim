package RestaurantSim;

public class OrderReceipt
{
    private final int orderID;

    public OrderReceipt(int orderID)
    {
        this.orderID = orderID;
    }

    public int GetOrderID()
    {
        return orderID;
    }
}
