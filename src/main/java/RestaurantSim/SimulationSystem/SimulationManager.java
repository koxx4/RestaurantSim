package RestaurantSim.SimulationSystem;

import RestaurantSim.*;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;

public class SimulationManager implements ITickableActionObject
{
    public static SimulationManager instance;
    private final SimulationSettings settings;
    private final PeopleData peopleData;
    private final FoodData foodData;
    private final TickManager tickManager;
    private final int tickDuration;
    private long elapsedTime;
    private long elapsedTicks;
    private boolean running;
    private Restaurant restaurant;
    private final StopWatch stopWatch;
    private final List<ITickableActionObject> tickableActionObjects;
    private final List<TickableAction> managerActions;

    public SimulationManager(SimulationSettings settings, SimulationData simulationData) {
        instance = this;
        this.running = true;
        this.tickDuration = settings.tickDuration;

        this.settings = settings;
        this.peopleData = simulationData.peopleData;
        this.foodData = simulationData.foodData;
        this.tickManager = new TickManager();
        this.managerActions = new ArrayList<>();

        stopWatch = new StopWatch();
        tickableActionObjects = new ArrayList<>();

        try {
            initialize();
        } catch (Exception e) {
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

    private List<Cook> generateAndRegisterCooks()
    {
        List<Cook> generatedCooks = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Cook newCook = new Cook(peopleData.getRandomFullName());
            generatedCooks.add(newCook);
            registerTickableActionObject(newCook);
        }

        return generatedCooks;
    }

    private void initialize()
    {
        //This might seem a little funny but yeah manager registers
        //itself
        registerTickableActionObject(this);
        createSpawnCustomerAction();

        restaurant = new Restaurant(new Menu(this.foodData), generateAndRegisterCooks());
        registerTickableActionObject(restaurant);
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
            //generatedCustomer.onRestaurantEnter();
            this.restaurant.addGuestToQueue(generatedCustomer);

            System.out.println("SimMan: created customer!");
            System.out.println(this  + "" +  generatedCustomer + "Arrived at restaurant. Gonna wait " +
                    generatedCustomer.getPatience() + " ticks before he'll leave!");

            createSpawnCustomerAction();
        });

        managerActions.add(spawnNextCustomer);

    }

    private void tick()
    {
        elapsedTicks++;
        System.out.println(this + "Tick("+ elapsedTicks +")! Duration: " + elapsedTime);

        if (!tickableActionObjects.isEmpty())
        {
            tickManager.updateTickableActionObjects(tickableActionObjects);
        }
    }

    private void registerTickableActionObject(ITickableActionObject object) {
        tickableActionObjects.add(object);
    }

    @Override
    public String toString()
    {
        return "SimMan: ";
    }

    @Override
    public List<TickableAction> getTickableActions() {
        return managerActions;
    }
}
