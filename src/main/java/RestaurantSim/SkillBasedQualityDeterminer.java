package RestaurantSim;

import RestaurantSim.SimulationSystem.*;

/**   Implementation of {@link IOrderQualityDeterminer} that heavily
 *    relies on skill that Cook preparing order has.
 *    This implementation expects that {@link PreparedOrderQuality} enum starts with
 *    worst quality to the best.
 * @see Cook
 */

public class SkillBasedQualityDeterminer implements IOrderQualityDeterminer {

    @Override
    public PreparedOrderQuality determineOrderQuality( Order order, Cook cook ) {
        //This array should be cached like that to avoid deep copying every time we must use it
        PreparedOrderQuality[] preparedOrderQualityValues = PreparedOrderQuality.values();

        SimulationSettings settings = Simulation.getInstance().getSettings();

        int numberOfSkillLevels = settings.maxCookSkill - settings.minCookSkill;

        float skillPercentage = (float) cook.getSkillLevel() / numberOfSkillLevels;
        int calculatedQuality = Math.round(skillPercentage * (preparedOrderQualityValues.length - 1));

        return randomizeQuality(settings, calculatedQuality , preparedOrderQualityValues);
    }

    /**
     * Generates random Order quality. This function starts from {@param calculatedQualityBase}
     * and then randomizes it based on chances that are set
     * in {@link SimulationSettings}. Given quality can become worse or better.
     * @see SimulationSettings#dishQualityImproveChance
     * and
     * @see SimulationSettings#dishQualityWorsenChance
     * @param settings Gives the function information about the food quality and chances to improve or worsen the Dish
     * @param calculatedQualityBase Integer that matches one of the {@link PreparedOrderQuality} enum value. This value
     *                              will be used as a starting point for improving or worsening final order quality.
     * @param preparedOrderQualityValues Gives the values of {@link PreparedOrderQuality} enum
     * @return Random Order quality
     */
    private PreparedOrderQuality randomizeQuality( SimulationSettings settings, int calculatedQualityBase,
                                                   PreparedOrderQuality[] preparedOrderQualityValues  ) {
        PreparedOrderQuality quality;
        float baseImproveChance = settings.dishQualityImproveChance;
        float baseWorsenChance = settings.dishQualityWorsenChance;

        while ( true ) {

            boolean dishQualityShouldImprove = SimulationUtilities.isGoingToHappen(settings.dishQualityImproveChance);
            boolean dishQualityShouldWorsen = SimulationUtilities.isGoingToHappen(settings.dishQualityWorsenChance);

            if ( dishQualityShouldImprove ) {
                calculatedQualityBase++;
                baseImproveChance -= settings.dishQualityImproveChangeRate;
            }
            if ( dishQualityShouldWorsen ) {
                calculatedQualityBase--;
                baseWorsenChance -= settings.dishQualitWorsenChangeRate;
            }
            //If neither improves or worsens
            else if ( !dishQualityShouldImprove && !dishQualityShouldWorsen )
                break;
        }

            if(calculatedQualityBase >= preparedOrderQualityValues.length)
                quality = preparedOrderQualityValues[preparedOrderQualityValues.length - 1];
            else if(calculatedQualityBase < 0 )
                quality = preparedOrderQualityValues[0];
            else
                quality = preparedOrderQualityValues[calculatedQualityBase];

            return quality;
        }
    }
