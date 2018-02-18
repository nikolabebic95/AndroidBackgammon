package rs.ac.bg.etf.pmu.bn140314d.backgammon.logic;

import java.io.Serializable;

/**
 * @author Nikola Bebic
 * @version 25-Jan-2017
 */
public interface IFieldFactory extends Serializable {
    /**
     * Creates a field and returns it
     * @return Reference to the object implementing the IField interface
     */
    IField create();
}
