package domain;

public class CityException extends Exception {
    public static final String OPTION_IN_CONSTRUCTION = "Opción %s en construcción. Archivo %s";
    public static final String WRONG_FILE_TIPE = "El archivo debe tener la extensión .dat";
    public static final String OPEN_ERROR = "Error al abrir la ciudad: %s";
    public static final String SAVE_ERROR = "Error al guardar la ciudad: %s";

    public CityException(String formattedMessage, String optionName, String fileName) {
        super(String.format(formattedMessage, optionName, fileName));
    }

    public CityException(String message) {
        super(message);
    }
}