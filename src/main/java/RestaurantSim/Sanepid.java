package RestaurantSim;

public class Sanepid extends RestaurantGuest {

    public Sanepid(String name)
    {
        super(name);
    }

    public Sanepid()
    {
        super();
    }

    private void EvaluateRestaurantWork()
    {

    }

    @Override
    public void InteractWithRestaurant(Restaurant restaurant)
    {
        EvaluateRestaurantWork();
    }

    @Override
    public void ReceiveOrder(PreparedOrder preparedOrder)
    {

    }
}
