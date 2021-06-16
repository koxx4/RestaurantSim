package RestaurantSim.SimulationSystem;

public class BuilderNotInitializedException extends Exception{
    public BuilderNotInitializedException(){
        super("Tried to get built object before starting building");
    }

    public BuilderNotInitializedException(String msg){
        super(msg);
    }
}
