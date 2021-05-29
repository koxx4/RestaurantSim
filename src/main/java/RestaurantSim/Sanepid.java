package RestaurantSim;

public class Sanepid extends RestaurantGuest {

    public Sanepid(String name,int orderID,boolean isWaitingToBeServed) {
        super(name,orderID,isWaitingToBeServed);
    }
    private void EvaluateRestaurantWork()
    {

    }


    @Override
    public void InteractWithRestaurant(Restaurant restaurant) {
        super.InteractWithRestaurant(restaurant);
    }

    @Override
    public boolean isWaitingToBeServed() {
        return super.isWaitingToBeServed();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public int getOrderID() {
        return super.getOrderID();
    }

    @Override
    public void receiveOrder(Order order) {
        super.receiveOrder(order);
    }
}
