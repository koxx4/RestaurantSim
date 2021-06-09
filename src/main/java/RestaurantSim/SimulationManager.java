package RestaurantSim;

import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulationManager
{
    public static SimulationManager instance;
    private final SimulationSettings settings;
    private final PeopleData peopleData;
    private final FoodData foodData;
    private int tickDuration;
    private long elapsedTime;
    private long elapsedTicks;
    private boolean running;
    private Restaurant restaurant;
    private List<TickableAction> gameActions;
    private StopWatch stopWatch;

    public SimulationManager(SimulationSettings settings, SimulationData simulationData) {
        instance = this;
        this.running = true;
        this.tickDuration = settings.tickDuration;

        this.settings = settings;
        this.peopleData = simulationData.peopleData;
        this.foodData = simulationData.foodData;

        stopWatch = new StopWatch();
        gameActions = new ArrayList<>();
        try
        {
            initialize();
        } catch (Exception e)
        {
            e.printStackTrace();
            this.stop();
        }
    }

    public boolean isRunning()
    {
        return this.running;
    }

    public void startSimulation()
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
                try { tick(); }
                catch (Exception ex) { ex.printStackTrace(); }
                stopWatch.start();
            }
            else
                stopWatch.resume();
        }
    }

    public void subscribeAction( TickableAction action)
    {
        gameActions.add(action);
    }

    public void stop()
    {
        System.out.println(this + "Stopping simulation!");
        this.running = false;
    }

    public SimulationSettings getSettings()
    {
        return this.settings;
    }

    public String GetRandomFullName()
    {
        return this.peopleData.getRandomFullName();
    }

    private Customer generateCustomer()
    {
        CustomerBuilder customerBuilder = new CustomerBuilder();

        Customer buildedCustomer = customerBuilder.buildCustomer()
                .named(peopleData.getRandomFullName())
                .withRandomPatience(settings.minClientPatience, settings.maxClientPatience)
                .withRater(new QualityBasedOrderRater())
                .getBuildCustomer();

        return buildedCustomer;
    }

    private void initialize() throws IOException
    {
        //If we want to set up some things before
        createSpawnCustomerAction();
        //Only now we can create restaurant
        restaurant = new Restaurant(new Menu(this.foodData));
    }

    private void createSpawnCustomerAction()
    {
        TickableAction spawnNextCustomer = new TickableAction
                ("Spawning customer action",
                        SimulationUitilities.getRandomInt(settings.minTicksSpawnClient, settings.maxTicksSpawnClient));

        System.out.println(this + "next customer in " + spawnNextCustomer.getDuration() + " ticks");

        spawnNextCustomer.setOnFinishCallback(  () -> {
            //So we create new customer, place him into simulation
            Customer generatedCustomer = generateCustomer();
            generatedCustomer.onRestaurantEnter();
            this.restaurant.addGuestToQueue(generatedCustomer);
            System.out.println("SimMan: created customer!");
            createSpawnCustomerAction();
        });

        this.subscribeAction(spawnNextCustomer);

    }

    private void tick()
    {
        elapsedTicks++;
        System.out.println(this + "Tick("+ elapsedTicks +")! Duration: " + elapsedTime);

        if (!gameActions.isEmpty())
        {
            for (int i = gameActions.size() - 1; i >= 0; i--)
            {
                var action = gameActions.get(i);

                if(action.isToBeAborted())
                {
                    gameActions.remove(i);
                }
                //If action is done
                else if (action.getTicksToComplete() <= 1)
                {
                    action.executeOnFinishCallback();
                    if(action.isRepeatable())
                    {
                        //Renew action
                        action.setTicksToComplete(action.getDuration());
                    }
                    else
                        gameActions.remove(i);
                }
                //Else update action
                else
                {
                    action.decrementTicks();
                    action.executeOnUpdateCallback();
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
