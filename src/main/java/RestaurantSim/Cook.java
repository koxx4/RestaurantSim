package RestaurantSim;

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
                .GetRandomInt(SimulationSettings.minCookAgility, SimulationSettings.maxCookAgility));
    }
    public void ReceiveOrder(Order order, int orderID, Restaurant sourceRestaurant)
    {
        String actionMessage = "Cook: Preparing order "+ orderID +" by cook " + this.name;

        TickableAction prepareOrderAction = new TickableAction(actionMessage, this.EstimateWorkTime(order));

        System.out.println(actionMessage + ". This will take me " + prepareOrderAction.GetDuration() + " ticks.");

        prepareOrderAction.onFinishCallback = () -> {
            //TODO: Quality will be dependent on cook skills in the future
            System.out.println("Cook " + this.name + " : prepared order " + orderID);
            sourceRestaurant.AddPreparedOrder(new PreparedOrder(order.GetDishes(), orderID,
                    PreparedOrderQuality.Average));
            this.busy = false;
        };
        SimulationManager.instance.SubscribeAction(prepareOrderAction);

        this.busy = true;
    }
    public boolean isBusy()
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
}
