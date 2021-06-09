package RestaurantSim;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Customer extends RestaurantGuest
{
    private IOrderRater orderRater;
    private TickableAction waitForOrderAction;
    private Restaurant currentRestaurant;

    public Customer()
    {
        super();
    }

    public void setOrderRater( IOrderRater orderRater ) {
        this.orderRater = orderRater;
    }

    public void rateRestaurant( PreparedOrder preparedOrder)
    {
        if ( orderRater != null ) {
            float rate = 0;
            rate = orderRater.rateOrder(preparedOrder);

            System.out.println(this + "Rating restaurant for " + rate + " stars");
            currentRestaurant.giveRate(rate);
        }
    }

    @Override
    public void interactWithRestaurant( Restaurant restaurant )
    {
        System.out.println("Customer (" + super.getName() + "): is interacting with restaurant");

        currentRestaurant = restaurant;
        Order composedOrder = composeOrder(currentRestaurant.getMenu());

        System.out.println(this + "I will try to buy " + composedOrder.GetDishes().get(0).getName());

        //For now we assume that client doesn't give tip
        //TODO: client gives sometimes tip

        tryMakeOrder(composedOrder);
    }

    @Override
    public void receiveOrder( PreparedOrder preparedOrder)
    {
        System.out.println(this + "Received prepared order " + preparedOrder.getID());

        if (SimulationUitilities.isGoingToHappen(SimulationManager.instance.getSettings().chanceToRateRestaurant))
        {
            this.rateRestaurant(preparedOrder);
        }

        //Abort waiting for order
        waitForOrderAction.abort();
        //Guest obviously leaves the queue
        createLeaveTask();
    }

    @Override
    public void onRestaurantEnter( ) {
        createWaitingTask();
    }

    private Order composeOrder( Menu menu )
    {
        //For now customer orders only one dish
        Order composedOrder = new Order(List.of(menu.getRandomDish()));

        return composedOrder;
    }

    private void createWaitingTask()
    {
        waitForOrderAction = new TickableAction(super.getName() + " is waiting for order", this.getPatience());
        System.out.println(this + "Arrived at restaurant. Gonna wait " + super.getPatience() + " ticks brefore I leave!");

        waitForOrderAction.setOnFinishCallback( () -> {
            System.out.println(this + "I don't have more time. I'm leaving...");

            //If this code is going to happen with this chance... then it's going to happen
            if (SimulationUitilities.isGoingToHappen(SimulationManager.instance.getSettings().chanceToRateRestaurantImpatience))
            {
                //2.0 is always a rate given when order is not prepared on time
                currentRestaurant.giveRate(SimulationManager.instance.getSettings().rateOnOrderNotPreparedOnTime);
                System.out.println(this + "Also, your restaurant sucks!");
            }

            //Guest obviously leaves the queue
            currentRestaurant.removeGuestFromQueue(this);
        });

        SimulationManager.instance.subscribeAction(waitForOrderAction);
    }

    private void createLeaveTask()
    {
        TickableAction leaveTask = new TickableAction(1);
        leaveTask.setOnFinishCallback(
                () -> currentRestaurant.removeGuestFromQueue(this));
        SimulationManager.instance.subscribeAction(leaveTask);
    }

    private void tryMakeOrder( Order composedOrder)
    {
        OrderReceipt orderReceipt =
                currentRestaurant.receiveOrder(composedOrder, composedOrder.GetTotalPrice());

        if(orderReceipt != null)
        {
            super.setOrderReceipt(orderReceipt);
            super.setWaitingToBeServed(false);
            System.out.println(this + "received order receipt, ID: " + orderReceipt.getOrderID());
        }
    }

    @Override
    public String toString()
    {
        return "Customer (" + super.getName() +"): ";
    }

}
