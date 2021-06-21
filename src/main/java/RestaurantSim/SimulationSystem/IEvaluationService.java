package RestaurantSim.SimulationSystem;

import RestaurantSim.Restaurant;

/**
 * An interface that defines how object of Restaurant class
 * should be evaluated
 */
public interface IEvaluationService {
    /**
     * Evaluates restaurant and decides whether it should be closed.
     * @param restaurant Restaurant to evaluate
     * @return Boolean whether restaurant should be closed as a result of evaluation
     */
    boolean restaurantShouldBeClosed( Restaurant restaurant);
}
