package RestaurantSim;

import java.util.*;

public class Restaurant {
    private Stack<PreparedOrder> ordersToPickUp;
    private List<Cook> cooks;
    private Queue<RestaurantGuest> restaurantGuests;
    private List<Float> rates;
    private final Menu menu;
    private int orderCounter;
    private boolean busyWithGuest;

    public Restaurant(Menu menu)
    {
          this.menu = menu;
          this.orderCounter = 0;
          this.busyWithGuest = false;
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
            //We essentially treat orderCounter as an id for our orders
            System.out.println("Restaruant: received order. Cost: " + order.GetTotalPrice());
            orderCounter++;
            GetFreeCook().ReceiveOrder(order, orderCounter, this);
            return new OrderReceipt(orderCounter);
        }

        return null;
    }

    public Menu getMenu()
    {
        return menu;
    }

    public void AddGuestToQueue(RestaurantGuest restaurantGuest)
    {
        restaurantGuests.add(restaurantGuest);
        System.out.println("Restaurant: " + restaurantGuest.GetName() +
                " joined the queue ("+restaurantGuests.size()+")");
    }

    public void RemoveGuestFromQueue(RestaurantGuest restaurantGuest)
    {
        if(!restaurantGuests.isEmpty())
            restaurantGuests.remove(restaurantGuest);

        System.out.println("Restaurant: " + restaurantGuest.GetName() + " left the queue");
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
        for(int i = 0; i < SimulationSettings.numberOfCooks; i++)
        {
            cooks.add(new Cook("Cooker " + i));
        }
    }

    private void CreateGuestHandlingAction()
    {
        TickableAction guestHandling = new TickableAction("Guest handling action", 3, true);
        guestHandling.onFinishCallback = () -> {
            Cook freeCook = GetFreeCook();
            if(freeCook != null && !restaurantGuests.isEmpty())
            {
                RestaurantGuest restaurantGuestToBeServed = restaurantGuests.poll();
                if(restaurantGuestToBeServed.IsWaitingToBeServed())
                {
                    System.out.println("Restaruant: Interacting with " + restaurantGuestToBeServed.GetName());
                    restaurantGuestToBeServed.InteractWithRestaurant(this);
                }
                //For now just readd customer
                restaurantGuests.add(restaurantGuestToBeServed);
            }
        };
        SimulationManager.instance.SubscribeAction(guestHandling);
    }

    private void CreateOrderPickUpAction()
    {
        TickableAction orderPickUpAction = new TickableAction("Order pick up action", 2, true);
        orderPickUpAction.onFinishCallback = () -> {
            if(!ordersToPickUp.isEmpty())
            {
                this.GivePreparedOrderToGuest();
            }
        };
        SimulationManager.instance.SubscribeAction(orderPickUpAction);
    }

    private void GivePreparedOrderToGuest()
    {
        PreparedOrder preparedOrder = ordersToPickUp.pop();
        for (var guest: restaurantGuests)
        {
            OrderReceipt guestReceipt = guest.GetOrderReceipt();
            if( (guestReceipt != null) && (guestReceipt.GetOrderID() == preparedOrder.GetID()) )
            {
                System.out.println("Restaruant: Giving order to " + guest.GetName());
                guest.ReceiveOrder(preparedOrder);
            }
        }

    }

    private Cook GetFreeCook()
    {
        for (var cook : cooks)
        {
            if(!cook.isBusy())
                return cook;
        }
        return null;
    }

}
