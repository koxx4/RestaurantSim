package RestaurantSim.SimulationSystem;

import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for managing {@link SimulationWindow} and proper printing information using it.
 */
class SimulationWindowController {
    /**
     * Maximum number of debug print labels that will be visible at once
     */
    private final int DEBUG_MAX_LABELS = 10;
    /**
     * Maximum number of normal print labels that will be visible at once
     */
    private final int PRINT_MAX_LABELS = 12;
    private final int ACTIONS_MAX_LABELS = 8;
    /**
     * Window controlled by this controller
     */
    private final SimulationWindow controlledWindow;
    /**
     * List of debug print labels that are visible to user
     */
    private List<Component> debugLables;
    /**
     * List of normal print labels that are visible to user
     */
    private List<Component> printLabels;

    /**
     * Creates instance of SimulationWindowController with SimulationWindow as its dependency
     * @param controlledWindow Window that this controller will handle
     */
    public SimulationWindowController( SimulationWindow controlledWindow ) {
        this.controlledWindow = controlledWindow;
        debugLables = new ArrayList<>(DEBUG_MAX_LABELS);
        printLabels = new ArrayList<>(PRINT_MAX_LABELS);
    }

    /**
     * Prints debug message to debug area of the output window
     * @param msg Message to print
     * @param author Author of the message
     * @see OutputDisplayProvider#printDebug(String, String)
     */
    public void printToDebugView(String msg, String author){
        Component messageLabel = new Label(msg).withBorder(Borders.singleLine(author));
        debugLables.add(messageLabel);

        controlledWindow.getSimulationDebugViewPanel()
                .addComponent(messageLabel);
        checkDebugLabelsForOverflow();
    }

    /**
     * Prints debug message to debug area of the output window
     * @param msg Message to print
     * @see OutputDisplayProvider#printDebug(String)
     */
    public void printToDebugView(String msg){
        Component messageLabel = new Label(msg).withBorder(Borders.singleLine());
        debugLables.add(messageLabel);

        controlledWindow.getSimulationDebugViewPanel()
                .addComponent(messageLabel);
        checkDebugLabelsForOverflow();
    }

    /**
     * Prints message to simulation area of the output window
     * @param msg Message to print
     * @param author Author of the message
     * @see OutputDisplayProvider#print(String, String)
     */
    public void printToMainView(String msg, String author){
        Component messageLabel = new Label(msg).withBorder(Borders.singleLine(author));
        printLabels.add(messageLabel);

        controlledWindow.getSimulationViewPanel()
                .addComponent(messageLabel);
        checkPrintLabelsForOverflow();
    }

    /**
     * Prints message to simulation area of the output window
     * @param msg Message to print
     * @see OutputDisplayProvider#print(String)
     */
    public void printToMainView(String msg){
        Component messageLabel = new Label(msg).withBorder(Borders.singleLine());
        printLabels.add(messageLabel);

        controlledWindow.getSimulationViewPanel()
                .addComponent(messageLabel);
        checkPrintLabelsForOverflow();
    }

    /**
     * Prints message to restaruant status area of the output window
     * @param msg Message to print
     * @see OutputDisplayProvider#printRestaurantStatus(String)
     */
    public void updateRestaurantStatus( String msg ){
        controlledWindow.getRestaurantStatusViewPanel().removeAllComponents();
        controlledWindow.getRestaurantStatusViewPanel().addComponent(new Label(msg).withBorder(Borders.singleLineBevel()));
    }

    /**
     * Prints message to cook status area of the output window
     * @param msg Message to print
     * @see OutputDisplayProvider#printCooksStatus(String)
     */
    public void updateCooksStatus( String msg ) {
        controlledWindow.getCooksStatusViewPanel().removeAllComponents();
        controlledWindow.getCooksStatusViewPanel().addComponent(new Label(msg).withBorder(Borders.singleLineBevel()));
    }

    /**
     * Prints message to tickable actions status area of the output window
     * @param msg Message to print
     * @see OutputDisplayProvider#printTickableActionsStatus(String)
     */
    public void updateTickableActionsStatus( String msg ) {
        controlledWindow.getTickablesStatusViewPanel().removeAllComponents();
        controlledWindow.getTickablesStatusViewPanel().addComponent(new Label(msg).withBorder(Borders.singleLineBevel()));
    }

    /**
     * Checks if debug labels list grown to big. Is so then this function will
     * delete the oldest created label. This method ensures that
     * amount of debug labels do not exceed max number of debug labels
     * @see SimulationWindowController#DEBUG_MAX_LABELS
     */
    private void checkDebugLabelsForOverflow() {
        if ( debugLables.size() > DEBUG_MAX_LABELS ){
            controlledWindow.getSimulationDebugViewPanel().removeComponent(debugLables.get(0));
            debugLables.remove(0);
        }
    }

    /**
     * Checks if print labels list grown to big. Is so then this function will
     * delete the oldest created label. This method ensures that
     * amount of print labels do not exceed max number of print labels
     * @see SimulationWindowController#PRINT_MAX_LABELS
     */
    private void checkPrintLabelsForOverflow() {
        if ( printLabels.size() > PRINT_MAX_LABELS ){
            controlledWindow.getSimulationViewPanel().removeComponent(printLabels.get(0));
            printLabels.remove(0);
        }
    }
}
