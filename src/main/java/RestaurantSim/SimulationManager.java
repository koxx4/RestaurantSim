package RestaurantSim;

public class SimulationManager {
    private boolean running;
    private Restaurant restaurant;
    private RestaurantGuest[] restaurantGuests;

    public SimulationManager(boolean running, Restaurant restaurant, RestaurantGuest[] restaurantGuests )
    {
        this.running = running;
        this.restaurant = restaurant;
        this.restaurantGuests = restaurantGuests;
    }
    private RestaurantGuest GenerateNextRestaurantGuest(RestaurantGuest restaurantGuest)
    {
        return null;
    }
    private void Tick()
    {

    }
    public boolean isRunning()
    {
        return false;
    }
    public void StartSimulation()
    {
        // Initialize
        
    }
    public void SubscribeAction()
    {

    }
}
