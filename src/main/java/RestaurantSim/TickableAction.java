package RestaurantSim;

public class TickableAction
{
    public ITickableActionOnFinishCallback onFinishCallback;
    public ITickableActionOnUpdateCallback onUpdateCallback;
    private String name;
    private final int duration;
    private int ticksToComplete;
    private boolean abort;


    public TickableAction(String name, int duration)
    {
        this.name = name;
        this.duration = duration;
        this.ticksToComplete = duration;
        abort = false;
    }

    public TickableAction(int duration)
    {
        this("Unnamed action", duration);
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
}
