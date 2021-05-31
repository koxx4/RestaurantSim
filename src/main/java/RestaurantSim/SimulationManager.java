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
        restaurant = new Restaurant(new Menu());

        Initialize();
    }

    public SimulationManager()
    {
        this(1000);
    }

    private void Tick()
    {
        if (!gameActions.isEmpty())
        {
            for (int i = gameActions.size() - 1; i >= 0; i--)
            {
                var action = gameActions.get(i);

                if(action.IsToBeAborted())
                {
                    gameActions.remove(i);
                }
                //If action is done
                else if (action.GetTicksToComplete() <= 1)
                {
                    action.ExecuteOnFinishCallback();
                    if(action.IsRepeatable())
                    {
                        //Renew action
                        action.SetTicksToComplete(action.GetDuration());
                    }
                    else
                        gameActions.remove(i);
                }
                //Else update action
                else
                {
                    action.DecrementTicks();
                    action.ExecuteOnUpdateCallback();
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

    private Customer GenerateCustomer()
    {
        return new Customer("Johne Doe");
    }

    private void Initialize()
    {
        //If we want to set up some things before
        //starting simulation
        CreateSpawnCustomerAction();
    }

    private void CreateSpawnCustomerAction()
    {
        TickableAction spawnNextCustomer = new TickableAction
                ("Spawning customer action", SimulationUitilities
                        .GetRandomInt(SimulationSettings.minTicksSpawnClient, SimulationSettings.maxTicksSpawnClient));
        spawnNextCustomer.onFinishCallback = () -> {
            //So we create new customer, place him into simulation
            this.restaurant.AddGuestToQueue(GenerateCustomer());
            //Then we recreate this task beacause we want to
            //spawn customers infinitely (for now?)
            CreateSpawnCustomerAction();
        };
    }
}
