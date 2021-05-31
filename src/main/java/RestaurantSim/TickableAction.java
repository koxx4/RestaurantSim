package RestaurantSim;

public class TickableAction
{
    public ITickableActionOnFinishCallback onFinishCallback;
    public ITickableActionOnUpdateCallback onUpdateCallback;
    private String name;
    private final int duration;
    private int ticksToComplete;
    private boolean abort;
    private boolean repeatable;


    public TickableAction(String name, int duration, boolean repeatable)
    {
        this.name = name;
        this.duration = duration;
        this.ticksToComplete = duration;
        this.repeatable = repeatable;
        abort = false;
    }
    public TickableAction(String name, int duration)
    {
        this(name, duration, false);
    }

    public TickableAction(int duration)
    {
        this("Unnamed action", duration, false);
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

    public boolean IsToBeAborted()
    {
        return this.abort;
    }
    public void AbortAction()
    {
        this.abort = true;
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
    public boolean IsRepeatable()
    {
        return this.repeatable;
    }
    public void SetRepeatable(boolean value)
    {
        this.repeatable = value;
    }
}
