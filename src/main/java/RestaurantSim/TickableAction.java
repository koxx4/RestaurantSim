package RestaurantSim;

public class TickableAction implements ITickableActionOnFinishCallback,ITickableActionOnUpdateCallback
{
    String name;
    int duration;
    int ticksToComplete;

    public TickableAction(String name, int duration)
    {
        this.name=name;
        this.duration=duration;
    }
    public TickableAction( int ticksToComplete)
    {
        this.ticksToComplete=ticksToComplete;

    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getTicksToComplete() {
        return ticksToComplete;
    }

    public void setTicksToComplete(int ticksToComplete) {
        this.ticksToComplete = ticksToComplete;
    }

    @Override
    public void Execute() {

    }

}
