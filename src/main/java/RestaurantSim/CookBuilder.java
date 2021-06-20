package RestaurantSim;

import RestaurantSim.SimulationSystem.IOrderQualityDeterminer;
import RestaurantSim.SimulationSystem.Simulation;
import RestaurantSim.SimulationSystem.SimulationUtilities;

import java.util.List;

public class CookBuilder {
    private IOrderQualityDeterminer orderQualityDeterminer;
    private String name;
    private int agility;
    private int skillLevel;
    private boolean startedBuilding;

    public CookBuilder() {
        name = "Unnamed cook";
        startedBuilding = false;

        //Default
        orderQualityDeterminer = new SkillBasedQualityDeterminer();
    }

    public CookBuilder buildCook(){
        startedBuilding = true;
        return this;
    }

    public CookBuilder named(String name){
        this.name = name;
        return this;
    }

    public CookBuilder namedRandomly(List<String> firstNames, List<String> surnames){
        String firstName = firstNames.get(SimulationUtilities.getRandomInt(firstNames.size()));
        String surname = surnames.get(SimulationUtilities.getRandomInt(surnames.size()));
        this.name = firstName + " " + surname;

        return this;
    }

    public CookBuilder withAgility(int value){
        this.agility = value;
        return this;
    }

    public CookBuilder withRandomAgility(int minValue, int maxValue){
        this.agility = SimulationUtilities.getRandomInt(minValue, maxValue);
        return this;
    }

    public CookBuilder withSkillLevel(int value){
        this.skillLevel = value;
        return this;
    }

    public CookBuilder withRandomSkillLevel(int minValue, int maxValue){
        this.skillLevel = SimulationUtilities.getRandomInt(minValue, maxValue);
        return this;
    }

    public CookBuilder withOrderQualityDeterminer(IOrderQualityDeterminer determiner){
        this.orderQualityDeterminer = determiner;
        return this;
    }

    public Cook getBuiltCook(){
        if(startedBuilding)
            return new Cook(name, agility, skillLevel, orderQualityDeterminer);
        else
            return null;
    }

}