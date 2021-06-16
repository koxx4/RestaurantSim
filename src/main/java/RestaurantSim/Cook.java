package RestaurantSim;

import RestaurantSim.SimulationSystem.IOrderQualityDeterminer;
import RestaurantSim.SimulationSystem.ITickableActionObject;
import RestaurantSim.SimulationSystem.TickableAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Cook implements ITickableActionObject
{
    private final IOrderQualityDeterminer orderQualityDeterminer;
    private final String name;
    private boolean busy;
    private final int agility;
    private final int skillLevel;
    private final List<TickableAction> tickableActions;

    public Cook(String name, int agility, int skillLevel, IOrderQualityDeterminer orderQualityDeterminer) {
        this.tickableActions = new ArrayList<>();
        this.name = name;
        this.agility = agility;
        this.skillLevel = skillLevel;
        this.orderQualityDeterminer = orderQualityDeterminer;
    }

    public void receiveOrder( Order order, int orderID, Restaurant sourceRestaurant) {

        String actionMessage = this + "Preparing order "+ orderID;

        TickableAction prepareOrderAction = new TickableAction(actionMessage, this.estimateWorkTime(order));

        System.out.println(actionMessage + ". This will take me " + prepareOrderAction.getDuration() + " ticks.");

        prepareOrderAction.setOnFinishCallback( () -> {
            finishPreparingOrder(order, orderID, sourceRestaurant);
        });
        tickableActions.add(prepareOrderAction);

        this.busy = true;
    }

    private void finishPreparingOrder( Order order, int orderID, @NotNull Restaurant sourceRestaurant) {

        PreparedOrderQuality calculatedQuality =
                orderQualityDeterminer.determineOrderQuality(order, this);

        sourceRestaurant.addPreparedOrder(new PreparedOrder(order, orderID, calculatedQuality));

        this.busy = false;

        System.out.println(this + " I have prepared order, ID: " + orderID + ", quality: " + calculatedQuality);
    }

    public boolean isBusy() {
        return busy;
    }

    public String getName() {
        return name;
    }

    private int estimateWorkTime( Order order) {
        int ticksToPrepareOrder = 0;
        for (var dish : order.GetDishes())
        {
            for (var ingredient : dish.getIngredients())
            {
                ticksToPrepareOrder += ingredient.getTicksToPrepare();
            }
        }

        ticksToPrepareOrder -= this.agility;
        if ( ticksToPrepareOrder <= 0 )
            ticksToPrepareOrder = 1;

        return ticksToPrepareOrder;
    }

    @Override
    public String toString()
    {
        return "Cook (" + this.name +" a["+ agility +"] s["+ skillLevel +"]): ";
    }

    @Override
    public List<TickableAction> getTickableActions() {
        return tickableActions;
    }

    @Override
    public boolean shouldBeUnregistered() {
        return false;
    }

    public int getSkillLevel() {
        return skillLevel;
    }
}
