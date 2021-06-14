package RestaurantSim;

import java.util.List;

public class TickManager {

    public TickManager( ) {
    }

    public void updateTickableActionObjects( List<ITickableActionObject> tickableActionObjects ) {

        for ( var tickableActionObject: tickableActionObjects ) {

            var tickableActions = tickableActionObject.getTickableActions();

            if(tickableActions != null && !tickableActions.isEmpty())
                handleTickableActions(tickableActions);
        }

    }

    private void handleTickableActions( List<TickableAction> tickableActions ) {
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
}
