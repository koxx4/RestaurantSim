package RestaurantSim.SimulationSystem;

import java.util.List;

public interface ITickableActionObject {

    List<TickableAction> getTickableActions();
    boolean shouldBeUnregistered();
}
