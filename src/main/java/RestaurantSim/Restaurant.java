package RestaurantSim;

import RestaurantSim.SimulationSystem.ITickableActionObject;
import RestaurantSim.SimulationSystem.Simulation;
import RestaurantSim.SimulationSystem.TickableAction;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A class that resembles a Restaurant. It is the heart of the simulation. Restaurant has cooks that make orders requested by {@link RestaurantGuest}s,
 * returns prepared orders to proper guests, interacts with guests that are waiting in its queue, also manages them.
 * Restaurant can be rated by any {@link RestaurantGuest} and closed by object of {@link Sanepid} type.
 */
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
     * @param menu Menu that this restaurant will be using
     */
    public Restaurant(Menu menu)
    {
        this.opened = true;
        this.tickableActions = new ArrayList<>();
        this.cooks = new ArrayList<>();
        this.menu = menu;
        this.orderCounter = 0;
        this.restaurantGuests = new ArrayDeque<>();
        this.rates = new ArrayList<>();
        this.ordersToPickUp = new Stack<>();
        this.createCooksStatusPrintAction();
        this.createGuestHandlingAction();
        this.createOrderPickUpAction();
    }

    /**
     * Adds cooks to this restaurant.
     * @param cooks List of {@link Cook}
     */
    public void addCooks(List<Cook> cooks){
        this.cooks.addAll(cooks);
    }

    /**
     * Receives an order request and if restaurant is able to handle it
     * this function will return an {@link OrderReceipt} object.
     * @param order Order to make
     * @param payForOrder Total payment for the requested order. If it's less than
     *                    required sum of prices of all ingredients this function will not
     *                    process the request and return <b>null</b>.
     * @return {@link OrderReceipt} or <b>null</b> if request couldn't be processed.
     */
    public OrderReceipt receiveOrderRequest( Order order, float payForOrder) {
        if(payForOrder >= order.getTotalPrice()) {
            if( isAbleToHandleOrder(order))
                return new OrderReceipt(orderCounter);
        }
        return null;
    }

    /**
     * @return {@link Menu} object that this restaurant has.
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Adds the new Guest to the queue
     * @param restaurantGuest guest to add to the queue
     */
    public void addGuestToQueue( RestaurantGuest restaurantGuest) {
        restaurantGuests.add(restaurantGuest);
        Simulation.getInstance().print(restaurantGuest.getName() +
                " joined the queue (" + restaurantGuests.size() + ")", this.toString());
        printRestaurantStatus();
    }

    /**
     * Removes Guest from the queue
     * @param target guest to remove
     */
    public void removeGuestFromQueue( RestaurantGuest target) {
        if(!restaurantGuests.isEmpty())
            restaurantGuests.remove(target);

        Simulation.getInstance().print(target.getName() + " left the queue", this.toString());
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
     * Adds the prepared order to internal restaurant's stack.
     * @param preparedOrder Prepared order to be added.
     */
    public void addPreparedOrder( PreparedOrder preparedOrder) {
        this.ordersToPickUp.push(preparedOrder);
    }

    /**
     * Closes the restaurant provided that the object requesting it is of a {@link Sanepid} type.
     * @param sender The object that wants to close the restaurant
     */
    public void close(Object sender){
        if (sender instanceof Sanepid){
            this.opened = false;
            Simulation.getInstance().print("RESTAURANT WILL BE CLOSED!");
        }
    }

    /**
     * @return True when restaurant is opened and false when it's not.
     */
    public boolean isOpened(){
        return opened;
    }

    /**
     * @return The average of all given rates
     */
    public float getRatesAverage() {
        float sum = 0;

        for(var rate : rates)
            sum += rate;

        return sum / rates.size();
    }

    /**
     * @return The number of all given rates
     */
    public int getNumberOfRates(){
        return rates.size();
    }

    /**
     * Creates an action for handling guests that are currently
     * waiting in restaurant's queue.
     */
    private void createGuestHandlingAction() {
        TickableAction guestHandling = new TickableAction("Guest handling action", 2, true);
        guestHandling.setOnFinishCallback( () -> {
            if( freeCookAvailable() && !restaurantGuests.isEmpty()) {
                tryHandleNextRestaurantGuest();
            }
        });
        tickableActions.add(guestHandling);
    }

    /**
     * Retrieves next guest from RestaurantGuests queue and handles him
     * provided that he is waiting to be served. Handled guest is then
     * readed at the end of the queue
     */
    private void tryHandleNextRestaurantGuest()
    {
        RestaurantGuest restaurantGuestToBeServed = restaurantGuests.poll();
        if(restaurantGuestToBeServed.isWaitingToBeServed()) {
            handleRestaurantGuest(restaurantGuestToBeServed);
        }
        //For now just read customer
        restaurantGuests.add(restaurantGuestToBeServed);
    }

    /**
     * Interacts with given guest.
     * @param restaurantGuestToBeServed Guest object that this restaurant should interact with.
     */
    private void handleRestaurantGuest( RestaurantGuest restaurantGuestToBeServed) {
        Simulation.getInstance().print("Interacting with " + restaurantGuestToBeServed.getName(), this.toString());
        restaurantGuestToBeServed.interactWithRestaurant();
    }

    /**
     * Creates the action that lets the restaurant return PreparedOrders to the guests
     * that made the request.
     */
    private void createOrderPickUpAction()
    {
        TickableAction orderPickUpAction = new TickableAction("Order pick up action", 2, true);
        orderPickUpAction.setOnFinishCallback( () -> {
            if(!ordersToPickUp.isEmpty()) {
                this.givePreparedOrderToGuest();
            }
        });
        tickableActions.add(orderPickUpAction);
    }

    /**
     * Gives prepared order to the Guest that has the matching
     * {@link OrderReceipt}
     */
    private void givePreparedOrderToGuest()
    {
        PreparedOrder preparedOrder = ordersToPickUp.pop();
        RestaurantGuest eligibleGuest = null;
        for (var guest: restaurantGuests) {
            if( customerIsEligibleForOrder(preparedOrder, guest)) {
                Simulation.getInstance().print("Giving order to " + guest.getName(), this.toString());
                guest.receiveOrder(preparedOrder);
            }
        }

    }

    /**
     * Checks if a given guest is eligible for receiving given preparedOrder object
     * @param preparedOrder Order that guest should receive
     * @param guest Guest to be checked
     * @return False if guest is not eligible, true if it is
     */
    private boolean customerIsEligibleForOrder( PreparedOrder preparedOrder, RestaurantGuest guest)
    {
        OrderReceipt guestReceipt = guest.getOrderReceipt();
        return (guestReceipt != null) && (guestReceipt.getOrderID() == preparedOrder.getID());
    }

    /**
     * Returns first found cook that is not busy at the moment that this function is called.
     * @return Random optional cook that is not busy.
     * @see Cook#isBusy()
     */
    private Optional<Cook> getFreeCook() {
        return cooks.stream().filter(cook -> !cook.isBusy()).findAny();
    }

    /**
     * Checks if any of the cook that this restaurant has is not busy at the moment that this function
     * is called.
     * @return True if any any cook is not busy, false otherwise.
     */
    private boolean freeCookAvailable() {
       return cooks.stream().anyMatch(cook -> !cook.isBusy());
    }

    private boolean isAbleToHandleOrder( Order order)
    {
        //We essentially treat orderCounter as an id for our orders
        orderCounter++;
        Optional<Cook> freeCook = getFreeCook();

        if( freeCook.isPresent() ) {
            Simulation.getInstance().print("Fine, your order is being made", this.toString());
            printOrderInfo(order, orderCounter);
            freeCook.get().makeOrder(order, orderCounter);
            return true;
        }

        Simulation.getInstance().print( "I'm so sorry, I don't have any cooks left! Try again later.",
                this.toString());

        return false;
    }

    /**
     * Prints all information about Order
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
     * Prints information about cooks status
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
     * Creates the action that lets print information about cooks periodically
     */
    private void createCooksStatusPrintAction() {
        TickableAction action = new TickableAction(1, true);
        action.setOnFinishCallback(this::printCooksStatus);
        tickableActions.add(action);
    }

}
