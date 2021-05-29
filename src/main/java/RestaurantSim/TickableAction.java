package RestaurantSim;

public class TickableAction
{
    public ITickableActionOnFinishCallback onFinishCallback;
    public ITickableActionOnUpdateCallback onUpdateCallback;
    private String name;
    private int duration;
    private int ticksToComplete;


    public TickableAction(String name, int duration)
    {
        this.name = name;
        this.duration = duration;
    }
    public TickableAction( int ticksToComplete)
    {
        this.ticksToComplete=ticksToComplete;

    }

    public String GetName() {
        return name;
    }

    public int GetDuration() {
        return duration;
    }

    public int GetTicksToComplete() {
        return ticksToComplete;
    }

    public void SetTicksToComplete(int ticksToComplete) {
        this.ticksToComplete = ticksToComplete;
    }

    public void DecrementTicks()
    {
        ticksToComplete--;
    }

    public void ExecuteOnFinishCallback()
    {
        if(onFinishCallback != null)
        {
            onFinishCallback.Execute();
        }
    }

    public void ExecuteOnUpdateCallback()
    {
        if(onUpdateCallback != null)
        {
            onUpdateCallback.Execute();
        }
    }
}
