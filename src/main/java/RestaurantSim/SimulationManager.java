package RestaurantSim;

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationManager
{
    public static SimulationManager instance;
    private int tickDuration;
    private long elapsedTime;
    private boolean running;
    private Restaurant restaurant;
    private List<TickableAction> gameActions;
    private StopWatch stopWatch;

    //Tick duration is in miliseconds!
    public SimulationManager(int tickDuration)
    {
        instance = this;
        this.running = true;
        this.tickDuration = tickDuration;
        stopWatch = new StopWatch();
        gameActions = new ArrayList<>();
    }

    private void Tick()
    {
        if (!gameActions.isEmpty())
        {
            for (int i = gameActions.size() - 1; i >= 0; i--)
            {
                //If action is done
                if (gameActions.get(i).GetTicksToComplete() <= 1)
                {
                    gameActions.get(i).ExecuteOnFinishCallback();
                    gameActions.remove(i);
                }
                //Else update action
                else
                {
                    gameActions.get(i).DecrementTicks();
                    gameActions.get(i).ExecuteOnUpdateCallback();
                }
            }
        }
    }

    public boolean isRunning()
    {
        return this.running;
    }

    public void StartSimulation()
    {
        // Initialize
        stopWatch.start();

        while (running)
        {
            stopWatch.suspend();
            elapsedTime = stopWatch.getTime();

            //If elapsed time matches tick duration
            if (elapsedTime >= tickDuration)
            {
                //Tick
                System.out.println("Tick! Duration: " + elapsedTime);
                stopWatch.reset();
                Tick();
                stopWatch.start();
            }
            else
                stopWatch.resume();
        }
    }

    public void SubscribeAction(TickableAction action)
    {
        gameActions.add(action);
    }

    public void Stop()
    {
        System.out.println("Stopping simulation!");
        this.running = false;
    }

    private Customer GenerateCustomer(RestaurantGuest restaurantGuest)
    {
        Customer generatedRestaurantGuest = new Customer("Johne Doe");


        return  generatedRestaurantGuest;
    }
}
