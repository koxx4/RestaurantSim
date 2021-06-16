package RestaurantSim.SimulationSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TickManager {

    private final List<ITickableActionObject> tickableActionObjects;
    private final List<ITickableActionObject> tickableActionObjectsBuffer;

    public long getElapsedTicks() {
        return elapsedTicks;
    }

    public void setElapsedTicks( long elapsedTicks ) {
        this.elapsedTicks = elapsedTicks;
    }

    private long elapsedTicks = 0;

    public TickManager() {
        tickableActionObjects = new ArrayList<>();
        tickableActionObjectsBuffer = new ArrayList<>();
    }

    public void registerTickableObject(ITickableActionObject tickableActionObject){
        tickableActionObjectsBuffer.add(tickableActionObject);
    }

    public void tick() {

        elapsedTicks++;
        for ( var tickableActionObject : tickableActionObjects) {
            var tickableActions = tickableActionObject.getTickableActions();

            if(tickableActions != null && !tickableActions.isEmpty())
                updateTickableActions(tickableActions);
        }
        synchronizeTickableActionObjects();
        cleanupTickableActionObjects();
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

    private void handleActionUpdate( TickableAction action ) {
        action.decrementTicks();
        action.executeOnUpdateCallback();
    }

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

    private void synchronizeTickableActionObjects(){
        if(!tickableActionObjectsBuffer.isEmpty()){
            tickableActionObjects.addAll(tickableActionObjectsBuffer);
            tickableActionObjectsBuffer.clear();
        }
    }

    private void cleanupTickableActionObjects(){
        //I mean it really seems odd but we need to
        //do some garbage collection job

        var tickablesToRemove = tickableActionObjects.stream()
                .filter(ITickableActionObject::shouldBeUnregistered)
                .collect(Collectors.toList());
        System.out.println(this + " removing: " + tickablesToRemove.size() + " tickables");
        tickableActionObjects.removeAll(tickablesToRemove);

    }

    @Override
    public String toString() {
        return "TickManager: ";
    }
}
