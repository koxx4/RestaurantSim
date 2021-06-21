package RestaurantSim.SimulationSystem;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

/**
 * This class is responsible for loading and creating SimulationSettings object
 */
public class SimulationSettingsLoader {

    /**
     * Deserializes SimulationSettings object from JSON file
     * @return Deserialized SimulationSettings object
     * @throws IOException
     */
    public SimulationSettings loadFromJson() throws IOException
    {
        SimulationSettings loadedSettings;
        Gson gson = new Gson();

        String simSettingsPath = getClass().getResource("/simulation_settings.json")
                .getPath();

        loadedSettings = gson.fromJson(new FileReader(simSettingsPath), SimulationSettings.class);

        return loadedSettings;
    }

}
