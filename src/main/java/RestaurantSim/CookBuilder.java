package RestaurantSim;

import RestaurantSim.SimulationSystem.IOrderQualityDeterminer;
import RestaurantSim.SimulationSystem.SimulationUtilities;

import java.util.List;

/**
 * This class provides a convenient way to create a {@link Cook} object
 * through descriptive methods.
 */
public class CookBuilder {
    private IOrderQualityDeterminer orderQualityDeterminer;
    private String name;
    private int agility;
    private int skillLevel;
    private boolean startedBuilding;
    private Restaurant cookWokrplace;

    /**
     * Creates new CookBuilder
     */
    public CookBuilder() {
        name = "Unnamed cook";
        startedBuilding = false;
        orderQualityDeterminer = new SkillBasedQualityDeterminer();
    }

    /**
     * Sets begin of building as true
     * @return this CookBuilder for convenient build function chaining.
     */
    public CookBuilder buildCook(){
        startedBuilding = true;
        return this;
    }

    /**
     * Sets the name of the cook that is being build
     * @param name name of the cook
     * @return this CookBuilder for convenient function chaining.
     */
    public CookBuilder named(String name){
        this.name = name;
        return this;
    }

    /**
     * Sets the random name of the cook that is being build.
     * Random name is constructed out of provided list of names
     * and surnames.
     * @param firstNames Contains list of firstnames
     * @param surnames Contains list of surnames
     * @return this CookBuilder for convenient function chaining.
     */
    public CookBuilder namedRandomly(List<String> firstNames, List<String> surnames){
        String firstName = firstNames.get(SimulationUtilities.getRandomInt(firstNames.size()));
        String surname = surnames.get(SimulationUtilities.getRandomInt(surnames.size()));
        this.name = firstName + " " + surname;
        return this;
    }

    /**
     * Sets the agility value of the cook that is being build
     * @param value Contains value of builder's agility
     * @return this CookBuilder for convenient function chaining.
     */
    public CookBuilder withAgility(int value){
        this.agility = value;
        return this;
    }

    /**
     * Sets the random agility of the cook that is being build.
     * Value is between minValue and maxValue.
     * @param minValue Contains minimum value, inclusive
     * @param maxValue Contains maximum value, inclusive
     * @return this CookBuilder for convenient function chaining.
     */
    public CookBuilder withRandomAgility(int minValue, int maxValue){
        this.agility = SimulationUtilities.getRandomInt(minValue, maxValue);
        return this;
    }

    /**
     * Sets the level of the cook that is being build.
     * @param value Skill value
     * @return this CookBuilder for convenient function chaining.
     */
    public CookBuilder withSkillLevel(int value){
        this.skillLevel = value;
        return this;
    }

    /**
     * Sets the random skill level of the cook that is being build.
     * Value is between minValue and maxValue.
     * @param minValue Contains minimum value, inclusive
     * @param maxValue Contains maximum value, inclusive
     * @return this CookBuilder for convenient function chaining.
     */
    public CookBuilder withRandomSkillLevel(int minValue, int maxValue){
        this.skillLevel = SimulationUtilities.getRandomInt(minValue, maxValue);
        return this;
    }

    /**
     * Sets the {@link IOrderQualityDeterminer} of the cook that
     * is being build.
     * @param determiner Implementation of {@link IOrderQualityDeterminer}
     * @return this CookBuilder for convenient function chaining.
     */
    public CookBuilder withOrderQualityDeterminer(IOrderQualityDeterminer determiner){
        this.orderQualityDeterminer = determiner;
        return this;
    }

    /**
     * Sets the restaurant that the cook that is being build will work for.
     * @param target Restaurant that the cook will work for.
     * @return this CookBuilder for convenient function chaining.
     */
    public CookBuilder workingAt(Restaurant target){
        this.cookWokrplace = target;
        return this;
    }

    /**
     * Checks if startedBulding and if true returns new {@link Cook}.
     * @return cook with desired properties, <b>null</b> if building of the cook
     * has not been started.
     */
    public Cook getBuiltCook(){
        if(startedBuilding)
            return new Cook(name, agility, skillLevel, orderQualityDeterminer, cookWokrplace);
        else
            return null;
    }

}