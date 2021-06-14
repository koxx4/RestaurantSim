package RestaurantSim.SimulationSystem;

public class SimulationSettings
{
    public final int tickDuration;
    public final float chanceToRateRestaurant;
    public final float chanceToRateRestaurantImpatience;
    public final int maxClientPatience;
    public final int minClientPatience;
    public final int maxCookAgility;
    public final int minCookAgility;
    public final int maxCookSkill;
    public final int minCookSkill;
    public final int maxTicksSpawnClient;
    public final int minTicksSpawnClient;
    public final float rateOnOrderNotPreparedOnTime;
    public final int numberOfCooks;
    public final float dishQualityWorsenChance;
    public final float dishQualityImproveChance;
    public final float dishQualitWorsenChangeRate;
    public final float dishQualityImproveChangeRate;

    public SimulationSettings()
    {
        tickDuration = 0;
        chanceToRateRestaurant = 0;
        chanceToRateRestaurantImpatience = 0;
        maxClientPatience = 0;
        minClientPatience = 0;
        maxCookAgility = 0;
        minCookAgility = 0;
        maxTicksSpawnClient = 0;
        minTicksSpawnClient = 0;
        rateOnOrderNotPreparedOnTime = 0;
        numberOfCooks = 0;
        maxCookSkill = 0;
        minCookSkill = 0;
        dishQualityWorsenChance = 0;
        dishQualityImproveChance = 0;
        dishQualitWorsenChangeRate = 0;
        dishQualityImproveChangeRate = 0;
    }

}

