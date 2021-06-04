package RestaurantSim;

import com.google.gson.Gson;
import org.apache.commons.lang3.time.StopWatch;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulationManager
{
    public static SimulationManager instance;
    private SimulationSettings settings;
    private PeopleData peopleData;
    private FoodData foodData;
    private int tickDuration;
    private long elapsedTime;
    private long elapsedTicks;
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
        try
        {
            Initialize();
        } catch (Exception e)
        {
            e.printStackTrace();
            this.Stop();
        }
    }

    public SimulationManager()
    {
        this(1000);
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
                stopWatch.reset();
                try { Tick(); }
                catch (Exception ex) { ex.printStackTrace(); }
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
        System.out.println(this + "Stopping simulation!");
        this.running = false;
    }

    public SimulationSettings GetSettings()
    {
        return this.settings;
    }

    private Customer GenerateCustomer()
    {
        return new Customer(peopleData.GetRandomFullName());
    }

    private void Initialize() throws IOException
    {
        //If we want to set up some things before
        //starting simulation
        LoadJsonConfigs();

        CreateSpawnCustomerAction();
        //Only now we can create restaurant
        restaurant = new Restaurant(new Menu(this.foodData));
    }

    private void LoadJsonConfigs() throws IOException
    {
        Gson gson = new Gson();

        String simSettingsPath = getClass().getResource("/simulation_settings.json").getPath();
        String menuPath = getClass().getResource("/food_data.json").getPath();
        String namesSurnamesPath = getClass().getResource("/names_surnames_data.json").getPath();

        this.settings = gson.fromJson(new FileReader(simSettingsPath), SimulationSettings.class);
        this.foodData = gson.fromJson(new FileReader(menuPath), FoodData.class);
        this.peopleData = gson.fromJson(new FileReader(namesSurnamesPath), PeopleData.class);
    }

    private void CreateSpawnCustomerAction()
    {
        TickableAction spawnNextCustomer = new TickableAction
                ("Spawning customer action", SimulationUitilities.
                        GetRandomInt(settings.minTicksSpawnClient, settings.maxTicksSpawnClient));

        System.out.println(this + "next customer in " + spawnNextCustomer.GetDuration() + " ticks");

        spawnNextCustomer.SetOnFinishCallback(  () -> {
            //So we create new customer, place him into simulation
            this.restaurant.AddGuestToQueue(GenerateCustomer());
            //Then we recreate this task beacause we want to
            //spawn customers infinitely (for now?)
            System.out.println("SimMan: created customer!");
            CreateSpawnCustomerAction();
        });

        this.SubscribeAction(spawnNextCustomer);

    }

    private void Tick()
    {
        elapsedTicks++;
        System.out.println(this + "Tick("+ elapsedTicks +")! Duration: " + elapsedTime);

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

    @Override
    public String toString()
    {
        return "SimMan: ";
    }

}
