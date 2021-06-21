package RestaurantSim;

import RestaurantSim.SimulationSystem.*;
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

    /**
     * Creates the object of this class
     * @param name Contains the name of Cook object
     * @param agility Contains the agility of Cook object
     * @param skillLevel Contains the skill level of Cook object
     * @param orderQualityDeterminer Implementation of IOrderQualityDeterminer
     */
    public Cook(String name, int agility, int skillLevel, IOrderQualityDeterminer orderQualityDeterminer) {
        this.tickableActions = new ArrayList<>();
        this.name = name;
        this.agility = agility;
        this.skillLevel = skillLevel;
        this.orderQualityDeterminer = orderQualityDeterminer;
    }

    /**
     * Receives the order and tries to prepare it
     * @param order Contains exact Order
     * @param orderID Contains exact Order ID
     * @param sourceRestaurant
     */
    public void receiveOrder( Order order, int orderID, Restaurant sourceRestaurant) {

        String actionMessage = "Preparing order "+ orderID;

        TickableAction prepareOrderAction = new TickableAction(actionMessage, this.estimateWorkTime(order));

        Simulation.getInstance().print(actionMessage + ". This will take me " + prepareOrderAction.getDuration()
                        + " ticks.", this.toString());

        prepareOrderAction.setOnFinishCallback( () -> {
            finishPreparingOrder(order, orderID, sourceRestaurant);
        });
        tickableActions.add(prepareOrderAction);

        this.busy = true;
    }

    /**
     * Finishes the order
     * @param order Contains exact Order
     * @param orderID Contains exact Order ID
     * @param sourceRestaurant
     */
    private void finishPreparingOrder( Order order, int orderID, @NotNull Restaurant sourceRestaurant) {

        PreparedOrderQuality calculatedQuality =
                orderQualityDeterminer.determineOrderQuality(order, this);

        sourceRestaurant.addPreparedOrder(new PreparedOrder(order, orderID, calculatedQuality));

        this.busy = false;

        Simulation.getInstance().print( "I have prepared order, ID: " + orderID + ", quality: "
                        + calculatedQuality, this.toString());
    }

    /**
     * @return True when Cook is busy or false when not
     */
    public boolean isBusy() {
        return busy;
    }

    /**
     * @return The name of Cook object
     */
    public String getName() {
        return name;
    }

    /**
     * Determines how much time it takes to prepare exact order
     * @param order Contains exact Order
     * @return How many tick it takes to prepare exact order
     */
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

    /**
     * @return Skill level of the Cook
     */
    public int getSkillLevel() {
        return skillLevel;
    }
}
