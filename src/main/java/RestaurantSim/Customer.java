package RestaurantSim;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Customer extends RestaurantGuest
{
    private int patience;
    private TickableAction waitForOrderAction;
    private Restaurant currentRestaurant;

    public Customer(String name)
    { 
        super(name);
        RandomizePatience();
    }

    public Customer()
    {
        this("Unnamed restaurant guest");
    }

    public void RateRestaurant(PreparedOrder preparedOrder)
    {
        float rate = 0;
        switch (preparedOrder.getQuality())
        {
            case Inedible: rate = SimulationUitilities.GetRandomFloat(); break;
            case Bad: rate = SimulationUitilities.GetRandomFloat() + 1; break;
            case Average: rate = SimulationUitilities.GetRandomFloat() + 2.5f; break;
            case Good: rate = SimulationUitilities.GetRandomFloat() + 3; break;
            case Excelent: rate = SimulationUitilities.GetRandomFloat() + 4; break;
            default: break;
        }
        currentRestaurant.GiveRate(rate);
    }

    @Override
    public void InteractWithRestaurant(Restaurant restaurant)
    {
        currentRestaurant = restaurant;
        Order composedOrder = ComposeOrder(currentRestaurant.getMenu());

        int orderID = currentRestaurant.ReceiveOrder(composedOrder);
        super.SetOrderID(orderID);
        super.SetWaitingToBeServed(false);

        waitForOrderAction = new TickableAction(super.GetName() + " is waiting for order", patience);

        waitForOrderAction.onFinishCallback = () -> {
            float drawnChance = SimulationUitilities.GetRandomFloat();

            //If drawn chance is in bounds of chances to rate restaurant
            if (drawnChance <= SimulationSettings.chanceToRateRestaurant)
            {
                //2.0 is always a rate given when order is not prepared on time
                currentRestaurant.GiveRate(SimulationSettings.rateOnOrderNotPreparedOnTime);
            }

            //Guest obviously leaves the queue
            currentRestaurant.RemoveGuestFromQueue(this);
        };

        SimulationManager.instance.SubscribeAction(waitForOrderAction);

    }

    @Override
    public void ReceiveOrder(PreparedOrder preparedOrder)
    {
        //Abort waiting for order
        waitForOrderAction.AbortAction();
        float drawnChance = SimulationUitilities.GetRandomFloat();

        if (drawnChance <= SimulationSettings.chanceToRateRestaurant)
        {
            this.RateRestaurant(preparedOrder);
        }
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

    private void RandomizePatience()
    {
        this.patience = SimulationUitilities
                .GetRandomInt(SimulationSettings.minClientPatience, SimulationSettings.maxClientPatience);
    }

}
