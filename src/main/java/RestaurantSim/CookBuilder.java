package RestaurantSim;

import RestaurantSim.SimulationSystem.IOrderQualityDeterminer;
import RestaurantSim.SimulationSystem.SimulationUitilities;

import java.util.List;

public class CookBuilder {
    private IOrderQualityDeterminer orderQualityDeterminer;
    private String name;
    private int agility;
    private int skillLevel;
    private boolean startedBuilding;
    /**
     * Create order quality which is determined
     * @return Started building as false
     */

    public CookBuilder() {
        name = "Unnamed cook";
        startedBuilding = false;

        //Default
        orderQualityDeterminer = new SkillBasedQualityDeterminer();
    }

    /**
     * Sets begin of building as false
     * @return Cookbuilder
     */

    public CookBuilder buildCook(){
        startedBuilding = true;
        return this;
    }

    /**
     *
     * @param name Contains exact name
     * @return Cookbuilder
     */
    public CookBuilder named(String name){
        this.name = name;
        return this;
    }

    /**
     *
     * @param firstNames Contains exact firstNames
     * @param surnames Contains exact surnNames
     * @return First Name is associated with surname
     */
    public CookBuilder namedRandomly(List<String> firstNames, List<String> surnames){
        String firstName = firstNames.get(SimulationUitilities.getRandomInt(firstNames.size()));
        String surname = surnames.get(SimulationUitilities.getRandomInt(surnames.size()));
        this.name = firstName + " " + surname;

        return this;
    }

    /**
     *
     * @param value Contains value of builder's agility
     * @return value
     */
    public CookBuilder withAgility(int value){
        this.agility = value;
        return this;
    }

    /**
     *
     * @param minValue Contains exact minimum value
     * @param maxValue Contains exact maximum value
     * @return Random agility
     */
    public CookBuilder withRandomAgility(int minValue, int maxValue){
        this.agility = SimulationUitilities.getRandomInt(minValue, maxValue);
        return this;
    }

    /**
     *
     * @param value Contains exact value
     * @return Skill level
     */
    public CookBuilder withSkillLevel(int value){
        this.skillLevel = value;
        return this;
    }

    /**
     * Assingns skill level
     * @param minValue Contains exact minimum value
     * @param maxValue Contains exact maximum value
     * @return Random Skill Level
     */
    public CookBuilder withRandomSkillLevel(int minValue, int maxValue){
        this.skillLevel = SimulationUitilities.getRandomInt(minValue, maxValue);
        return this;
    }

    /**
     *
     * @param determiner contains exact determiner
     * @return Order quality determiner
     */
    public CookBuilder withOrderQualityDeterminer(IOrderQualityDeterminer determiner){
        this.orderQualityDeterminer = determiner;
        return this;
    }

    /**
     * Ckecks if startedBulding is true it creates Cook
     * @return builtCook
     */
    public Cook getBuiltCook(){
        if(startedBuilding)
            return new Cook(name, agility, skillLevel, orderQualityDeterminer);
        else
            return null;
    }

}