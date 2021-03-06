package RestaurantSim.SimulationSystem;

/**
 * Class that wraps all the data that the simulation requires to run properly.
 * For now it only consists of {@link PeopleData} and {@link FoodData}
 */
public class SimulationData {
    public final PeopleData peopleData;
    public final FoodData foodData;

    /**
     * Creates an instance of this class.
     * @param peopleData loaded people data
     * @param foodData lodaded food data
     */
    public SimulationData( PeopleData peopleData, FoodData foodData ) {
        this.peopleData = peopleData;
        this.foodData = foodData;
    }

    /**
     * Returns people data
     * @return
     */
    public PeopleData getPeopleData( ) {
        return peopleData;
    }

    /**
     * Returns food data
     * @return
     */
    public FoodData getFoodData( ) {
        return foodData;
    }
}
