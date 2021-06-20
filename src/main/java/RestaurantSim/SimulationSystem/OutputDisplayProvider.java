package RestaurantSim.SimulationSystem;

/**
 * An interface that provides methods to print any kind of information in form
 * of Strings.
 */
public interface OutputDisplayProvider {
    /**
     * Prints a debug message.
     * @param msg Message to print
     * @param author Author of the message
     */
    void printDebug(String msg, String author);
    /**
     * Prints a debug message.
     * @param msg Message to print
     */
    void printDebug(String msg);
    /**
     * Prints a message.
     * @param msg Message to print
     * @param author Author of the message
     */
    void print(String msg, String author);
    /**
     * Prints a message.
     * @param msg Message to print
     */
    void print(String msg);

    /**
     * Prints a text that is marked as a restaurant status.
     * This print does not cumulate which means that only the message
     * that was printed last will be shown.
     * NOTE: You can print whatever you want through this, but
     * this method should be used to display a message related to
     * a object of a class mentioned in the name.
     * @param msg Message to print.
     */
    void printRestaurantStatus( String msg);
    /**
     * Prints a text that is marked as a cook status.
     * This print does not cumulate which means that only the message
     * that was printed last will be shown.
     * NOTE: You can print whatever you want through this, but
     * this method should be used to display a message related to
     * a object of a class mentioned in the name.
     * @param msg Message to print.
     */
    void printCooksStatus( String msg);
    /**
     * Prints a text that is marked as a tickable actions status.
     * This print does not cumulate which means that only the message
     * that was printed last will be shown.
     * NOTE: You can print whatever you want through this, but
     * this method should be used to display a message related to
     * a object of a class mentioned in the name.
     * @param msg Message to print.
     */
    void printTickableActionsStatus( String msg);

    /**
     * Closes this output which will unable to use it after.
     */
    void close();

    /**
     * Indicated whether this output is opened and ready for printing
     * @return Whether output is opened and ready for printing
     */
    boolean isOpened();
}
