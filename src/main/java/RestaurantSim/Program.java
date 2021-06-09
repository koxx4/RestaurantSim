package RestaurantSim;

import java.io.IOException;

public class Program
{

    public static void main(String[] args)
    {
        SimulationManager simulationManager = null;
        SimulationSettingsLoader simulationSettingsLoader = new SimulationSettingsLoader();
        SimulationDataLoader simulationDataLoader = new SimulationDataLoader();

        try {
            simulationManager = new SimulationManager(
                    simulationSettingsLoader.loadFromJson(),
                    simulationDataLoader.loadFromJson());

            simulationManager.startSimulation();

        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }
}
