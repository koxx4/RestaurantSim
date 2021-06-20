package RestaurantSim.SimulationSystem;

import com.googlecode.lanterna.gui2.*;

import java.util.Set;

class SimulationWindow extends BasicWindow {

    private final Panel mainPanel;
    private final Panel simulationViewPanel;
    private final Panel simulationDebugViewPanel;
    private final Panel statusesPanel;
    private final Panel restaurantStatusViewPanel;
    private final Panel cooksStatusViewPanel;
    private final Panel tickablesStatusViewPanel;
   // private final Button pauseButton;
   // private final Button exitButton;

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

    public Panel getMainPanel() {
        return mainPanel;
    }

    public Panel getSimulationViewPanel() {
        return simulationViewPanel;
    }

    public Panel getSimulationDebugViewPanel() {
        return simulationDebugViewPanel;
    }

    public Panel getRestaurantStatusViewPanel() {
        return restaurantStatusViewPanel;
    }

    public Panel getCooksStatusViewPanel() {
        return cooksStatusViewPanel;
    }

    public Panel getTickablesStatusViewPanel() {
        return tickablesStatusViewPanel;
    }

}
