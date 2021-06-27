package RestaurantSim.SimulationSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class manages ticking, objects that implement {@link ITickableActionObject} interface and
 * updates and their TickableActions.
 */
 class TickManager {

    /**
     * List of object implementing {@link ITickableActionObject} interface
     * that are registered and which will be updated
     */
    private final List<ITickableActionObject> tickableActionObjects;
    /**
     * A buffer that is synchronized at the end of each tick with main list of
     * {@link ITickableActionObject} objects. When a new {@link ITickableActionObject}
     * is to be subscribed it will be added to this list and then at the end of current tick
     * moved to tickableActionObjects list.
     */
    private final List<ITickableActionObject> tickableActionObjectsBuffer;

    /**
     * Total amount of ticks that elapsed since start of the simulation.
     */
    private long elapsedTicks = 0;

    /**
     * Main constructor.
     */
    public TickManager() {
        tickableActionObjects = new ArrayList<>();
        tickableActionObjectsBuffer = new ArrayList<>();
    }

    /**
     * Returns total amount of ticks that elapsed since start of the simulation.
     * @return
     */
    public long getElapsedTicks() {
        return elapsedTicks;
    }

    /**
     * Registers a new tickableActionObject and initially adds it
     * to the buffer.
     * @param tickableActionObject
     */
    public void registerTickableObject(ITickableActionObject tickableActionObject){
        tickableActionObjectsBuffer.add(tickableActionObject);
    }

    /**
     * Ticks, updates all tickable objects and its tickabel actions, increments
     * internal total ticks counter that have elapsed since simulation start.
     * Unfollows tickable game objects that don't want to be registered anymore.
     * Also prints active tickable actions statuses.
     * @see this#printTickableActions()
     * @see this#synchronizeTickableActionObjects()
     * @see this#cleanupTickableActionObjects()
     */
    public void tick() {

        elapsedTicks++;
        for ( var tickableActionObject : tickableActionObjects) {
            var tickableActions = tickableActionObject.getTickableActions();

            if(tickableActions != null && !tickableActions.isEmpty())
                updateTickableActions(tickableActions);
        }
        synchronizeTickableActionObjects();
        cleanupTickableActionObjects();
        printTickableActions();
    }

    private void updateTickableActions( List<TickableAction> tickableActions ) {
        for ( int i = tickableActions.size() - 1; i >= 0; i--)
        {
            var action = tickableActions.get(i);

            if(action.isToBeAborted()) {
                tickableActions.remove(i);
            }
            //If action is done
            else if (action.getTicksToComplete() <= 1) {
                handleActionFinish(tickableActions, action);
            }
            //Else update action
            else {
                handleActionUpdate(action);
            }

        }
    }
    /**
     * Manages an TickableAction that is being ticked (updated).
     * @param action Action that is being updated
     */
    private void handleActionUpdate( TickableAction action ) {
        action.decrementTicks();
        action.executeOnUpdateCallback();
    }

    /**
     * Manages an TickableAction that should finish.
     * @param tickableActions List of all TickableActions that specific ITickableActionObject has.
     * @see ITickableActionObject#getTickableActions()
     * @param action Action that is being finished
     */
    private void handleActionFinish( List<TickableAction> tickableActions, TickableAction action ) {

        action.executeOnFinishCallback();

        if( action.isRepeatable()) {
            //Renew action
            action.setTicksToComplete(action.getDuration());
        }
        else {
            tickableActions.remove(action);
        }
    }

    /**
     * This function moves elements from tickableActionObjects buffer
     * to the proper main list of tickableActionObjects
     */
    private void synchronizeTickableActionObjects(){
        if(!tickableActionObjectsBuffer.isEmpty()){
            tickableActionObjects.addAll(tickableActionObjectsBuffer);
            tickableActionObjectsBuffer.clear();
        }
    }

    /**
     * Removes tickableActionObjects, that are marked to be
     * unregistered, from the main list.
     * @see ITickableActionObject#shouldBeUnregistered()
     */
    private void cleanupTickableActionObjects(){
        //I mean it really seems odd but we need to
        //do some garbage collection job
        var tickablesToRemove = tickableActionObjects.stream()
                .filter(ITickableActionObject::shouldBeUnregistered)
                .collect(Collectors.toList());

        tickableActionObjects.removeAll(tickablesToRemove);
    }

    /**
     * Prints active tickable actions that this class has access into, using
     * output provided to simulation.
     * @see OutputDisplayProvider#printTickableActionsStatus(String)
     */
    private void printTickableActions() {
        StringBuilder stringBuilder = new StringBuilder();
        for ( var tickableObj : tickableActionObjects ){
            for ( var actions : tickableObj.getTickableActions() ){
                stringBuilder.append("[\u25A3" + actions.getDescription() + ", ticksLeft: "
                        + actions.getTicksToComplete() + "]\n")      ;
            }
        }
        Simulation.getInstance().printToTickableActionsStatus(stringBuilder.toString());
    }
    @Override
    public String toString() {
        return "TickManager: ";
    }
}
