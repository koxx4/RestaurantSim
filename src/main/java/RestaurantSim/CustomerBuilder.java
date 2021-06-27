package RestaurantSim;

import RestaurantSim.SimulationSystem.IOrderRater;
import RestaurantSim.SimulationSystem.SimulationUtilities;

import java.util.List;

/**
 * This class provides a convenient way to create a {@link Customer} object
 * through descriptive methods.
 */
public class CustomerBuilder {
    private String name;
    private int patience;
    private IOrderRater orderRater;
    private Restaurant targetRestaurant;
    boolean startedBuilding;

    /**
     * Default constructor.
     */
    public CustomerBuilder(){
        name = "Unnamed customer";
    }

    /**
     * Starts building new customer. Sets restaurant reference that the
     * built customer will be assigned to.
     * @param targetRestaurant
     * @return this CustomerBuilder for convenient build function chaining.
     */
    public CustomerBuilder buildCustomerAssignedToRestaurant(Restaurant targetRestaurant){
        this.targetRestaurant = targetRestaurant;
        startedBuilding = true;
        return this;
    }

    /**
     * Sets the name of the customer that is being build.
     * @param name Name of the customer
     * @return this CustomerBuilder for convenient build function chaining.
     */
    public CustomerBuilder named(String name){
        this.name = name;
        return this;
    }

    /**
     * Sets the patience of the customer that is being build.
     * @param value
     * @return this CustomerBuilder for convenient build function chaining.
     */
    public CustomerBuilder withPatience(int value){
        this.patience = value;
        return this;
    }

    /**
     * Sets the random name of the customer that is being build.
     * Random name is constructed out of provided list of names
     * and surnames.
     * @param firstNames Contains list of firstnames
     * @param lastNames Contains list of lastname
     * @return this CustomerBuilder for convenient function chaining.
     */
    public CustomerBuilder namedRandomly( List<String> firstNames, List<String> lastNames ){
        String firstName = firstNames.get(SimulationUtilities.getRandomInt(firstNames.size()));
        String lastName = lastNames.get(SimulationUtilities.getRandomInt(firstNames.size()));
        String fullName = firstName + " " + lastName;
        this.name = fullName;
        return this;
    }

    /**
     * Sets the random patience of the customer that is being build.
     * Value is between minValue and maxValue.
     * @param minValue Contains minimum value, inclusive
     * @param maxValue Contains maximum value, inclusive
     * @return this CustomerBuilder for convenient function chaining.
     */
    public CustomerBuilder withRandomPatience(int minValue, int maxValue){
        this.patience = SimulationUtilities.getRandomInt(minValue, maxValue);
        return this;
    }

    /**
     *  Sets the implementation of the {@link IOrderRater} for the
     *  customer that is being build.
     * @param rater Implementation of IOrderRater
     * @return this CustomerBuilder for convenient build function chaining.
     */
    public CustomerBuilder withRater(IOrderRater rater){
       this.orderRater = rater;
        return this;
    }

    /**
     * Creates and returns new {@link Customer}.
     * @return new {@link Customer} or <b>null</b> if building was not started in the
     * first place
     */
    public Customer getBuiltCustomer(){
        if(startedBuilding)
            return new Customer(name, patience, orderRater, targetRestaurant);
        else
            return null;
    }

}
