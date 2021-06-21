package RestaurantSim.SimulationSystem;

import java.util.List;

/**
 * Classes that want to use {@link TickableAction} objects must implement this interface
 * in order to be properly registered and processed. This interface exposes necessary
 * methods to enable this. If object implementing this interface does not want to
 * use {@link TickableAction}s anymore it should signal that through shouldBeUnregistered() method.
 * Otherwise object's {@link TickableAction}s list will always be updated and checked.
 */
public interface ITickableActionObject {

    /**
     * Returns a list of TickableActions that object want to register
     * @return List of {@link TickableAction} to register
     */
    List<TickableAction> getTickableActions();

    /**
     * Returns condition whether object wants to be unregistered.
     * Unregistered object's {@link TickableAction} list will not be updated anymore
     * from now on.
     * @return Condition whether object should be unregistered
     */
    boolean shouldBeUnregistered();
}
