package RestaurantSim;

import org.jetbrains.annotations.NotNull;

public class Cook
{
    private final String name;
    private boolean busy;
    private int agility;
    private int skillLevel;

    public Cook(String name, int agility, int skillLevel)
    {
        this.name = name;
        this.agility = agility;
        this.skillLevel = skillLevel;
    }

    public Cook(String name)
    {
        this(name, SimulationUitilities
                .getRandomInt(SimulationManager.instance.getSettings().minCookAgility,
                        SimulationManager.instance.getSettings().maxCookAgility),
                SimulationUitilities.getRandomInt(SimulationManager.instance.getSettings().minCookSkill,
                        SimulationManager.instance.getSettings().maxCookSkill));
    }

    public void receiveOrder( Order order, int orderID, Restaurant sourceRestaurant)
    {
        String actionMessage = this + "Preparing order "+ orderID;

        TickableAction prepareOrderAction = new TickableAction(actionMessage, this.estimateWorkTime(order));

        System.out.println(actionMessage + ". This will take me " + prepareOrderAction.getDuration() + " ticks.");

        prepareOrderAction.setOnFinishCallback( () -> {
            finishPreparingOrder(order, orderID, sourceRestaurant);
        });
        SimulationManager.instance.subscribeAction(prepareOrderAction);

        this.busy = true;
    }

    private void finishPreparingOrder( Order order, int orderID, @NotNull Restaurant sourceRestaurant)
    {
        //TODO: Quality will be dependent on cook skills in the future
        System.out.println(this + " I have prepared order, ID: " + orderID);

       // PreparedOrderQuality calculatedQuality = this.calculatePreparedDishQuality();
        sourceRestaurant.addPreparedOrder(new PreparedOrder(order, orderID, PreparedOrderQuality.Average));

        this.busy = false;
    }

    private PreparedOrderQuality calculatePreparedDishQuality()
    {
        PreparedOrderQuality base = PreparedOrderQuality.values()[this.skillLevel];

        //TODO:
        return null;
    }

    public boolean isBusy()
    {
        return busy;
    }

    public String getName()
    {
        return name;
    }

    private int estimateWorkTime( Order order)
    {
        int ticksToPrepareOrder = 0;
        for (var dish : order.GetDishes())
        {
            for (var ingredient : dish.getIngredients())
            {
                ticksToPrepareOrder += ingredient.getTicksToPrepare();
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
