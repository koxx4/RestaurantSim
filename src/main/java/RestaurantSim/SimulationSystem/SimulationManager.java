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
    private boolean running;
    private Restaurant restaurant;
    private final StopWatch stopWatch;
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

    @Override
    public String toString() {
        return "SimMan: ";
    }

    @Override
    public List<TickableAction> getTickableActions() {
        return managerActions;
    }

    @Override
    public boolean shouldBeUnregistered() {
        return false;
    }

    private Customer generateAndRegisterCustomer()
    {
        CustomerBuilder customerBuilder = new CustomerBuilder();

        Customer builtCustomer = customerBuilder.buildCustomer()
                .named(peopleData.getRandomFullName())
                .withRandomPatience(settings.minClientPatience, settings.maxClientPatience)
                .withRater(new QualityBasedOrderRater())
                .getBuiltCustomer();

        assert builtCustomer != null;

        tickManager.registerTickableObject(builtCustomer);
        return builtCustomer;
    }
    private Sanepid generateAndRegisterSanepid() {
        Sanepid sanepid = new Sanepid(peopleData.getRandomFullName(), new RateBasedEvaluationService());
        tickManager.registerTickableObject(sanepid);

        return sanepid;
    }

    private Cook generateAndRegisterCook()
    {
        CookBuilder cookBuilder = new CookBuilder();

        Cook cook = cookBuilder.buildCook()
                .named(peopleData.getRandomFullName())
                .withRandomAgility(settings.maxCookAgility, settings.maxCookAgility)
                .withRandomSkillLevel(settings.minCookSkill, settings.maxCookSkill)
                .withOrderQualityDeterminer(new SkillBasedQualityDeterminer())
                .getBuiltCook();

        assert cook != null;

        tickManager.registerTickableObject(cook);
        return cook;
    }

    private List<Cook> generateCooksList(int listSize){
        List<Cook> cooks = new ArrayList<>();

        for(int i = 0; i < listSize; i++){
            cooks.add(generateAndRegisterCook());
        }

        return cooks;
    }

    private void initialize()
    {
        //This might seem a little funny but yeah manager registers
        //itself
        tickManager.registerTickableObject(this);
        createSpawnCustomerAction();
        createRestaurantProtectionAction();

        restaurant = new Restaurant(new Menu(this.foodData), generateCooksList(settings.numberOfCooks));
        tickManager.registerTickableObject(restaurant);

    }

    private void createSpawnCustomerAction()
    {
        TickableAction spawnNextCustomer = new TickableAction
                ("Spawning customer action",
                        SimulationUitilities.getRandomInt(settings.minTicksSpawnClient, settings.maxTicksSpawnClient));

        System.out.println(this + "next customer in " + spawnNextCustomer.getDuration() + " ticks");

        spawnNextCustomer.setOnFinishCallback(  () -> {
            //So we create new customer, place him into simulation
            Customer generatedCustomer = generateAndRegisterCustomer();
            //generatedCustomer.onRestaurantEnter();
            this.restaurant.addGuestToQueue(generatedCustomer);

            System.out.println("SimMan: created customer!");
            System.out.println(this  + "" +  generatedCustomer + "Arrived at restaurant. Gonna wait " +
                    generatedCustomer.getPatience() + " ticks before he'll leave!");

            createSpawnCustomerAction();
        });

        managerActions.add(spawnNextCustomer);

    }
    private void createSpawnSanepidAction() {
        TickableAction action = new TickableAction(1);
        action.setOnFinishCallback( () -> {

            restaurant.addGuestToQueue(generateAndRegisterSanepid());

        });
        managerActions.add(action);
    }

    private void createRestaruantRateCheckAction() {

        System.out.println(this + "next sanepid check in " + settings.restaruantRatesCheckFrequency + " ticks");
        TickableAction action = new TickableAction(settings.restaruantRatesCheckFrequency, true);

        action.setOnFinishCallback( () -> {

            System.out.println(this + "next sanepid check in " + settings.restaruantRatesCheckFrequency + " ticks");
            createSpawnSanepidAction();

        });

        managerActions.add(action);
    }

    private void createRestaurantProtectionAction(){
        System.out.println(this + "after " + settings.restaurantStartProtectionDuration + " ticks sanepid will be able" +
                " to check restaurant. Prepare!" );

        TickableAction action = new TickableAction(settings.restaurantStartProtectionDuration);
        action.setOnFinishCallback(this::createRestaruantRateCheckAction);

        managerActions.add(action);
    }

    private void tick()
    {
        tickManager.tick();
        System.out.println(this + "Tick("+ tickManager.getElapsedTicks() +")! Duration: " + elapsedTime);

        if(!restaurant.isOpened()){
            stop();
        }

    }
}
