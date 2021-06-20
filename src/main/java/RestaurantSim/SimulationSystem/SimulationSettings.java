package RestaurantSim.SimulationSystem;

/**
 * Class that contains all configuration options for simulation
 */
public class SimulationSettings
{
    /**
     * Time in milliseconds that passes between each tick
     */
    public final int tickDuration;
    /**
     * Chance at which customer will rate restaurant after receiving prepared order.
     * Range from [0,1].
     */
    public final float chanceToRateRestaurant;
    /**
     * Chance at which customer will rate restaurant after waiting to long in queue.
     * Range from [0,1].
     */
    public final float chanceToRateRestaurantImpatience;
    /**
     * Maximum possible client patience
     */
    public final int maxClientPatience;
    /**
     * Minimal possible client patience
     */
    public final int minClientPatience;
    /**
     * Maximum possible cook agility
     */
    public final int maxCookAgility;
    /**
     * Minimal possible cook agility
     */
    public final int minCookAgility;
    /**
     * Maximum possible cook skill
     */
    public final int maxCookSkill;
    /**
     * Minimum possible cook skill
     */
    public final int minCookSkill;
    /**
     * Maximum possible ticks amount after which client will spawn
     */
    public final int maxTicksSpawnClient;
    /**
     * Minimum possible ticks amount after which client will spawn
     */
    public final int minTicksSpawnClient;
    /**
     * Rate given to {@link RestaurantSim.Restaurant} by {@link RestaurantSim.Customer} when
     * its waiting time is up.
     */
    public final float rateOnOrderNotPreparedOnTime;
    /**
     * Number of cooks that {@link RestaurantSim.Restaurant} has
     */
    public final int numberOfCooks;
    /**
     * Chance at which dish quality can worsen.
     * Range from [0,1].
     */
    public final float dishQualityWorsenChance;
    /**
     * Chance at which dish quality can improve.
     * Range from [0,1].
     */
    public final float dishQualityImproveChance;
    /**
     * This value will be subtracted from dishQualityWorsenChance after
     * the dish quality was successfully worsened.
     */
    public final float dishQualitWorsenChangeRate;
    /**
     * This value will be subtracted from dishQualityImproveChance after
     * the dish quality was successfully improved.
     */
    public final float dishQualityImproveChangeRate;
    /**
     * Chance at which sanepid will close {@link RestaurantSim.Restaurant} because of a
     * low rates average value.
     */
    public final float sanepidRestaurantRateBasedCloseChance;
    /**
     * Ticks frequency at which restaurant rate check will be made
     */
    public final int restaruantRatesCheckFrequency;
    /**
     * Time in ticks of how long will take Sanepid object to evaluate a Restaurant
     */
    public final int sanepidEvaluationTime;
    /**
     * Time in ticks of how long the initial protection of Restaurant from Sanepid
     * checks will last.
     */
    public final int restaurantStartProtectionDuration;
    /**
     * Chance at which Customer object will decide to compose its own dish
     * from available ingredients
     */
    public final float customerChanceToMakeOwnDish;
    /**
     * Chance at which Customer object will add another ingredient to its
     * own composed dish.
     */
    public final float customerChanceToAddIngredient;

    /**
     * Creates object of this class and assigns all of its internal values to 0
     */
    public SimulationSettings() {
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
        sanepidRestaurantRateBasedCloseChance = 0;
        restaruantRatesCheckFrequency = 0;
        sanepidEvaluationTime = 0;
        restaurantStartProtectionDuration = 0;
        customerChanceToMakeOwnDish = 0;
        customerChanceToAddIngredient = 0;
    }
}

