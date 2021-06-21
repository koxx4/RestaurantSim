package RestaurantSim;

import RestaurantSim.SimulationSystem.*;

import java.util.ArrayList;
import java.util.List;

public class Customer extends RestaurantGuest
{
    private final IOrderRater orderRater;
    private TickableAction waitForOrderAction;
    private final List<TickableAction> tickableActions;
    private boolean shouldBeUnregistered;

    /**
     * Creates Customer with name, patience and orderRater
     * @param name Contains exact name
     * @param patience  Contains exact patience
     * @param orderRater  Contains exact orderRater
     * @param targetRestaurant Restaurant that this Guest is bound to
     */
    public Customer(String name, int patience, IOrderRater orderRater, Restaurant targetRestaurant)
    {
        super(name,patience, targetRestaurant);
        this.orderRater = orderRater;
        tickableActions = new ArrayList<>();
        this.shouldBeUnregistered = false;
        createWaitingTask();
    }

    /**
     * Rates restaurant provided when orderRater doesn't equal null
     * @param preparedOrder
     */
    public void rateRestaurant( PreparedOrder preparedOrder)
    {
        if ( orderRater != null ) {
            float rate = 0;
            rate = orderRater.rateOrder(preparedOrder);

            Simulation.getInstance().print("Rating restaurant for " + rate + " stars", this.toString());
            getTargetRestaurant().giveRate(rate);
        }
    }

    @Override
    public List<TickableAction> getTickableActions() {
        return tickableActions;
    }

    @Override
    public boolean shouldBeUnregistered() {
        return shouldBeUnregistered;
    }

    @Override
    public void interactWithRestaurant() {
        Simulation.getInstance().print("Is interacting with restaurant",
                this.toString());

        Order composedOrder = composeOrder(getTargetRestaurant().getMenu());

        Simulation.getInstance().print( "I will try to buy " + composedOrder.GetDishes().get(0).getName(),
                this.toString());

        tryMakeOrder(composedOrder);
    }

    @Override
    public void receiveOrder( PreparedOrder preparedOrder)
    {
        Simulation.getInstance().print("Received prepared order " + preparedOrder.getID(), this.toString());

        if ( SimulationUtilities.isGoingToHappen(Simulation.getInstance().getSettings().chanceToRateRestaurant)) {
            this.rateRestaurant(preparedOrder);
        }

        //Abort waiting for order
        waitForOrderAction.abort();
        //Guest obviously leaves the queue
        createLeaveTask();
    }

    /**
     * Composes order from Memu
     * @param menu Contains list of dishes
     * @return Order
     */
    private Order composeOrder(Menu menu)
    {
        if( SimulationUtilities.isGoingToHappen(Simulation.getInstance().getSettings().customerChanceToMakeOwnDish))
            return composeOwnDish(menu);

        return composeOrderFromMenu(menu);
    }

    /**
     * It allows the customer to compose his own dish
     * @param menu Consists of available dishes
     * @return Order which was prepared by Customer
     */
    private Order composeOwnDish( Menu menu ) {
        String dishName = getName() + " custom dish.";
        List<Ingredient> ingredients = new ArrayList<>();

        do {
            ingredients.add(menu.getRandomIngredient());
        }while ( SimulationUtilities.isGoingToHappen(
                Simulation.getInstance().getSettings().customerChanceToAddIngredient));

        return new Order(List.of(new Dish(ingredients, dishName)));
    }

    /**
     * It allows the customer to compose Order from menu
     * @param menu Contains exact Menu
     * @return Order  from list
     */
    private Order composeOrderFromMenu(Menu menu) {
        return new Order(List.of(menu.getRandomDish()));
    }

    /**
     * Adds wait for order to tickable actions
     */
    private void createWaitingTask()
    {
        waitForOrderAction = new TickableAction(super.getName() + " is waiting for order", this.getPatience());

        waitForOrderAction.setOnFinishCallback( () -> {
            Simulation.getInstance().print( "I don't have more time. I'm leaving...", this.toString());

            //If this code is going to happen with this chance... then it's going to happen
            if ( SimulationUtilities.isGoingToHappen(Simulation.getInstance().getSettings().chanceToRateRestaurantImpatience))
            {
                //2.0 is always a rate given when order is not prepared on time
                getTargetRestaurant().giveRate(Simulation.getInstance().getSettings().rateOnOrderNotPreparedOnTime);
                Simulation.getInstance().print("Also, your restaurant sucks!", this.toString());
            }

            //Guest obviously leaves the queue
            getTargetRestaurant().removeGuestFromQueue(this);
        });

        tickableActions.add(waitForOrderAction);
    }

    /**
     * Adds leave tasks to tickable actions
     */
    private void createLeaveTask()
    {
        TickableAction leaveTask = new TickableAction(1);
        leaveTask.setOnFinishCallback(
                () -> {
                    getTargetRestaurant().removeGuestFromQueue(this);
                    shouldBeUnregistered = true;
                });
        tickableActions.add(leaveTask);
    }

    /**
     * It allows to make order
     * @param composedOrder Contains exact composed order
     */
    private void tryMakeOrder( Order composedOrder) {
        OrderReceipt orderReceipt =
                getTargetRestaurant().receiveOrder(composedOrder, composedOrder.GetTotalPrice());

        if(orderReceipt != null)
        {
            super.setOrderReceipt(orderReceipt);
            super.setWaitingToBeServed(false);
            Simulation.getInstance().print("received order receipt, ID: " + orderReceipt.getOrderID(), this.toString());
        }
    }

    @Override
    public String toString() {
        return "Customer (" + super.getName() +"): ";
    }

}
