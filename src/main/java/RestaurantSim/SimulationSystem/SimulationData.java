package RestaurantSim.SimulationSystem;

public class SimulationData {
    public final PeopleData peopleData;
    public final FoodData foodData;

    public SimulationData( PeopleData peopleData, FoodData foodData ) {
        this.peopleData = peopleData;
        this.foodData = foodData;
    }

    public PeopleData getPeopleData( ) {
        return peopleData;
    }

    public FoodData getFoodData( ) {
        return foodData;
    }
}
