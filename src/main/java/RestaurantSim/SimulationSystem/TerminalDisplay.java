package RestaurantSim.SimulationSystem;

import com.googlecode.lanterna.TextColor;

public class TerminalDisplay implements OutputDisplayProvider{
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    @Override
    public void printDebug( String msg, String author ) {
        System.out.println(ANSI_RED + "DEBUG: " + author + " " + msg + ANSI_RESET);
    }

    @Override
    public void printDebug( String msg ) {
        System.out.println(ANSI_RED + "DEBUG: " + msg + ANSI_RESET);
    }

    @Override
    public void print( String msg, String author ) {
        System.out.println(ANSI_GREEN + author + " " + msg + ANSI_RESET);
    }

    @Override
    public void print( String msg ) {
        System.out.println(ANSI_GREEN + msg + ANSI_RESET);
    }

    @Override
    public void printRestaurantStatus( String msg ) {
        System.out.println(ANSI_BLUE_BACKGROUND + msg + ANSI_RESET);
    }

    @Override
    public void printCooksStatus( String msg ) {

    }

    @Override
    public void printTickableActionsStatus( String msg ) {

    }

    @Override
    public void close() {
        //for terminal we don't do much...
    }

    @Override
    public boolean isOpened() {
        return true;
    }
}
