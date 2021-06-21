package RestaurantSim.SimulationSystem;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * This class is responsible for loading simulation data.
 */
public class SimulationDataLoader {

    /**
     * Loads simulation data from JSON config files.
     * @return Simulation data object that is fully deserialized from json files.
     * @throws FileNotFoundException
     */
    public SimulationData loadFromJson() throws FileNotFoundException {
        Gson gson = new Gson();

        String menuPath = getClass().getResource("/food_data.json")
                .getPath();
        String namesSurnamesPath = getClass().getResource("/names_surnames_data.json")
                .getPath();

        FoodData foodData = gson.fromJson(new FileReader(menuPath), FoodData.class);
        PeopleData peopleData = gson.fromJson(new FileReader(namesSurnamesPath), PeopleData.class);

        return new SimulationData(peopleData, foodData);
    }
}
