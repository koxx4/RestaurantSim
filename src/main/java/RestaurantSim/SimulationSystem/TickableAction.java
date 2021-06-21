package RestaurantSim.SimulationSystem;


/**
 * This class describes an action that spans some amount of ticks and also
 * provides ways to define a behaviour on when this action is finished or is
 * being updated. Every TickableAction has duration, can have a description and
 * can be aborted at any time. Action can also be marked as repeatable,
 * which means that action will be recreated when it is finished.
 */
public class TickableAction
{
    /**
     * Callback executed when action is finished
     */
    private ITickableActionFinishCallback onFinishCallback;
    /**
     * Callback executed when action is ticked (updated)
     */
    private ITickableActionTickCallback onTickCallback;
    /**
     * Brief description of what this action does
     */
    private final String description;
    /**
     * Duration in ticks of how long this action takes to complete
     */
    private final int duration;
    /**
     * Number of ticks left to finish this action
     */
    private int ticksToComplete;
    /**
     * Boolean indicating that this action should be aborted (not allowed in Poland 🤓👌)
     */
    private boolean abort;
    /**
     * Boolean indicating that this action is repeatable and should be recreated when it is finished
     */
    private boolean repeatable;

    /**
     * Creates a TickableAction objects
     * @param description Brief description of this action
     * @param duration Duration in ticks of how long this action takes to complete
     * @param repeatable Flag indicating that this action should be recreated after it is finished
     */
    public TickableAction( String description, int duration, boolean repeatable) {
        this.description = description;
        this.duration = duration;
        this.ticksToComplete = duration;
        this.repeatable = repeatable;
        abort = false;
    }
    /**
     * Creates a TickableAction objects
     * @param description Brief description of this action
     * @param duration Duration in ticks of how long this action takes to complete
     */
    public TickableAction( String description, int duration) {
        this(description, duration, false);
    }

    /**
     * Creates a TickableAction objects
     * @param duration Duration in ticks of how long this action takes to complete
     */
    public TickableAction(int duration) {
        this("Unnamed action", duration, false);
    }
    /**
     * Creates a TickableAction objects
     * @param duration Duration in ticks of how long this action takes to complete
     * @param repeatable Flag indicating that this action should be recreated after it is finished
     */
    public TickableAction(int duration, boolean repeatable) {
        this("Unnamed action", duration, repeatable);
    }

    /**
     * Returns a description of this action
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a duration of this action
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns an amount of ticks left to finish this action
     * @return
     */
    public int getTicksToComplete() {
        return ticksToComplete;
    }

    /**
     * Sets amount of ticks left to complete this aciton
     */
    public void setTicksToComplete( int ticksToComplete) {
        this.ticksToComplete = ticksToComplete;
    }

    /**
     * Decrements amount of ticks left to finish this action
     */
    public void decrementTicks(){
        ticksToComplete--;
    }

    /**
     * Returns a flag indicating whether this action should be aborted
     * @return
     */
    public boolean isToBeAborted() {
        return this.abort;
    }

    /**
     * Marks this action to be aborted
     */
    public void abort() {
        this.abort = true;
    }

    /**
     * Executes callback that should be fired when this action finishes
     */
    public void executeOnFinishCallback() {
        if(onFinishCallback != null) {
            onFinishCallback.execute();
        }
    }

    /**
     * Executes callback that should be fired when this action is ticked (updated)
     */
    public void executeOnUpdateCallback() {
        if(onTickCallback != null)
        {
            onTickCallback.execute();
        }
    }

    /**
     * Returns flag indicating whether this action is repeatable
     * @return
     */
    public boolean isRepeatable() {
        return this.repeatable;
    }

    /**
     * Sets this action as repeatable
     * @param value
     */
    public void setRepeatable( boolean value) {
        this.repeatable = value;
    }

    /**
     * Sets callback that should be executed when this action finishes
     * @param onFinishCallback
     */
    public void setOnFinishCallback( ITickableActionFinishCallback onFinishCallback) {
        this.onFinishCallback = onFinishCallback;
    }


    /**
     * Sets callback that should be executed when this action is ticked (updated)
     * @param onTickCallback
     */
    public void setOnTickCallback( ITickableActionTickCallback onTickCallback) {
        this.onTickCallback = onTickCallback;
    }
}
