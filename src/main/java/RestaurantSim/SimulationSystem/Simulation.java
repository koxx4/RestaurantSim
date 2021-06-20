package RestaurantSim.SimulationSystem;

public class Simulation {
    private static Simulation instance;
    private final SimulationManager simulationManager;
    private final SimulationSettings simulationSettings;
    private final SimulationData simulationData;
    private final OutputDisplayProvider output;

    public Simulation(SimulationSettings simulationSettings, SimulationData data,
                      OutputDisplayProvider outputDisplayProvider ) {
        instance = this;
        this.simulationSettings = simulationSettings;
        this.simulationData = data;
        this.simulationManager = new SimulationManager(simulationSettings, data, outputDisplayProvider);
        this.output = outputDisplayProvider;
    }

    public void start(){
        this.simulationManager.initialize();
        this.simulationManager.startSimulation();
    }

    public void stop(){
        this.simulationManager.stop();
        this.output.printDebug("CLOSING!", "Simulation");
        try{Thread.sleep(3000);}catch ( Exception ignored ){};
        this.output.close();
    }

    public static Simulation getInstance() {
        return instance;
    }

    public SimulationSettings getSettings() {
        return simulationSettings;
    }

    public SimulationData getSimulationData() {
        return simulationData;
    }

    public OutputDisplayProvider getOutput() {
        return this.output;
    }

    public void printDebug(String msg, String author){
        this.output.printDebug(msg, author);
    }
    public void printDebug(String msg){
        this.output.printDebug(msg);
    }
    public void print(String msg, String author){
        this.output.print(msg, author);
    }
    public void print(String msg){
        this.output.print(msg);
    }
    public void printToRestaurantStatus( String msg){
        this.output.printRestaurantStatus(msg);
    }
    public void printToCooksStatus( String msg){
        this.output.printCooksStatus(msg);
    }
    public void printToTickableActionsStatus( String msg){
        this.output.printTickableActionsStatus(msg);
    }

}
