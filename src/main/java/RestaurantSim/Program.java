package RestaurantSim;

public class Program
{
    private static SimulationManager simulationManager;

    public static void main(String[] args)
    {
        System.out.println("Start symulacji");
        simulationManager = new SimulationManager(4000);
        simulationManager.StartSimulation();
    }
}
