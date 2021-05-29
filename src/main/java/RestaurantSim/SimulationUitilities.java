package RestaurantSim;

import java.util.Random;

public final class SimulationUitilities
{
    private static Random internalRandomGenerator = new Random();
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
    public static int GetRandomInt(int min, int max, Random randomGenerator)
    {
        return randomGenerator.nextInt((max - min) + 1) + min;
    }
    public static int GetRandomInt(int min, int max)
    {
        return internalRandomGenerator.nextInt((max - min) + 1) + min;
    }
    public static int GetRandomInt(int max, Random randomGenerator)
    {
        return randomGenerator.nextInt(max);
    }
    public static int GetRandomInt(int max)
    {
        return internalRandomGenerator.nextInt(max);
    }
    public static float GetRandomFloat(Random randomGenerator)
    {
        return randomGenerator.nextFloat();
    }
    public static float GetRandomFloat()
    {
        return internalRandomGenerator.nextFloat();
    }
}
