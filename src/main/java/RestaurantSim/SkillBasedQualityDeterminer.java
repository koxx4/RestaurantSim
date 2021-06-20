package RestaurantSim;

import RestaurantSim.SimulationSystem.*;

//This implementation expects that @PreparedOrderQuality enum starts with
//worst quality to the best
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

    private PreparedOrderQuality randomizeQuality( SimulationSettings settings, int calculatedQuality,
                                                   PreparedOrderQuality[] preparedOrderQualityValues  ) {
        PreparedOrderQuality quality;
        float baseImproveChance = settings.dishQualityImproveChance;
        float baseWorsenChance = settings.dishQualityWorsenChance;

        while ( true ) {

            boolean dishQualityShouldImprove = SimulationUtilities.isGoingToHappen(settings.dishQualityImproveChance);
            boolean dishQualityShouldWorsen = SimulationUtilities.isGoingToHappen(settings.dishQualityWorsenChance);

            if ( dishQualityShouldImprove ) {
                calculatedQuality++;
                baseImproveChance -= settings.dishQualityImproveChangeRate;
            }
            if ( dishQualityShouldWorsen ) {
                calculatedQuality--;
                baseWorsenChance -= settings.dishQualitWorsenChangeRate;
            }
            //If neither improves or worsens
            else if ( !dishQualityShouldImprove && !dishQualityShouldWorsen )
                break;
        }

            if(calculatedQuality >= preparedOrderQualityValues.length)
                quality = preparedOrderQualityValues[preparedOrderQualityValues.length - 1];
            else if(calculatedQuality < 0 )
                quality = preparedOrderQualityValues[0];
            else
                quality = preparedOrderQualityValues[calculatedQuality];

            return quality;
        }
    }
