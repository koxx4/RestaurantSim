package RestaurantSim;

public class SimulationSettings
{
    public final float chanceToRateRestaurant;
    public final float chanceToRateRestaurantImpatience;
    public final int maxClientPatience;
    public final int minClientPatience;
    public final int maxCookAgility;
    public final int minCookAgility;
    public final int maxTicksSpawnClient;
    public final int minTicksSpawnClient;
    public final float rateOnOrderNotPreparedOnTime;
    public final int numberOfCooks;

    public SimulationSettings()
    {
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
    }

}

