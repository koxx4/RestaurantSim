package RestaurantSim.SimulationSystem;

import com.googlecode.lanterna.gui2.*;

import java.util.Set;

/**
 * Simple window to which all simulation output will be printed.
 * This class extends BasicWindow from Lanterna library.
 * @see BasicWindow
 */
class SimulationWindow extends BasicWindow {

    private final Panel mainPanel;
    private final Panel simulationViewPanel;
    private final Panel simulationDebugViewPanel;
    private final Panel statusesPanel;
    private final Panel restaurantStatusViewPanel;
    private final Panel cooksStatusViewPanel;
    private final Panel tickablesStatusViewPanel;

    /**
     * Creates instance SimulationWindow, sets its internal panels and their layouts.
     */
    public SimulationWindow() {
        super("Restaurant simulation window");
        //Make window full screen
        setHints(Set.of(Hint.EXPANDED));

        mainPanel = new Panel();
        simulationDebugViewPanel = new Panel();
        simulationViewPanel = new Panel();
        statusesPanel = new Panel();
        restaurantStatusViewPanel = new Panel();
        cooksStatusViewPanel = new Panel();
        tickablesStatusViewPanel = new Panel();

        mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        statusesPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        simulationDebugViewPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        simulationViewPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        mainPanel.addComponent(simulationDebugViewPanel.withBorder(Borders.singleLine("Debug info")));
        mainPanel.addComponent(simulationViewPanel.withBorder(Borders.doubleLine("Simulation view")));
        mainPanel.addComponent(statusesPanel.withBorder(Borders.singleLine("Statuses")));
        statusesPanel.addComponent(restaurantStatusViewPanel.withBorder(Borders.doubleLineBevel("Restaurant status")));
        statusesPanel.addComponent(cooksStatusViewPanel.withBorder(Borders.doubleLineBevel("Cooks")));
        statusesPanel.addComponent(tickablesStatusViewPanel.withBorder(Borders.doubleLineBevel("Tickable actions")));
        setComponent(mainPanel);
    }

    /**
     * Returns main panel which is a parent to all of the {@link Component}s in this window
     */
    public Panel getMainPanel() {
        return mainPanel;
    }

    /**
     * Returns a panel in which simulation specific info should be shown
     * @return
     */
    public Panel getSimulationViewPanel() {
        return simulationViewPanel;
    }

    /**
     * Returns a panel in which simulation specific debug info should be shown
     * @return
     */
    public Panel getSimulationDebugViewPanel() {
        return simulationDebugViewPanel;
    }

    /**
     * Returns a panel in which Restaurant specific info should be shown
     * @return
     */
    public Panel getRestaurantStatusViewPanel() {
        return restaurantStatusViewPanel;
    }

    /**
     * Returns a panel in which Cooks specific info should be shown
     * @return
     */
    public Panel getCooksStatusViewPanel() {
        return cooksStatusViewPanel;
    }

    /**
     * Returns a panel in which simulation TickableActions specific info should be shown
     * @return
     */
    public Panel getTickablesStatusViewPanel() {
        return tickablesStatusViewPanel;
    }

}
