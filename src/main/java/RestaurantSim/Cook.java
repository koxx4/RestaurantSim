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
        String actionMessage = "Preparing order by cook " + this.name;
        TickableAction prepareOrderAction = new TickableAction(actionMessage, this.EstimateWorkTime(order));
        prepareOrderAction.onFinishCallback = () -> {
            //TODO: Quality will be dependent on cook skills in the future
            sourceRestaurant.AddPreparedOrder(new PreparedOrder(order.getDishes(), orderID,
                    PreparedOrderQuality.Average));
            this.busy = false;
        };
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
        for (var dish : order.getDishes())
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
