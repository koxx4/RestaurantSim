package RestaurantSim.SimulationSystem;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

/**
 * This class is an implementation of a {@link OutputDisplayProvider} interface.
 * Creates a window in which simulation can output any information it wants.
 * Provides a simple form of "retro" GUI.
 */
public class LanternaDisplay implements OutputDisplayProvider {
    /**
     * Thread on which GUI rendering is taking place
     */
    private AsynchronousTextGUIThread guiThread;
    /**
     * Controller of a main window on which output is seen.
     */
    private SimulationWindowController simulationWindowController;

    /**
     * Creates a instance of this class and configures GUI related things internally.
     */
    public LanternaDisplay(){
        try {
            SimulationWindow simulationWindow = new SimulationWindow();
            Screen screen = new TerminalScreen(new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(190,50))
                    .setTerminalEmulatorTitle("RestaurantSim!")
                    .createTerminal());
            TextGUIThreadFactory factory = new SeparateTextGUIThread.Factory();
            WindowBasedTextGUI windowBasedTextGUI = new MultiWindowTextGUI(factory,screen);

            screen.startScreen();
            windowBasedTextGUI.addWindow(simulationWindow);
            guiThread = (AsynchronousTextGUIThread) windowBasedTextGUI.getGUIThread();
            guiThread.start();

            simulationWindowController = new SimulationWindowController(simulationWindow);

        }catch ( Exception e ){
            System.out.println("There was a problem initializing GUI (Lanterna Display)!");
            e.printStackTrace();
        }
    }

    @Override
    public void printDebug( String msg, String author ) {
        simulationWindowController.printToDebugView(msg, author);
    }

    @Override
    public void printDebug( String msg ) {
        simulationWindowController.printToDebugView(msg);
    }

    @Override
    public void print( String msg, String author ) {
        simulationWindowController.printToMainView(msg, author);
    }

    @Override
    public void print( String msg ) {
        simulationWindowController.printToMainView(msg);
    }

    @Override
    public void printRestaurantStatus( String msg ) {
        simulationWindowController.updateRestaurantStatus(msg);
    }

    @Override
    public void printCooksStatus( String msg ) {
        simulationWindowController.updateCooksStatus(msg);
    }

    @Override
    public void printTickableActionsStatus( String msg ) {
        simulationWindowController.updateTickableActionsStatus(msg);
    }

    @Override
    public void close() {
        guiThread.stop();
    }

    @Override
    public boolean isOpened() {
      return guiThread.getThread().isAlive();
    }

}
