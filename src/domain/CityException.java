package domain;

public class CityException extends Exception {
    public static final String OPTION_IN_CONSTRUCTION = "Opción %s en construcción. Archivo %s";
    public static final String WRONG_FILE_TIPE = "El archivo debe tener la extensión .dat";
    public static final String OPEN_ERROR = "Error al abrir la ciudad: %s";
    public static final String SAVE_ERROR = "Error al guardar la ciudad: %s";
    public static final String IMPORT_ERROR = "Error al importar la ciudad: %s";
    public static final String EXPORT_ERROR = "Error al exportar la ciudad: %s";
    public static final String CLASS_INSTANCE_ERROR = "Error al instanciar la clase: %s";
    public static final String CLASS_NOT_FOUND_ERROR = "Error al encontrar la clase: %s";
    public static final String CONSTRUCTOR_NOT_FOUND_ERROR = "Constructor not found for class: %s";
    public static final String INVALID_LINE_FORMAT = "Formato de línea inválido: %s";

    public CityException(String formattedMessage, String optionName, String fileName) {
        super(String.format(formattedMessage, optionName, fileName));
    }

    public CityException(String message) {
        super(message);
    }
}