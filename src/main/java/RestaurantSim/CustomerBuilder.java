package RestaurantSim;

import RestaurantSim.SimulationSystem.SimulationUitilities;

import java.util.List;

public class CustomerBuilder {
    private final Customer customerToBuild;

    public CustomerBuilder(){
        this.customerToBuild = new Customer();
    }

    public CustomerBuilder buildCustomer(){
        return this;
    }

    public CustomerBuilder named(String name){
        customerToBuild.setName(name);
        return this;
    }

    public CustomerBuilder withPatience(int value){
        customerToBuild.setPatience(value);
        return this;
    }

    public CustomerBuilder namedRandomly( List<String> firstNames, List<String> lastNames ){
        String firstName = firstNames.get(SimulationUitilities.getRandomInt(firstNames.size()));
        String lastName = lastNames.get(SimulationUitilities.getRandomInt(firstNames.size()));
        String fullName = firstName + " " + lastName;
        customerToBuild.setName(fullName);
        return this;
    }

    public CustomerBuilder withRandomPatience(int minValue, int maxValue){
        customerToBuild
                .setPatience(SimulationUitilities.getRandomInt(minValue, maxValue));
        return this;
    }

    public CustomerBuilder withRater(IOrderRater rater){
        customerToBuild.setOrderRater(rater);
        return this;
    }

    public Customer getBuildCustomer(){
        return this.customerToBuild;
    }

}
