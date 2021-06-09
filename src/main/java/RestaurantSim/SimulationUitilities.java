package RestaurantSim;

import java.util.Random;

public final class SimulationUitilities
{
    private static final Random internalRandomGenerator = new Random();
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
    public static int getRandomInt( int min, int max, Random randomGenerator)
    {
        return randomGenerator.nextInt((max - min) + 1) + min;
    }
    public static int getRandomInt( int min, int max)
    {
        return internalRandomGenerator.nextInt((max - min) + 1) + min;
    }
    public static int getRandomInt( int max, Random randomGenerator)
    {
        return randomGenerator.nextInt(max);
    }
    public static int getRandomInt( int max)
    {
        return internalRandomGenerator.nextInt(max);
    }
    public static float getRandomFloat( Random randomGenerator)
    {
        return randomGenerator.nextFloat();
    }
    public static float getRandomFloat()
    {
        return internalRandomGenerator.nextFloat();
    }

    public static boolean isGoingToHappen( float chanceToHappen, Random randomGenerator)
    {
        float drawnChance = randomGenerator.nextFloat();

        return drawnChance <= chanceToHappen;
    }

    /**
     * Returns true with given possibility. Otherwise returns false.
     * Helpful if you want to run some code based on probability of something occurring.
     * @param chanceToHappen Chance that this function will return true
     * @return true or false
     */
    public static boolean isGoingToHappen( float chanceToHappen )
    {
        float drawnChance = internalRandomGenerator.nextFloat();

        return drawnChance <= chanceToHappen;
    }

}
