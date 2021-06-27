package RestaurantSim;

import RestaurantSim.SimulationSystem.IEvaluationService;
import RestaurantSim.SimulationSystem.Simulation;
import RestaurantSim.SimulationSystem.SimulationUtilities;

/**
 * This implementation of the {@link IEvaluationService} is based on
 * rates that
 */
public class AverageRateBasedEvaluationService implements IEvaluationService {
    @Override
    public boolean restaurantShouldBeClosed( Restaurant restaurant ) {
        if(restaurant.getRatesAverage() <= 2.2){
            return SimulationUtilities.isGoingToHappen(
                    Simulation.getInstance().getSettings().sanepidRestaurantRateBasedCloseChance);
        }
        return false;
    }
}
