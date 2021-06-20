package RestaurantSim;

import RestaurantSim.SimulationSystem.ITickableActionObject;
import RestaurantSim.SimulationSystem.Simulation;
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

    /**
     * Creates the object of this class
     * @param menu
     * @param cooks
     */
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
        this.createCooksStatusPrintAction();
    }

    /**
     *
     * @param order
     * @param payForOrder
     * @return
     */
    public OrderReceipt receiveOrder( Order order, float payForOrder) {
        if(payForOrder >= order.GetTotalPrice())
        {
            if( tryHandleOrder(order))
                return new OrderReceipt(orderCounter);
        }
        return null;
    }

    /**
     * @return The menu for the Customer object
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Adds the new Guest to the queue
     * @param restaurantGuest Contains exact Guest
     */
    public void addGuestToQueue( RestaurantGuest restaurantGuest) {
        restaurantGuests.add(restaurantGuest);
        Simulation.getInstance().print(restaurantGuest.getName() +
                " joined the queue (" + restaurantGuests.size() + ")", this.toString());
        printRestaurantStatus();
    }

    /**
     * Removes Guest from the queue
     * @param restaurantGuest Contains exact Guest
     */
    public void removeGuestFromQueue( RestaurantGuest restaurantGuest) {
        if(!restaurantGuests.isEmpty())
            restaurantGuests.remove(restaurantGuest);

        Simulation.getInstance().print(restaurantGuest.getName() + " left the queue", this.toString());
        printRestaurantStatus();
    }

    /**
     * Gives Restaurant the rate
     * @param rate Contains rate value
     */
    public void giveRate( float rate) {
        rates.add(rate);
    }

    /**
     * Adds the Order to prepared
     * @param preparedOrder Contains information about exact order
     */
    public void addPreparedOrder( PreparedOrder preparedOrder) {
        this.ordersToPickUp.push(preparedOrder);
    }

    /**
     * Appears when Restaurant is going to be closed
     * @param sender
     */
    public void close(Object sender){

        if (sender instanceof Sanepid){
            this.opened = false;
            Simulation.getInstance().print("RESTAURANT WILL BE CLOSED!");
        }
    }

    /**
     * @return True when is opened and false when not
     */
    public boolean isOpened(){
        return opened;
    }

    /**
     * @return The average of all rates given
     */
    public float getRatesAverage() {
        float sum = 0;

        for(var rate : rates)
            sum += rate;

        return sum / rates.size();
    }

    /**
     * @return The number of all rates given
     */
    public int getNumberOfRates(){
        return rates.size();
    }

    /**
     * Creates an action for Guest object
     */
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
        if(restaurantGuestToBeServed.isWaitingToBeServed()) {
            handleRestaurantGuest(restaurantGuestToBeServed);
        }
        //For now just read customer
        restaurantGuests.add(restaurantGuestToBeServed);
    }

    private void handleRestaurantGuest( RestaurantGuest restaurantGuestToBeServed) {
        Simulation.getInstance().print("Interacting with " + restaurantGuestToBeServed.getName(), this.toString());
        restaurantGuestToBeServed.interactWithRestaurant();
    }

    /**
     * Creates the action that lets the Guest collect their Order
     */
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

    /**
     * Gives prepared order to the Guest
     */
    private void givePreparedOrderToGuest()
    {
        PreparedOrder preparedOrder = ordersToPickUp.pop();
        RestaurantGuest eligibleGuest = null;
        for (var guest: restaurantGuests)
        {
            if( customerIsEligibleForOrder(preparedOrder, guest))
            {
                Simulation.getInstance().print("Giving order to " + guest.getName(), this.toString());
                guest.receiveOrder(preparedOrder);
            }
        }

    }

    private boolean customerIsEligibleForOrder( PreparedOrder preparedOrder, RestaurantGuest guest)
    {
        OrderReceipt guestReceipt = guest.getOrderReceipt();
        return (guestReceipt != null) && (guestReceipt.getOrderID() == preparedOrder.getID());
    }

    /**
     * @return Not busy Cook
     */
    private Cook getFreeCook()
    {
        for (var cook : cooks)
        {
            if(!cook.isBusy())
                return cook;
        }
        return null;
    }

    /**
     * @return Available Cook
     */
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
            Simulation.getInstance().print("Fine, your order is being made", this.toString());
            printOrderInfo(order, orderCounter);
            freeCook.receiveOrder(order, orderCounter, this);
            return true;
        }

        Simulation.getInstance().print( "I'm so sorry, I don't have any cooks left! Try again later.",
                this.toString());

        return false;
    }

    /**
     * Prints all information about exact Dish
     * @param order Contains exact Order
     * @param id Contains exact Order ID
     */
    private void printOrderInfo(Order order, int id){
        Simulation.getInstance().print("Order info:\nid = " + id + "\n" + order, this.toString());
    }

    @Override
    public String toString() {
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

    /**
     * Prints information about the Restaurant status
     */
    private void printRestaurantStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        for ( var guest : this.restaurantGuests ){
            if(guest instanceof Customer) {
                stringBuilder.append("|C");
                if(guest.isWaitingToBeServed())
                    stringBuilder.append("(W)|");
                else
                    stringBuilder.append("|");
            }
            else if(guest instanceof Sanepid)
                    stringBuilder.append("|S|");
        }
        stringBuilder.append("---> Restaurant\n");
        stringBuilder.append("Average rates: " + getRatesAverage() + "\u2605");
        Simulation.getInstance().printToRestaurantStatus(stringBuilder.toString());
    }

    /**
     * Prints information about the Cook status
     */
    private void printCooksStatus(){
        StringBuilder stringBuilder = new StringBuilder();
        for ( var cook : cooks ){
            stringBuilder.append("|" + cook.toString() + "");
            stringBuilder.append(", busy: " + cook.isBusy());
            stringBuilder.append("|\n");
        }
        Simulation.getInstance().printToCooksStatus(stringBuilder.toString());
    }

    /**
     * Creates the action that lets print information about Cook
     */
    private void createCooksStatusPrintAction() {
        TickableAction action = new TickableAction(1, true);
        action.setOnFinishCallback(this::printCooksStatus);
        tickableActions.add(action);
    }

}
