package RestaurantSim;

import RestaurantSim.SimulationSystem.IEvaluationService;
import RestaurantSim.SimulationSystem.SimulationManager;
import RestaurantSim.SimulationSystem.SimulationUitilities;

public class RateBasedEvaluationService implements IEvaluationService {
    @Override
    public boolean restaurantShouldBeClosed( Restaurant restaurant ) {
        if(restaurant.getRatesAverage() <= 2.2){
            return SimulationUitilities.isGoingToHappen(
                    SimulationManager.instance.getSettings().sanepidRestaurantRateBasedCloseChance);
        }
        return false;
    }
}
