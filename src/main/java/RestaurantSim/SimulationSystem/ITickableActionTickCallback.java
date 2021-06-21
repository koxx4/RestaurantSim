package RestaurantSim.SimulationSystem;

/**
 * An interface that defines a behaviour when {@link TickableAction} is updated
 * (which happens every tick).
 */
public interface ITickableActionTickCallback {
    /**
     * Behaviour when {@link TickableAction} is updated (ticked)
     */
    void execute();
}
