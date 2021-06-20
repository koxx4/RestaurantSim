package RestaurantSim;

import RestaurantSim.SimulationSystem.IEvaluationService;
import RestaurantSim.SimulationSystem.Simulation;
import RestaurantSim.SimulationSystem.SimulationUtilities;

public class RateBasedEvaluationService implements IEvaluationService {
    @Override
    public boolean restaurantShouldBeClosed( Restaurant restaurant ) {
        if(restaurant.getRatesAverage() <= 2.2){
            return SimulationUtilities.isGoingToHappen(
                    Simulation.getInstance().getSettings().sanepidRestaurantRateBasedCloseChance);
        }
        return false;
    }
}
