package RestaurantSim.SimulationSystem;

import RestaurantSim.*;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages simulation 🤓👌
 */
class SimulationManager implements ITickableActionObject {

    private  final OutputDisplayProvider output;
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

    /**
     * Creates instance of SimulationManager with all its dependencies
     * @param settings Loaded simulation settings
     * @param simulationData Loaded simulation data
     * @param output Output to which SimulationManager will print various information
     */
    public SimulationManager(SimulationSettings settings, SimulationData simulationData,
                             OutputDisplayProvider output) {
        this.running = true;
        this.tickDuration = settings.tickDuration;
        this.settings = settings;
        this.peopleData = simulationData.peopleData;
        this.output = output;
        this.foodData = simulationData.foodData;
        this.tickManager = new TickManager();
        this.managerActions = new ArrayList<>();
        stopWatch = new StopWatch();
    }

    /**
     * Indicates whether simulation is running
     * @return True when simulation is running, false - when it is not 🤓👌
     */
    public boolean isRunning()
    {
        return this.running;
    }

    /**
     * Starts whole simulation with tick duration that is
     * defined in SimulationSettings class passed in constructor
     */
    public void startSimulation() {
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

    /**
     * Stops simulation regardless of its state
     */
    public void stop() {
        output.printDebug("Stopping simulation!", this.toString());
        this.running = false;
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

    /**
     * Creates new Customer object and registers it to TickableObjects list
     * @return Newly created Customer
     */
    private Customer generateAndRegisterCustomer()
    {
        CustomerBuilder customerBuilder = new CustomerBuilder();

        Customer builtCustomer = customerBuilder.buildCustomerAssignedToRestaurant(this.restaurant)
                .named(peopleData.getRandomFullName())
                .withRandomPatience(settings.minClientPatience, settings.maxClientPatience)
                .withRater(new QualityBasedOrderRater())
                .getBuiltCustomer();

        assert builtCustomer != null;

        tickManager.registerTickableObject(builtCustomer);
        return builtCustomer;
    }
    /**
     * Creates new Sanepid object and registers it to TickableObjects list
     * @return Newly created Sanepid
     */
    private Sanepid generateAndRegisterSanepid() {
        Sanepid sanepid = new Sanepid(peopleData.getRandomFullName(),
                new RateBasedEvaluationService(),
                restaurant);
        tickManager.registerTickableObject(sanepid);

        return sanepid;
    }

    /**
     * Creates new Cook object and registers it to TickableObjects list
     * @return Newly created Cook
     */
    private Cook generateAndRegisterCook()
    {
        CookBuilder cookBuilder = new CookBuilder();

        Cook cook = cookBuilder.buildCook()
                .named(peopleData.getRandomFullName())
                .withRandomAgility(settings.minCookAgility, settings.maxCookAgility)
                .withRandomSkillLevel(settings.minCookSkill, settings.maxCookSkill)
                .withOrderQualityDeterminer(new SkillBasedQualityDeterminer())
                .getBuiltCook();

        assert cook != null;

        tickManager.registerTickableObject(cook);
        return cook;
    }

    /**
     * Generates Cook objects and construct new List object
     * containing created cooks.
     * @param listSize Size of List that will be created
     * @return List of newly created cooks
     */
    private List<Cook> generateCooksList(int listSize){
        List<Cook> cooks = new ArrayList<>();

        for(int i = 0; i < listSize; i++){
            cooks.add(generateAndRegisterCook());
        }

        return cooks;
    }

    /**
     * Initializes SimulationManager
     */
    public void initialize()
    {
        tickManager.registerTickableObject(this);
        createSpawnCustomerAction();
        createRestaurantProtectionAction();

        restaurant = new Restaurant(new Menu(this.foodData), generateCooksList(settings.numberOfCooks));
        tickManager.registerTickableObject(restaurant);

    }

    /**
     * Creates TickableAction that will periodically spawn Customer object
     */
    private void createSpawnCustomerAction() {
        TickableAction spawnNextCustomer = new TickableAction
                ("Spawning customer action",
                        SimulationUtilities.getRandomInt(settings.minTicksSpawnClient, settings.maxTicksSpawnClient));

        output.printDebug("Next customer in " + spawnNextCustomer.getDuration() + " ticks", this.toString());

        spawnNextCustomer.setOnFinishCallback(  () -> {
            //So we create new customer, place him into simulation
            Customer generatedCustomer = generateAndRegisterCustomer();
            //generatedCustomer.onRestaurantEnter();
            this.restaurant.addGuestToQueue(generatedCustomer);

            output.printDebug("Created customer!",this.toString());
            output.printDebug(generatedCustomer + " arrived at restaurant.\nGonna wait " +
                    generatedCustomer.getPatience() + " ticks before he'll leave!", this.toString());

            createSpawnCustomerAction();
        });

        managerActions.add(spawnNextCustomer);

    }

    /**
     * Creates TickableAction that will periodically spawn Sanepid object
     */
    private void createSpawnSanepidAction() {
        TickableAction action = new TickableAction("Spawning sanepid",1);
        action.setOnFinishCallback( () -> {
            restaurant.addGuestToQueue(generateAndRegisterSanepid());
        });
        managerActions.add(action);
    }

    /**
     * Creates TickableAction that will periodically spawn
     * TickableAction that creates Sanepid object
     */
    private void createRestaruantRateCheckAction() {

        output.printDebug("Next sanepid check in " + settings.restaruantRatesCheckFrequency + " ticks",
                this.toString());
        TickableAction action = new TickableAction("Restaurant rate check"
                ,settings.restaruantRatesCheckFrequency, true);

        action.setOnFinishCallback( () -> {
            output.printDebug("Next sanepid check in " + settings.restaruantRatesCheckFrequency + " ticks",
                    this.toString());
            createSpawnSanepidAction();
        });

        managerActions.add(action);
    }

    /**
     * Creates a TickableAction at which finish sanepid checks will begin
     */
    private void createRestaurantProtectionAction(){
        output.printDebug("After " + settings.restaurantStartProtectionDuration + " ticks sanepid will be able" +
                " to check restaurant. Prepare!", this.toString() );

        TickableAction action = new TickableAction("Restaurant initial protection",
                settings.restaurantStartProtectionDuration);
        action.setOnFinishCallback(this::createRestaruantRateCheckAction);

        managerActions.add(action);
    }

    /**
     * Ticks simulation and pushes it forward in time. Updates
     * all TickableActions. Time between each tick is defined in
     * SimulationSettings.
     */
    private void tick() {
        tickManager.tick();
        output.printDebug( "Ticked! This is tick number "
                + tickManager.getElapsedTicks(), this.toString());

        if(!restaurant.isOpened() || !output.isOpened()){
            stop();
        }

    }

}
