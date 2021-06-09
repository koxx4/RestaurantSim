package RestaurantSim;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class SimulationSettingsLoader {

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
