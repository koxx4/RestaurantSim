package RestaurantSim;

public class QualityBasedOrderRater implements IOrderRater{

    @Override
    public float rateOrder( PreparedOrder order ) {
        float rate = 0;
        switch (order.getQuality())
        {
            case Inedible: rate = SimulationUitilities.getRandomFloat(); break;
            case Bad: rate = SimulationUitilities.getRandomFloat() + 1; break;
            case Average: rate = SimulationUitilities.getRandomFloat() + 2.5f; break;
            case Good: rate = SimulationUitilities.getRandomFloat() + 3; break;
            case Excelent: rate = SimulationUitilities.getRandomFloat() + 4; break;
            default: break;
        }
        return rate;
    }

}
