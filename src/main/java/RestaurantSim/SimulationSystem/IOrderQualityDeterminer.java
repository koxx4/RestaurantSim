package RestaurantSim.SimulationSystem;

import RestaurantSim.Cook;
import RestaurantSim.Order;
import RestaurantSim.PreparedOrderQuality;

public interface IOrderQualityDeterminer {
    PreparedOrderQuality determineOrderQuality( Order order, Cook cook);
}
