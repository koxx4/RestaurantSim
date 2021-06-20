package RestaurantSim;

import RestaurantSim.SimulationSystem.IOrderRater;
import RestaurantSim.SimulationSystem.Simulation;
import RestaurantSim.SimulationSystem.SimulationUtilities;

import java.util.List;

public class CustomerBuilder {
    private String name;
    private int patience;
    private IOrderRater orderRater;
    boolean startedBuilding;

    public CustomerBuilder(){
        name = "Unnamed customer";
        startedBuilding = false;
    }

    public CustomerBuilder buildCustomer(){
        startedBuilding = true;
        return this;
    }

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

    public CustomerBuilder withRandomPatience(int minValue, int maxValue){
        this.patience = SimulationUtilities.getRandomInt(minValue, maxValue);
        return this;
    }

    public CustomerBuilder withRater(IOrderRater rater){
       this.orderRater = rater;
        return this;
    }

    public Customer getBuiltCustomer(){
        if(startedBuilding)
            return new Customer(name, patience, orderRater);
        else
            return null;
    }

}
