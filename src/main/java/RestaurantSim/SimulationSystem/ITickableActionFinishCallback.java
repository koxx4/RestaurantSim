package RestaurantSim.SimulationSystem;

/**
 * An interface that defines a behavior of a TickableAction when
 * it is finished.
 */
public interface ITickableActionFinishCallback {
    /**
     * Behaviour when TickableAction is finished
     */
    void execute();
}
