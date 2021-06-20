package RestaurantSim;

import RestaurantSim.SimulationSystem.IOrderRater;
import RestaurantSim.SimulationSystem.Simulation;
import RestaurantSim.SimulationSystem.SimulationUtilities;

public class QualityBasedOrderRater implements IOrderRater {

    @Override
    public float rateOrder( PreparedOrder order ) {
        float rate = 0;
        switch (order.getQuality())
        {
            case Inedible: rate = SimulationUtilities.getRandomFloat(); break;
            case Bad: rate = SimulationUtilities.getRandomFloat() + 1; break;
            case Average: rate = SimulationUtilities.getRandomFloat() + 2.5f; break;
            case Good: rate = SimulationUtilities.getRandomFloat() + 3; break;
            case Excelent: rate = SimulationUtilities.getRandomFloat() + 4; break;
            default: break;
        }
        return rate;
    }

}
