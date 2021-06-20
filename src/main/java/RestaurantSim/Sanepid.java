package RestaurantSim;

import RestaurantSim.SimulationSystem.*;

import java.util.ArrayList;
import java.util.List;

public class Sanepid extends RestaurantGuest  {

    private final List<TickableAction> tickableActions;
    private final IEvaluationService evaluationService;
    private boolean shouldBeUnregistered;
    private String[] sanepidQuotes =
            {       "...there are rats!... ",
                    "...those tables are dirty, too warm in fridge...",
                    "...what a disgusting smell..."
            };

    /**
     * Makes new Sanepid object
     * @param name Contains the name of Sanepid object
     * @param evaluationService Evaluates the Restaurant
     */
    public Sanepid(String name, IEvaluationService evaluationService, Restaurant target) {
        super(name, Integer.MAX_VALUE, target);
        this.tickableActions = new ArrayList<>();
        this.evaluationService = evaluationService;
    }

    @Override
    public void interactWithRestaurant() {
        Simulation.getInstance().getOutput().print( "HELLO! Please show me condition of restaurant!"
                , this.toString());

        createEvaluationAction();
        evaluateRestaurantWork();
    }

    @Override
    public void receiveOrder( PreparedOrder preparedOrder) {

    }

    @Override
    public List<TickableAction> getTickableActions() {
        return tickableActions;
    }

    @Override
    public boolean shouldBeUnregistered() {
        return shouldBeUnregistered;
    }

    @Override
    public String toString() {
        return "Sanepid (" + super.getName() + "): ";
    }

    /**
     * Evaluates whether Restaurant should be closed or not
     */
    private void evaluateRestaurantWork() {
        if(evaluationService.restaurantShouldBeClosed(getTargetRestaurant())){
            Simulation.getInstance().print("This place is too dangerous to eat!\n Close this garbage NOW!");
            getTargetRestaurant().close(this);
        }
    }

    /**
     * Creates tickable actions for Sanepid object and removes it from the queue under some circumstances
     */
    private void createEvaluationAction(){
        TickableAction action = new TickableAction(Simulation.getInstance().getSettings().sanepidEvaluationTime );
        action.setOnFinishCallback(() -> {
            evaluateRestaurantWork();
            getTargetRestaurant().removeGuestFromQueue(this);
            shouldBeUnregistered = true;
        });
        action.setOnTickCallback(this::printRandomEvalutaionMessage);

        tickableActions.add(action);

    }

    /**
     * Prints random message while Sanepid object is in the Restaurant
     */
    private void printRandomEvalutaionMessage() {
        int random = SimulationUtilities.getRandomInt(0, sanepidQuotes.length-1);
        Simulation.getInstance().getOutput().print(sanepidQuotes[random], this.toString());
    }

}
