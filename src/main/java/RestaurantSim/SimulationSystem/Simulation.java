package RestaurantSim.SimulationSystem;

/**
 * Class that wraps all the necessary components to run and manage the simulation
 */
public class Simulation {
    /**
     * A instance of this class. Only one at a time can exist.
     */
    private static Simulation instance;
    private final SimulationManager simulationManager;
    private final SimulationSettings simulationSettings;
    private final SimulationData simulationData;
    private final OutputDisplayProvider output;

    /**
     * Creates Simulation objects but don't starts it.
     * @param simulationSettings Settings to use in simulation
     * @param data Data to use in simulation
     * @param outputDisplayProvider Output to which simulation will print various information about its state
     */
    public Simulation(SimulationSettings simulationSettings, SimulationData data,
                      OutputDisplayProvider outputDisplayProvider ) {
        instance = this;
        this.simulationSettings = simulationSettings;
        this.simulationData = data;
        this.simulationManager = new SimulationManager(simulationSettings, data, outputDisplayProvider);
        this.output = outputDisplayProvider;
    }

    /**
     * Initializes simulation and starts it
     */
    public void start(){
        this.simulationManager.initialize();
        this.simulationManager.startSimulation();
    }

    /**
     * Stops this simulation and output that it is using
     */
    public void stop(){
        this.simulationManager.stop();
        this.output.printDebug("CLOSING!", "Simulation");
        try{Thread.sleep(3000);}catch ( Exception ignored ){};
        this.output.close();
    }

    /**
     * Returns reference to an instance of simulation
     * @return Instance of simulation
     */
    public static Simulation getInstance() {
        return instance;
    }

    /**
     * Returns active settings that simulation is using
     * @return Settings of a simulation
     */
    public SimulationSettings getSettings() {
        return simulationSettings;
    }

    /**
     * Returns reference to data that simulation is using
     * @return Simulation data
     */
    public SimulationData getSimulationData() {
        return simulationData;
    }

    /**
     * Returns reference to output which simulation is using
     * @return Simulation active output
     */
    public OutputDisplayProvider getOutput() {
        return this.output;
    }

    /**
     * Wraps {@link OutputDisplayProvider} method for less verbose access.
     * @param msg Message to print
     * @param author Author of the message
     */
    public void printDebug(String msg, String author){
        this.output.printDebug(msg, author);
    }
    /**
     * Wraps {@link OutputDisplayProvider} method for less verbose access.
     * @param msg Message to print
     */
    public void printDebug(String msg){
        this.output.printDebug(msg);
    }
    /**
     * Wraps {@link OutputDisplayProvider} method for less verbose access.
     * @param msg Message to print
     * @param author Author of the message
     */
    public void print(String msg, String author){
        this.output.print(msg, author);
    }
    /**
     * Wraps {@link OutputDisplayProvider} method for less verbose access.
     * @param msg Message to print
     */
    public void print(String msg){
        this.output.print(msg);
    }
    /**
     * Wraps {@link OutputDisplayProvider} method for less verbose access.
     * @param msg Message to print
     */
    public void printToRestaurantStatus( String msg){
        this.output.printRestaurantStatus(msg);
    }
    /**
     * Wraps {@link OutputDisplayProvider} method for less verbose access.
     * @param msg Message to print
     */
    public void printToCooksStatus( String msg){
        this.output.printCooksStatus(msg);
    }
    /**
     * Wraps {@link OutputDisplayProvider} method for less verbose access.
     * @param msg Message to print
     */
    public void printToTickableActionsStatus( String msg){
        this.output.printTickableActionsStatus(msg);
    }

}
