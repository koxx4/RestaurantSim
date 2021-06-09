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
          this.populateWithWorkers();
          this.createGuestHandlingAction();
          this.createOrderPickUpAction();
    }

    public OrderReceipt receiveOrder( Order order, float payForOrder)
    {
        if(payForOrder >= order.GetTotalPrice())
        {
            if( tryHandleOrder(order))
                return new OrderReceipt(orderCounter);
        }

        return null;
    }

    public Menu getMenu()
    {
        return menu;
    }

    public void addGuestToQueue( RestaurantGuest restaurantGuest)
    {
        restaurantGuests.add(restaurantGuest);
        System.out.println(this + restaurantGuest.getName() +
                " joined the queue (" + restaurantGuests.size() + ")");
    }

    public void removeGuestFromQueue( RestaurantGuest restaurantGuest)
    {
        if(!restaurantGuests.isEmpty())
            restaurantGuests.remove(restaurantGuest);

        System.out.println(this + restaurantGuest.getName() + " left the queue");
    }

    public void giveRate( float rate)
    {
        rates.add(rate);
    }

    public void addPreparedOrder( PreparedOrder preparedOrder)
    {
        this.ordersToPickUp.push(preparedOrder);
    }

    private void populateWithWorkers()
    {
        for( int i = 0; i < SimulationManager.instance.getSettings().numberOfCooks; i++)
        {
            cooks.add(new Cook(SimulationManager.instance.GetRandomFullName()));
        }
    }

    private void createGuestHandlingAction()
    {
        TickableAction guestHandling = new TickableAction("Guest handling action", 3, true);
        guestHandling.setOnFinishCallback( () -> {
            if( freeCookAvailable() && !restaurantGuests.isEmpty())
            {
                tryHandleNextRestaurantGuest();
            }
        });
        SimulationManager.instance.subscribeAction(guestHandling);
    }

    private void tryHandleNextRestaurantGuest()
    {
        RestaurantGuest restaurantGuestToBeServed = restaurantGuests.poll();
        if(restaurantGuestToBeServed.isWaitingToBeServed())
        {
            handleRestaurantGuest(restaurantGuestToBeServed);
        }
        //For now just readd customer
        restaurantGuests.add(restaurantGuestToBeServed);
    }

    private void handleRestaurantGuest( RestaurantGuest restaurantGuestToBeServed)
    {
        System.out.println(this + "Interacting with " + restaurantGuestToBeServed.getName());
        restaurantGuestToBeServed.interactWithRestaurant(this);
    }

    private void createOrderPickUpAction()
    {
        TickableAction orderPickUpAction = new TickableAction("Order pick up action", 2, true);
        orderPickUpAction.setOnFinishCallback( () -> {
            if(!ordersToPickUp.isEmpty())
            {
                this.givePreparedOrderToGuest();
            }
        });
        SimulationManager.instance.subscribeAction(orderPickUpAction);
    }

    private void givePreparedOrderToGuest()
    {
        PreparedOrder preparedOrder = ordersToPickUp.pop();
        RestaurantGuest eligibleGuest = null;
        for (var guest: restaurantGuests)
        {
            if( customerIsEligibleForOrder(preparedOrder, guest))
            {
                System.out.println(this + "Giving order to " + guest.getName());
                guest.receiveOrder(preparedOrder);
            }
        }

    }

    private boolean customerIsEligibleForOrder( PreparedOrder preparedOrder, RestaurantGuest guest)
    {
        OrderReceipt guestReceipt = guest.getOrderReceipt();
        return (guestReceipt != null) && (guestReceipt.getOrderID() == preparedOrder.getID());
    }

    private Cook getFreeCook()
    {
        for (var cook : cooks)
        {
            if(!cook.isBusy())
                return cook;
        }
        return null;
    }

    private boolean freeCookAvailable()
    {
        for (var cook : cooks)
        {
            if(!cook.isBusy())
                return true;
        }
        return false;
    }

    private boolean tryHandleOrder( Order order)
    {
        //We essentially treat orderCounter as an id for our orders
        System.out.println(this + "received order. Cost: $" + order.GetTotalPrice());

        orderCounter++;
        Cook freeCook = getFreeCook();

        if(freeCook != null)
        {
            System.out.println(this + "Fine, your order is being made");
            freeCook.receiveOrder(order, orderCounter, this);
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
