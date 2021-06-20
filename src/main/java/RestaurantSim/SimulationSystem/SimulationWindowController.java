package RestaurantSim.SimulationSystem;

import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Label;

import java.util.ArrayList;
import java.util.List;

class SimulationWindowController {
    private final int DEBUG_MAX_LABELS = 10;
    private final int PRINT_MAX_LABELS = 12;
    private final SimulationWindow controlledWindow;
    private List<Component> debugLables;
    private List<Component> printLabels;

    public SimulationWindowController( SimulationWindow controlledWindow ) {
        this.controlledWindow = controlledWindow;
        debugLables = new ArrayList<>(DEBUG_MAX_LABELS);
        printLabels = new ArrayList<>(PRINT_MAX_LABELS);
    }

    public void printToDebugView(String msg, String author){
        Component messageLabel = new Label(msg).withBorder(Borders.singleLine(author));
        debugLables.add(messageLabel);

        controlledWindow.getSimulationDebugViewPanel()
                .addComponent(messageLabel);
        checkDebugLabelsForOverflow();
    }

    public void printToDebugView(String msg){
        Component messageLabel = new Label(msg).withBorder(Borders.singleLine());
        debugLables.add(messageLabel);

        controlledWindow.getSimulationDebugViewPanel()
                .addComponent(messageLabel);
        checkDebugLabelsForOverflow();
    }

    public void printToMainView(String msg, String author){
        Component messageLabel = new Label(msg).withBorder(Borders.singleLine(author));
        printLabels.add(messageLabel);

        controlledWindow.getSimulationViewPanel()
                .addComponent(messageLabel);
        checkPrintLabelsForOverflow();
    }

    public void printToMainView(String msg){
        Component messageLabel = new Label(msg).withBorder(Borders.singleLine());
        printLabels.add(messageLabel);

        controlledWindow.getSimulationViewPanel()
                .addComponent(messageLabel);
        checkPrintLabelsForOverflow();
    }

    public void updateRestaurantStatus( String msg ){
        controlledWindow.getRestaurantStatusViewPanel().removeAllComponents();
        controlledWindow.getRestaurantStatusViewPanel().addComponent(new Label(msg).withBorder(Borders.singleLineBevel()));
    }

    public void updateCooksStatus( String msg ) {
        controlledWindow.getCooksStatusViewPanel().removeAllComponents();
        controlledWindow.getCooksStatusViewPanel().addComponent(new Label(msg).withBorder(Borders.singleLineBevel()));
    }

    public void updateTickableActionsStatus( String msg ) {
        controlledWindow.getTickablesStatusViewPanel().removeAllComponents();
        controlledWindow.getTickablesStatusViewPanel().addComponent(new Label(msg).withBorder(Borders.singleLineBevel()));
    }

    private void checkDebugLabelsForOverflow() {
        if ( debugLables.size() > DEBUG_MAX_LABELS ){
            controlledWindow.getSimulationDebugViewPanel().removeComponent(debugLables.get(0));
            debugLables.remove(0);
        }
    }

    private void checkPrintLabelsForOverflow() {
        if ( printLabels.size() > PRINT_MAX_LABELS ){
            controlledWindow.getSimulationViewPanel().removeComponent(printLabels.get(0));
            printLabels.remove(0);
        }
    }
}
