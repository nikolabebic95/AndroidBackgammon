package rs.ac.bg.etf.pmu.bn140314d.backgammon.logic;

/**
 * @author Nikola Bebic
 * @version 25-Jan-2017
 */
public class FieldFactory implements IFieldFactory {

    @Override
    public IField create() {
        return new Field();
    }
}
