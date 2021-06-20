package RestaurantSim.SimulationSystem;

import RestaurantSim.PreparedOrder;

/**
 * An interface that defines how order should be rated.
 */
public interface IOrderRater {
    /**
     * Calculates given order rate
     * @param order Order to rate
     * @return Rate of given order. In range of from 1 to 5.
     */
    float rateOrder( PreparedOrder order );
}
