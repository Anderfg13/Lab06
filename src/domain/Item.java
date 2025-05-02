package domain;
import java.awt.Color;

/**
 * Interfaz que representa un ítem en la simulación de la ciudad.
 * Define métodos y constantes para describir el comportamiento y las propiedades de los ítems.
 */
public interface Item {
    /**
     * Constante que representa la forma redonda.
     */
    public static final int ROUND = 1;

    /**
     * Constante que representa la forma cuadrada.
     */
    public static final int SQUARE = 2;

    /**
     * Constante que representa la forma triangular.
     */
    public static final int TRIANGLE = 3;

    /**
     * Método abstracto que define la lógica de decisión del ítem.
     * Las clases que implementen esta interfaz deben proporcionar una implementación.
     */
    public abstract void decide();

    /**
     * Método por defecto que define el comportamiento de cambio del ítem.
     * Puede ser sobrescrito por las clases que implementen esta interfaz.
     */
    public default void change() {}

    /**
     * Método por defecto que devuelve la forma del ítem.
     * Por defecto, los ítems tienen forma redonda.
     *
     * @return Un entero que representa la forma del ítem (ROUND por defecto).
     */
    public default int shape() {
        return ROUND;
    }

    /**
     * Método por defecto que devuelve el color del ítem.
     * Por defecto, los ítems son de color negro.
     *
     * @return Un objeto Color que representa el color del ítem (Color.black por defecto).
     */
    public default Color getColor() {
        return Color.black;
    }

    /**
     * Método por defecto que indica si el ítem está activo.
     * Por defecto, los ítems están activos.
     *
     * @return true si el ítem está activo, false en caso contrario (true por defecto).
     */
    public default boolean isActive() {
        return true;
    }

    /**
     * Método por defecto que indica si el ítem es un agente.
     * Por defecto, los ítems no son agentes.
     *
     * @return true si el ítem es un agente, false en caso contrario (false por defecto).
     */
    public default boolean isAgent() {
        return false;
    }
}