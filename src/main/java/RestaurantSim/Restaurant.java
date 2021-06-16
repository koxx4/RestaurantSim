package RestaurantSim;

import RestaurantSim.SimulationSystem.ITickableActionObject;
import RestaurantSim.SimulationSystem.TickableAction;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Restaurant implements ITickableActionObject {
    private final Stack<PreparedOrder> ordersToPickUp;
    private final List<Cook> cooks;
    private final Queue<RestaurantGuest> restaurantGuests;
    private final List<Float> rates;
    private final Menu menu;
    private final List<TickableAction> tickableActions;
    private int orderCounter;
    private boolean opened;

    public Restaurant(Menu menu, @NotNull List<Cook> cooks)
    {
        this.opened = true;
        this.tickableActions = new ArrayList<>();
        this.cooks = cooks;
        this.menu = menu;
        this.orderCounter = 0;
        this.restaurantGuests = new ArrayDeque<>();
        this.rates = new ArrayList<>();
        this.ordersToPickUp = new Stack<>();
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

    public void close(Object sender){

        if ( sender.getClass() == Sanepid.class ){
            this.opened = false;
        }
    }

    public boolean isOpened(){
        return opened;
    }

    public float getRatesAverage() {
        float sum = 0;

        for(var rate : rates)
            sum += rate;

        return sum / rates.size();
    }

    public int getNumberOfRates(){
        return rates.size();
    }

    private void createGuestHandlingAction()
    {
        TickableAction guestHandling = new TickableAction("Guest handling action", 2, true);
        guestHandling.setOnFinishCallback( () -> {
            if( freeCookAvailable() && !restaurantGuests.isEmpty())
            {
                tryHandleNextRestaurantGuest();
            }
        });
        tickableActions.add(guestHandling);
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
        tickableActions.add(orderPickUpAction);
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

        orderCounter++;
        Cook freeCook = getFreeCook();

        if(freeCook != null)
        {
            System.out.println(this + "Fine, your order is being made");
            printOrderInfo(order, orderCounter);
            freeCook.receiveOrder(order, orderCounter, this);
            return true;
        }

        System.out.println(this + "I'm so sorry, I don't have any cooks left! Try again later.");
        return false;
    }

    private void printOrderInfo(Order order, int id){
        System.out.println("Order info:\nid = " + id + "\n" + order);
    }

    @Override
    public String toString()
    {
        return "Restaurant: ";
    }

    @Override
    public List<TickableAction> getTickableActions() {
        return tickableActions;
    }

    @Override
    public boolean shouldBeUnregistered() {
        return false;
    }
}
