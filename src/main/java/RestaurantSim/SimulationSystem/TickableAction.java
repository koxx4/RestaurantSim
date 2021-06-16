package RestaurantSim.SimulationSystem;

public class TickableAction
{
    private ITickableActionFinishCallback onFinishCallback;
    private ITickableActionTickCallback onTickCallback;
    private final String description;
    private final int duration;
    private int ticksToComplete;
    private boolean abort;
    private boolean repeatable;


    public TickableAction( String description, int duration, boolean repeatable)
    {
        this.description = description;
        this.duration = duration;
        this.ticksToComplete = duration;
        this.repeatable = repeatable;
        abort = false;
    }
    public TickableAction( String description, int duration)
    {
        this(description, duration, false);
    }

    public TickableAction(int duration)
    {
        this("Unnamed action", duration, false);
    }
    public TickableAction(int duration, boolean repeatable)
    {
        this("Unnamed action", duration, repeatable);
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public int getTicksToComplete() {
        return ticksToComplete;
    }

    public void setTicksToComplete( int ticksToComplete) {
        this.ticksToComplete = ticksToComplete;
    }

    public void decrementTicks()
    {
        ticksToComplete--;
    }

    public boolean isToBeAborted()
    {
        return this.abort;
    }
    public void abort()
    {
        this.abort = true;
    }

    public void executeOnFinishCallback()
    {
        if(onFinishCallback != null)
        {
            onFinishCallback.execute();
        }
    }

    public void executeOnUpdateCallback()
    {
        if(onTickCallback != null)
        {
            onTickCallback.execute();
        }
    }
    public boolean isRepeatable()
    {
        return this.repeatable;
    }

    public void setRepeatable( boolean value)
    {
        this.repeatable = value;
    }

    public void setOnFinishCallback( ITickableActionFinishCallback onFinishCallback)
    {
        this.onFinishCallback = onFinishCallback;
    }

    public void setOnTickCallback( ITickableActionTickCallback onTickCallback)
    {
        this.onTickCallback = onTickCallback;
    }
}
