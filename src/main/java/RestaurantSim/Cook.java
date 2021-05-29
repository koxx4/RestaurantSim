package RestaurantSim;

public class Cook {
    String name;
    boolean busy;

    public Cook() {
    }

    public Cook(String name) {
        this.name = name;
    }
    /*private int EstimateWorkTime()
    {
        return
    }*/
    public void ReceiveOrder(Order order)
    {

    }
    public boolean isBusy()
    {
        if(busy=true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String getName() {
        return name;
    }
}
