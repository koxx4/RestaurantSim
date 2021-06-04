package RestaurantSim;

import java.util.*;

public class Restaurant {
    private Stack<PreparedOrder> ordersToPickUp;
    private List<Cook> cooks;
    private Queue<RestaurantGuest> restaurantGuests;
    private List<Float> rates;
    private final Menu menu;
    private int orderCounter;

    public Restaurant(Menu menu)
    {
          this.menu = menu;
          this.orderCounter = 0;
          this.restaurantGuests = new ArrayDeque<>();
          this.rates = new ArrayList<>();
          this.cooks = new ArrayList<>();
          this.ordersToPickUp = new Stack<>();
          this.PopulateWithWorkers();
          this.CreateGuestHandlingAction();
          this.CreateOrderPickUpAction();
    }

    public OrderReceipt ReceiveOrder(Order order, float payForOrder)
    {
        if(payForOrder >= order.GetTotalPrice())
        {
            if(TryHandleOrder(order))
                return new OrderReceipt(orderCounter);
        }

        return null;
    }

    public Menu GetMenu()
    {
        return menu;
    }

    public void AddGuestToQueue(RestaurantGuest restaurantGuest)
    {
        restaurantGuests.add(restaurantGuest);
        System.out.println(this + restaurantGuest.GetName() +
                " joined the queue (" + restaurantGuests.size() + ")");
    }

    public void RemoveGuestFromQueue(RestaurantGuest restaurantGuest)
    {
        if(!restaurantGuests.isEmpty())
            restaurantGuests.remove(restaurantGuest);

        System.out.println(this + restaurantGuest.GetName() + " left the queue");
    }

    public void GiveRate(float rate)
    {
        rates.add(rate);
    }

    public void AddPreparedOrder(PreparedOrder preparedOrder)
    {
        this.ordersToPickUp.push(preparedOrder);
    }

    private void PopulateWithWorkers()
    {
        for(int i = 0; i < SimulationManager.instance.GetSettings().numberOfCooks; i++)
        {
            cooks.add(new Cook("Cook " + i));
        }
    }

    private void CreateGuestHandlingAction()
    {
        TickableAction guestHandling = new TickableAction("Guest handling action", 3, true);
        guestHandling.SetOnFinishCallback( () -> {
            if( FreeCookAvailable() && !restaurantGuests.isEmpty())
            {
                TryHandleNextRestaurantGuest();
            }
        });
        SimulationManager.instance.SubscribeAction(guestHandling);
    }

    private void TryHandleNextRestaurantGuest()
    {
        RestaurantGuest restaurantGuestToBeServed = restaurantGuests.poll();
        if(restaurantGuestToBeServed.IsWaitingToBeServed())
        {
            HandleRestaurantGuest(restaurantGuestToBeServed);
        }
        //For now just readd customer
        restaurantGuests.add(restaurantGuestToBeServed);
    }

    private void HandleRestaurantGuest(RestaurantGuest restaurantGuestToBeServed)
    {
        System.out.println(this + "Interacting with " + restaurantGuestToBeServed.GetName());
        restaurantGuestToBeServed.InteractWithRestaurant(this);
    }

    private void CreateOrderPickUpAction()
    {
        TickableAction orderPickUpAction = new TickableAction("Order pick up action", 2, true);
        orderPickUpAction.SetOnFinishCallback( () -> {
            if(!ordersToPickUp.isEmpty())
            {
                this.GivePreparedOrderToGuest();
            }
        });
        SimulationManager.instance.SubscribeAction(orderPickUpAction);
    }

    private void GivePreparedOrderToGuest()
    {
        PreparedOrder preparedOrder = ordersToPickUp.pop();
        RestaurantGuest eligibleGuest = null;
        for (var guest: restaurantGuests)
        {
            if(CustomerIsEligibleForOrder(preparedOrder, guest))
            {
                System.out.println(this + "Giving order to " + guest.GetName());
                guest.ReceiveOrder(preparedOrder);
            }
        }

    }

    private boolean CustomerIsEligibleForOrder(PreparedOrder preparedOrder, RestaurantGuest guest)
    {
        OrderReceipt guestReceipt = guest.GetOrderReceipt();
        return (guestReceipt != null) && (guestReceipt.GetOrderID() == preparedOrder.GetID());
    }

    private Cook GetFreeCook()
    {
        for (var cook : cooks)
        {
            if(!cook.IsBusy())
                return cook;
        }
        return null;
    }

    private boolean FreeCookAvailable()
    {
        for (var cook : cooks)
        {
            if(!cook.IsBusy())
                return true;
        }
        return false;
    }

    private boolean TryHandleOrder(Order order)
    {
        //We essentially treat orderCounter as an id for our orders
        System.out.println(this + "received order. Cost: $" + order.GetTotalPrice());

        orderCounter++;
        Cook freeCook = GetFreeCook();

        if(freeCook != null)
        {
            System.out.println(this + "Fine, your order is being made");
            freeCook.ReceiveOrder(order, orderCounter, this);
            return true;
        }

        System.out.println(this + "I'm so sorry, I don't have any cooks left! Try again later.");
        return false;
    }

    @Override
    public String toString()
    {
        return "Restaurant: ";
    }

}
