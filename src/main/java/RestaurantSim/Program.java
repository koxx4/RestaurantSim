package RestaurantSim;

import org.w3c.dom.ls.LSOutput;

public class Program
{
    private static SimulationManager simulationManager;
    private static TickableAction firstAction;
    private static TickableAction secondAction;
    private static TickableAction thirdAction;

    public static void main(String[] args)
    {
        firstAction = new TickableAction(10);
        secondAction = new TickableAction(5);
        thirdAction = new TickableAction(3);
        firstAction.onFinishCallback = () -> System.out.println("FirstAction onFinishCallback");
        firstAction.onUpdateCallback = () -> System.out.println("FirstAction onUpdateCallback");
        secondAction.onFinishCallback = () -> System.out.println("SecondAction onFinishCallback");
        secondAction.onUpdateCallback = () -> System.out.println("SecondAction onUpdateCallback");
        thirdAction.onFinishCallback = () -> System.out.println("ThirdAction FINISH!");


        System.out.println("Start symulacji");
        simulationManager = new SimulationManager(1000);
        simulationManager.SubscribeAction(firstAction);
        simulationManager.SubscribeAction(secondAction);
        simulationManager.SubscribeAction(thirdAction);
        simulationManager.StartSimulation();

    }
}
