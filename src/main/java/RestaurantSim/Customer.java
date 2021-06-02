package RestaurantSim;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Customer extends RestaurantGuest
{
    private TickableAction waitForOrderAction;
    private Restaurant currentRestaurant;

    public Customer(String name, int patience)
    { 
        super(name, patience);
        CreateWaitingTask();
    }

    public Customer(int patience)
    {
        super(patience);
        CreateWaitingTask();
    }

    public Customer(String name)
    {
        super(name);
        CreateWaitingTask();
    }

    public Customer()
    {
        super();
        CreateWaitingTask();
    }

    public void RateRestaurant(PreparedOrder preparedOrder)
    {
        float rate = 0;
        switch (preparedOrder.GetQuality())
        {
            case Inedible: rate = SimulationUitilities.GetRandomFloat(); break;
            case Bad: rate = SimulationUitilities.GetRandomFloat() + 1; break;
            case Average: rate = SimulationUitilities.GetRandomFloat() + 2.5f; break;
            case Good: rate = SimulationUitilities.GetRandomFloat() + 3; break;
            case Excelent: rate = SimulationUitilities.GetRandomFloat() + 4; break;
            default: break;
        }
        System.out.println(this + "Rating restaurant for " + rate + " stars");
        currentRestaurant.GiveRate(rate);
    }

    @Override
    public void InteractWithRestaurant(Restaurant restaurant)
    {
        System.out.println("Customer (" + super.GetName() + "): is interacting with restaurant");

        currentRestaurant = restaurant;
        Order composedOrder = ComposeOrder(currentRestaurant.GetMenu());

        System.out.println(this + "I will try to buy " + composedOrder.GetDishes().get(0).getName());

        //For now we assume that client doesn't give tip
        //TODO: client gives sometimes tip

        TryMakeOrder(composedOrder);
    }

    @Override
    public void ReceiveOrder(PreparedOrder preparedOrder)
    {
        System.out.println(this + "Received prepared order " + preparedOrder.GetID());
        //Abort waiting for order
        waitForOrderAction.Abort();

        if (SimulationUitilities.IsGoingToHappen(SimulationSettings.chanceToRateRestaurant))
        {
            this.RateRestaurant(preparedOrder);
        }

        //Guest obviously leaves the queue
        currentRestaurant.RemoveGuestFromQueue(this);
    }

    private Order ComposeOrder(Menu menu)
    {
        Order composedOrder;
        Dish randomDish;
        int numberOfDishes = menu.availableDishes.size();
        int randomDishIndex = SimulationUitilities.GetRandomInt(numberOfDishes);
        Iterator<Dish> availableDishesIterator = menu.availableDishes.elements().asIterator();

        for (int i = 0; i != randomDishIndex; i++)
        {
            //Move iterator to next element
            //don't assign it anywhere
            availableDishesIterator.next();
        }
        //After iterator is on a right position
        randomDish = availableDishesIterator.next();
        List<Dish> dishesInOrder = new ArrayList<>();
        dishesInOrder.add(randomDish);
        composedOrder = new Order(dishesInOrder);

        return composedOrder;
    }

    private void CreateWaitingTask()
    {
        waitForOrderAction = new TickableAction(super.GetName() + " is waiting for order", this.GetPatience());
        System.out.println(this + "Arrived at restaurant. Gonna wait " + super.GetPatience() + " ticks brefore I leave!");

        waitForOrderAction.SetOnFinishCallback( () -> {
            System.out.println(this + "I don't have more time. I'm leaving...");

            //If this code is going to happen with this chance... then it's going to happen
            if (SimulationUitilities.IsGoingToHappen(SimulationSettings.chanceToRateRestaurantImpatience))
            {
                //2.0 is always a rate given when order is not prepared on time
                currentRestaurant.GiveRate(SimulationSettings.rateOnOrderNotPreparedOnTime);
                System.out.println(this + "Also, your restaurant sucks!");
            }

            //Guest obviously leaves the queue
            currentRestaurant.RemoveGuestFromQueue(this);
        });

        SimulationManager.instance.SubscribeAction(waitForOrderAction);
    }

    private void TryMakeOrder(Order composedOrder)
    {
        OrderReceipt orderReceipt =
                currentRestaurant.ReceiveOrder(composedOrder, composedOrder.GetTotalPrice());

        if(orderReceipt != null)
        {
            super.SetOrderReceipt(orderReceipt);
            super.SetWaitingToBeServed(false);
            System.out.println(this + "received order receipt, ID: " + orderReceipt.GetOrderID());
        }
    }

    @Override
    public String toString()
    {
        return "Customer (" + super.GetName() +"): ";
    }

}
