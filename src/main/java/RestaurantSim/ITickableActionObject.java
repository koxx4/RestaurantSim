package RestaurantSim;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ITickableActionObject {

    List<TickableAction> getTickableActions( );
}
