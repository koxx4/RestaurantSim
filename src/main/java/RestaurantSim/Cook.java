package RestaurantSim;

import org.jetbrains.annotations.NotNull;

public class Cook
{
    private final String name;
    private boolean busy;
    private int agility;

    public Cook(String name, int agility)
    {
        this.name = name;
    }

    public Cook(String name)
    {
        this(name, SimulationUitilities
                .GetRandomInt(SimulationManager.instance.GetSettings().minCookAgility,
                        SimulationManager.instance.GetSettings().maxCookAgility));
    }

    public void ReceiveOrder(Order order, int orderID, Restaurant sourceRestaurant)
    {
        String actionMessage = this + "Preparing order "+ orderID;

        TickableAction prepareOrderAction = new TickableAction(actionMessage, this.EstimateWorkTime(order));

        System.out.println(actionMessage + ". This will take me " + prepareOrderAction.GetDuration() + " ticks.");

        prepareOrderAction.SetOnFinishCallback( () -> {
            FinishPreparingOrder(order, orderID, sourceRestaurant);
        });
        SimulationManager.instance.SubscribeAction(prepareOrderAction);

        this.busy = true;
    }

    private void FinishPreparingOrder(Order order, int orderID, @NotNull Restaurant sourceRestaurant)
    {
        //TODO: Quality will be dependent on cook skills in the future
        System.out.println(this + " I have prepared order, ID: " + orderID);

        sourceRestaurant.AddPreparedOrder(new PreparedOrder(order, orderID, PreparedOrderQuality.Average));

        this.busy = false;
    }

    public boolean IsBusy()
    {
        return busy;
    }

    public String getName()
    {
        return name;
    }

    private int EstimateWorkTime(Order order)
    {
        int ticksToPrepareOrder = 0;
        for (var dish : order.GetDishes())
        {
            for (var ingredient : dish.getIngredients())
            {
                ticksToPrepareOrder += ingredient.GetTicksToPrepare();
            }
        }
        ticksToPrepareOrder -= this.agility;
        return ticksToPrepareOrder;
    }

    @Override
    public String toString()
    {
        return "Cook (" + this.name +"): ";
    }
}
