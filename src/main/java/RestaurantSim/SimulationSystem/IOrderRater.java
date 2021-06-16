package RestaurantSim.SimulationSystem;

import RestaurantSim.PreparedOrder;

public interface IOrderRater {
    float rateOrder( PreparedOrder order );
}
