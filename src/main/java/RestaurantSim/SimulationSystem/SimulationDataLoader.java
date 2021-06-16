package RestaurantSim.SimulationSystem;

import RestaurantSim.PeopleData;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class SimulationDataLoader {

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
