package RestaurantSim.SimulationSystem;

public interface OutputDisplayProvider {
    void printDebug(String msg, String author);
    void printDebug(String msg);
    void print(String msg, String author);
    void print(String msg);
    void printRestaurantStatus( String msg);
    void printCooksStatus( String msg);
    void printTickableActionsStatus( String msg);
    void close();
    boolean isOpened();
}
