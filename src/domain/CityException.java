package domain;

public class CityException extends Exception {
    public static final String OPTION_IN_CONSTRUCTION = "Opción en construcción.";

    public CityException(String message) {
        super(message);
    }
}