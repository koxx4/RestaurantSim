package RestaurantSim.SimulationSystem;

import RestaurantSim.SimulationSystem.TickableAction;

import java.util.List;

public interface ITickableActionObject {

    List<TickableAction> getTickableActions();
    boolean shouldBeUnregistered();
}
