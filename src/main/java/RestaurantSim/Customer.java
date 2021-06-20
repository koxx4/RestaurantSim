package RestaurantSim;

import RestaurantSim.SimulationSystem.*;

import java.util.ArrayList;
import java.util.List;

public class Customer extends RestaurantGuest
{
    private IOrderRater orderRater;
    private TickableAction waitForOrderAction;
    private Restaurant currentRestaurant;
    private final List<TickableAction> tickableActions;
    private boolean shouldBeUnregistered;

    public Customer(String name, int patience, IOrderRater orderRater)
    {
        super(name,patience);
        this.orderRater = orderRater;
        tickableActions = new ArrayList<>();
        this.shouldBeUnregistered = false;
        createWaitingTask();
    }

    public void setOrderRater( IOrderRater orderRater ) {
        this.orderRater = orderRater;
    }

    public void rateRestaurant( PreparedOrder preparedOrder)
    {
        if ( orderRater != null ) {
            float rate = 0;
            rate = orderRater.rateOrder(preparedOrder);

            Simulation.getInstance().print("Rating restaurant for " + rate + " stars", this.toString());
            currentRestaurant.giveRate(rate);
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
    public void interactWithRestaurant( Restaurant restaurant )
    {
        currentRestaurant = restaurant;

        Simulation.getInstance().print("Is interacting with restaurant",
                this.toString());

        Order composedOrder = composeOrder(currentRestaurant.getMenu());

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

    private Order composeOrder(Menu menu)
    {
        if( SimulationUtilities.isGoingToHappen(Simulation.getInstance().getSettings().customerChanceToMakeOwnDish))
            return composeOwnDish(menu);

        return composeOrderFromMenu(menu);
    }

    private Order composeOwnDish( Menu menu ) {
        String dishName = getName() + " custom dish.";
        List<Ingredient> ingredients = new ArrayList<>();

        do {
            ingredients.add(menu.getRandomIngredient());
        }while ( SimulationUtilities.isGoingToHappen(
                Simulation.getInstance().getSettings().customerChanceToAddIngredient));

        return new Order(List.of(new Dish(ingredients, dishName)));
    }

    private Order composeOrderFromMenu(Menu menu) {
        return new Order(List.of(menu.getRandomDish()));
    }

    private void createWaitingTask()
    {
        waitForOrderAction = new TickableAction(super.getName() + " is waiting for order", this.getPatience());

        waitForOrderAction.setOnFinishCallback( () -> {
            Simulation.getInstance().print( "I don't have more time. I'm leaving...", this.toString());

            //If this code is going to happen with this chance... then it's going to happen
            if ( SimulationUtilities.isGoingToHappen(Simulation.getInstance().getSettings().chanceToRateRestaurantImpatience))
            {
                //2.0 is always a rate given when order is not prepared on time
                currentRestaurant.giveRate(Simulation.getInstance().getSettings().rateOnOrderNotPreparedOnTime);
                Simulation.getInstance().print("Also, your restaurant sucks!", this.toString());
            }

            //Guest obviously leaves the queue
            currentRestaurant.removeGuestFromQueue(this);
        });

        tickableActions.add(waitForOrderAction);
    }

    private void createLeaveTask()
    {
        TickableAction leaveTask = new TickableAction(1);
        leaveTask.setOnFinishCallback(
                () -> {
                    currentRestaurant.removeGuestFromQueue(this);
                    shouldBeUnregistered = true;
                });
        tickableActions.add(leaveTask);
    }

    private void tryMakeOrder( Order composedOrder) {
        OrderReceipt orderReceipt =
                currentRestaurant.receiveOrder(composedOrder, composedOrder.GetTotalPrice());

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
