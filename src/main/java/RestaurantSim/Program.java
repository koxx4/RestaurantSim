package RestaurantSim;

import RestaurantSim.SimulationSystem.*;

import java.io.IOException;

public class Program
{

    /**
     * Entry point of this program. Creates an simulation and runs it.
     * @param args Command line arguments. This application does not support any.
     *             All settings should be applied to the corresponding json config files.
     */
    public static void main(String[] args)
    {
        Simulation simulation = null;
        SimulationSettingsLoader simulationSettingsLoader = new SimulationSettingsLoader();
        SimulationDataLoader simulationDataLoader = new SimulationDataLoader();

        try {
            simulation = new Simulation(
                    simulationSettingsLoader.loadFromJson(),
                    simulationDataLoader.loadFromJson(),
                    new LanternaDisplay());

            simulation.start();

        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }
}
