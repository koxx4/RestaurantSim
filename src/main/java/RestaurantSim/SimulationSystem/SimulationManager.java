package RestaurantSim.SimulationSystem;

import RestaurantSim.*;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;

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
                .withRandomAgility(settings.minCookAgility, settings.maxCookAgility)
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

    public void initialize()
    {
        tickManager.registerTickableObject(this);
        createSpawnCustomerAction();
        createRestaurantProtectionAction();

        restaurant = new Restaurant(new Menu(this.foodData), generateCooksList(settings.numberOfCooks));
        tickManager.registerTickableObject(restaurant);

    }

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

    private void createSpawnSanepidAction() {
        TickableAction action = new TickableAction(1);
        action.setOnFinishCallback( () -> {
            restaurant.addGuestToQueue(generateAndRegisterSanepid());
        });
        managerActions.add(action);
    }

    private void createRestaruantRateCheckAction() {

        output.printDebug("Next sanepid check in " + settings.restaruantRatesCheckFrequency + " ticks",
                this.toString());
        TickableAction action = new TickableAction(settings.restaruantRatesCheckFrequency, true);

        action.setOnFinishCallback( () -> {
            output.printDebug("Next sanepid check in " + settings.restaruantRatesCheckFrequency + " ticks",
                    this.toString());
            createSpawnSanepidAction();
        });

        managerActions.add(action);
    }

    private void createRestaurantProtectionAction(){
        output.printDebug("After " + settings.restaurantStartProtectionDuration + " ticks sanepid will be able" +
                " to check restaurant. Prepare!", this.toString() );

        TickableAction action = new TickableAction(settings.restaurantStartProtectionDuration);
        action.setOnFinishCallback(this::createRestaruantRateCheckAction);

        managerActions.add(action);
    }

    private void tick() {
        tickManager.tick();
        output.printDebug( "Ticked! This is tick number "
                + tickManager.getElapsedTicks(), this.toString());

        if(!restaurant.isOpened() || !output.isOpened()){
            stop();
        }

    }

}
