package RestaurantSim;

import RestaurantSim.SimulationSystem.IEvaluationService;
import RestaurantSim.SimulationSystem.SimulationManager;
import RestaurantSim.SimulationSystem.SimulationUitilities;
import RestaurantSim.SimulationSystem.TickableAction;

import java.util.ArrayList;
import java.util.List;

public class Sanepid extends RestaurantGuest  {

    private final List<TickableAction> tickableActions;
    private Restaurant sourceRestaurant;
    private final IEvaluationService evaluationService;
    private boolean shouldBeUnregistered;
    private String[] sanepidQuotes = {"...macie tu szczury!... ", "...no te blaty takie troche brudne, w lodowce za cieplo...", "...straszny smród..."};

    public Sanepid(String name, IEvaluationService evaluationService) {
        super(name, Integer.MAX_VALUE);
        this.tickableActions = new ArrayList<>();
        this.evaluationService = evaluationService;
    }


    @Override
    public void interactWithRestaurant( Restaurant restaurant) {
        sourceRestaurant = restaurant;

        System.out.println(this + "kontrola! Prosze pokazac stan lokalu i kuchni!");
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

    private void evaluateRestaurantWork() {
        if(evaluationService.restaurantShouldBeClosed(sourceRestaurant)){
            sourceRestaurant.close(this);
        }
    }

    private void createEvaluationAction(){
        TickableAction action = new TickableAction(SimulationManager.instance.getSettings().sanepidEvaluationTime );
        action.setOnFinishCallback(() -> {
            evaluateRestaurantWork();
            sourceRestaurant.removeGuestFromQueue(this);
            shouldBeUnregistered = true;
        });
        action.setOnTickCallback(this::printRandomEvalutaionMessage);

        tickableActions.add(action);

    }

    private void printRandomEvalutaionMessage() {
        int random = SimulationUitilities.getRandomInt(0, sanepidQuotes.length-1);

        System.out.println(this + sanepidQuotes[random]);
    }

}
