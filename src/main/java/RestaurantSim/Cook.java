package RestaurantSim;

import RestaurantSim.SimulationSystem.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * This class resembles a cook that is able to make an requested {@link Order} into a {@link PreparedOrder}.
 * Time in which the order will be prepared depends solely on cook's agility and
 * the quality of the prepared order can depend on a cook's skill level
 * but any implementation {@link IOrderQualityDeterminer} passed to this class
 * constructor can provide its own way to determine the quality of the {@link PreparedOrder}.
 * @see QualityBasedOrderRater
 */
public class Cook implements ITickableActionObject
{
    private final IOrderQualityDeterminer orderQualityDeterminer;
    private final String name;
    private boolean busy;
    private final int agility;
    private final int skillLevel;
    private final List<TickableAction> tickableActions;
    private final Restaurant restaurantWorkplace;

    /**
     * Creates the object of this class
     * @param name Contains the name of Cook object
     * @param agility Contains the agility of Cook object
     * @param skillLevel Contains the skill level of Cook object
     * @param orderQualityDeterminer Implementation of IOrderQualityDeterminer
     * @param workplace Restaurant that this cook will work for
     */
    public Cook(String name, int agility, int skillLevel,
                IOrderQualityDeterminer orderQualityDeterminer, Restaurant workplace) {
        this.tickableActions = new ArrayList<>();
        this.name = name;
        this.agility = agility;
        this.skillLevel = skillLevel;
        this.orderQualityDeterminer = orderQualityDeterminer;
        this.restaurantWorkplace = workplace;
    }

    /**
     * Tries to make an order.
     * @param order Contains exact Order
     * @param orderID Contains exact Order ID
     */
    public void makeOrder( Order order, int orderID) {
        String actionMessage = "Preparing order "+ orderID;
        createMakeOrderAction(order, orderID, actionMessage);

        this.busy = true;
    }

    private void createMakeOrderAction( Order order, int orderID, String actionMessage ) {
        TickableAction prepareOrderAction = new TickableAction(actionMessage, this.estimateWorkTime(order));

        Simulation.getInstance().print(actionMessage + ". This will take me " + prepareOrderAction.getDuration()
                        + " ticks.", this.toString());

        prepareOrderAction.setOnFinishCallback( () -> {
            finishPreparingOrder(order, orderID, restaurantWorkplace);
        });
        tickableActions.add(prepareOrderAction);
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
     * @return True when Cook is busy, false when not
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
     * Determines how much time it takes to prepare order
     * @param order Contains exact Order
     * @return How many tick it takes to prepare exact order
     */
    private int estimateWorkTime( Order order) {
        int ticksToPrepareOrder = 0;
        for (var dish : order.getDishes())
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
