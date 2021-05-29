package RestaurantSim;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

        System.out.println("Order "+order.getDishes()+" is ready to be picked up");

    }
    public boolean isBusy()
    {
        return busy;
    }

    public String getName() {
        return name;
    }
}
