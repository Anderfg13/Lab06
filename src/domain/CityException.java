package domain;

public class CityException extends Exception {
    public static final String OPTION_IN_CONSTRUCTION = "Opción %s en construcción. Archivo %s";

    public CityException(String formattedMessage, String optionName, String fileName) {
        super(String.format(formattedMessage, optionName, fileName));
    }
}