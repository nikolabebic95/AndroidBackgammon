package rs.ac.bg.etf.pmu.bn140314d.backgammon.logic;

/**
 * @author Nikola Bebic
 * @version 25-Jan-2017
 */
public interface IFieldFactory {
    /**
     * Creates a field and returns it
     * @return Reference to the object implementing the IField interface
     */
    IField create();
}
