package RestaurantSim.SimulationSystem;

import RestaurantSim.Cook;
import RestaurantSim.Order;
import RestaurantSim.PreparedOrderQuality;

/**
 * An interface that defines how order quality should be determined (calculated)
 */
public interface IOrderQualityDeterminer {
    /**
     * Determines given order quality.
     * @param order Order which quality should be determined
     * @param cook Cook that is responsible for preparing order
     * @return Determined quality of an passed order.
     */
    PreparedOrderQuality determineOrderQuality( Order order, Cook cook);
}
