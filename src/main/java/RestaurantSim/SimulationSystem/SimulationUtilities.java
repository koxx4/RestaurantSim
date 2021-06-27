package RestaurantSim.SimulationSystem;

import java.util.Random;

/**
 * This class provides many useful methods that are often used in simulation
 * especially methods that are somehow related to the random number generation.
 */
public final class SimulationUtilities
{
    private static final Random internalRandomGenerator = new Random();
    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @param randomGenerator Random class instance that will be used for random number generation
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int getRandomInt( int min, int max, Random randomGenerator) {
        return randomGenerator.nextInt((max - min) + 1) + min;
    }
    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int getRandomInt( int min, int max)
    {
        return internalRandomGenerator.nextInt((max - min) + 1) + min;
    }
    /**
     * Returns a pseudo-random number between 0 and max, inclusive.
     * The difference between 0 and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param max Maximum value.  Must be greater than min.
     * @param randomGenerator Random class instance that will be used for random number generation
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int getRandomInt( int max, Random randomGenerator)
    {
        return randomGenerator.nextInt(max);
    }
    /**
     * Returns a pseudo-random number between 0 and max, inclusive.
     * The difference between 0 and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int getRandomInt( int max)
    {
        return internalRandomGenerator.nextInt(max);
    }

    /**
     * Returns random float in range of [0,1).
     * @param randomGenerator Random class instance that will be used for random number generation
     * @return
     * @see Random#nextFloat()
     */
    public static float getRandomFloat( Random randomGenerator)
    {
        return randomGenerator.nextFloat();
    }
    /**
     * Returns random float in range of [0,1).
     * @return
     * @see Random#nextFloat()
     */
    public static float getRandomFloat()
    {
        return internalRandomGenerator.nextFloat();
    }

    /**
     * Returns true with given possibility. Otherwise returns false.
     * Helpful if you want to run some code based on probability of something occurring.
     * @param chanceToHappen Chance that this function will return true
     * @param randomGenerator Random class instance that will be used for random number generation
     * @return true or false
     */
    public static boolean isGoingToHappen( float chanceToHappen, Random randomGenerator) {
        float drawnChance = randomGenerator.nextFloat();
        return drawnChance <= chanceToHappen;
    }

    /**
     * Returns true with given possibility. Otherwise returns false.
     * Helpful if you want to run some code based on probability of something occurring.
     * @param chanceToHappen Chance that this function will return true
     * @return true or false
     */
    public static boolean isGoingToHappen( float chanceToHappen ) {
        float drawnChance = internalRandomGenerator.nextFloat();
        return drawnChance <= chanceToHappen;
    }

}
