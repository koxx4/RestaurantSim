package RestaurantSim;

public class Sanepid extends RestaurantGuest {

    public Sanepid()
    {
        super();
    }

    private void evaluateRestaurantWork()
    {

    }

    @Override
    public void interactWithRestaurant( Restaurant restaurant)
    {
        evaluateRestaurantWork();
    }

    @Override
    public void receiveOrder( PreparedOrder preparedOrder)
    {

    }

    @Override
    public void onRestaurantEnter( ) {
        evaluateRestaurantWork();
    }
}
