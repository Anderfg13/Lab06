package domain;
import java.awt.Color;

/**
 * Clase que representa un poste de luz en la simulación de la ciudad.
 * Hereda de la clase TrafficLight y simula un poste de luz que cambia de estado cada tres turnos.
 * El poste de luz alterna entre dos colores: gris oscuro (apagado) y naranja (encendido).
 */
public class StreetLight extends TrafficLight {
    // Colores que representan los estados del poste de luz
    private static final Color[] COLORS = {Color.DARK_GRAY, Color.ORANGE}; // Gris oscuro (apagado), Naranja (encendido)

    // Contador de turnos para controlar el cambio de estado
    private int turnCounter = 0;

    /**
     * Constructor de la clase StreetLight.
     * Inicializa el poste de luz en una posición específica de la ciudad.
     *
     * @param city  La ciudad a la que pertenece el poste de luz.
     * @param row   La fila en la que se ubicará el poste de luz.
     * @param column La columna en la que se ubicará el poste de luz.
     */
    public StreetLight(City city, int row, int column) {
        super(city, row, column);
    }

    /**
     * Método que cambia el estado del poste de luz cada tres turnos.
     * Alterna entre los colores gris oscuro (apagado) y naranja (encendido).
     */
    @Override
    public void change() {
        turnCounter++;
        if (turnCounter % 3 == 0) { // Cada 3 turnos cambia de estado
            colorIndex = (colorIndex + 1) % COLORS.length;
        }
    }

    /**
     * Devuelve el color actual del poste de luz.
     *
     * @return El color actual del poste de luz.
     */
    @Override
    public Color getColor() {
        return COLORS[colorIndex];
    }

    /**
     * Devuelve la forma del poste de luz.
     * En esta implementación, el poste de luz tiene forma redonda.
     *
     * @return Un entero que representa la forma del poste de luz (Item.ROUND).
     */
    @Override
    public int shape() {
        return Item.ROUND;
    }

    /**
     * Indica si el poste de luz está activo.
     * En esta implementación, el poste de luz siempre está activo.
     *
     * @return true, ya que el poste de luz siempre está activo.
     */
    @Override
    public boolean isActive() {
        return true;
    }

    /**
     * Indica si el poste de luz es un agente.
     * En esta implementación, el poste de luz no es un agente.
     *
     * @return false, ya que el poste de luz no es un agente.
     */
    @Override
    public boolean isAgent() {
        return false;
    }
}