package RestaurantSim;

import RestaurantSim.SimulationSystem.IOrderRater;
import RestaurantSim.SimulationSystem.SimulationUtilities;

import java.util.List;

public class CustomerBuilder {
    private String name;
    private int patience;
    private IOrderRater orderRater;
    private Restaurant targetRestaurant;
    boolean startedBuilding;

    public CustomerBuilder(){
        name = "Unnamed customer";
        startedBuilding = false;
    }

    /**
     * Creates Customer and assigned them to Restaurant
     * @param targetRestaurant
     * @return This object
     */
    public CustomerBuilder buildCustomerAssignedToRestaurant(Restaurant targetRestaurant){
        this.targetRestaurant = targetRestaurant;
        startedBuilding = true;
        return this;
    }

    /**
     * Creates the object of this class
     * @param name Contains the name
     * @return This object
     */
    public CustomerBuilder named(String name){
        this.name = name;
        return this;
    }

    public CustomerBuilder withPatience(int value){
        this.patience = value;
        return this;
    }

    public CustomerBuilder namedRandomly( List<String> firstNames, List<String> lastNames ){
        String firstName = firstNames.get(SimulationUtilities.getRandomInt(firstNames.size()));
        String lastName = lastNames.get(SimulationUtilities.getRandomInt(firstNames.size()));
        String fullName = firstName + " " + lastName;
        this.name = fullName;
        return this;
    }

    /**
     * Creates object of this class with random patience
     * @param minValue Minimal value of patience
     * @param maxValue Maximal value of patience
     * @return This object
     */
    public CustomerBuilder withRandomPatience(int minValue, int maxValue){
        this.patience = SimulationUtilities.getRandomInt(minValue, maxValue);
        return this;
    }

    /**
     *  Creates object of this class that is abe to rate the Restaurant
     * @param rater Implementation of IOrderRater
     * @return This object
     */
    public CustomerBuilder withRater(IOrderRater rater){
       this.orderRater = rater;
        return this;
    }

    /**
     * @return The exact object of this class
     */
    public Customer getBuiltCustomer(){
        if(startedBuilding)
            return new Customer(name, patience, orderRater, targetRestaurant);
        else
            return null;
    }

}
