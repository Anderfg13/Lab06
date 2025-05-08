package domain;
import java.awt.Color;
import java.io.Serializable;

/**
 * Clase que representa un semáforo en la simulación de la ciudad.
 * Implementa la interfaz Item y define el comportamiento de un semáforo,
 * incluyendo su cambio de color y su representación gráfica.
 */
public class TrafficLight implements Item, Serializable { // Agregar Serializable
    private static final long serialVersionUID = 1L;

    // Colores que representan los estados del semáforo
    private static final Color[] COLORS = {Color.RED, Color.YELLOW, Color.GREEN, Color.YELLOW};

    // Índice que indica el color actual del semáforo
    protected int colorIndex = 0;

    // Ciudad a la que pertenece el semáforo
    private City city;

    // Posición del semáforo en la ciudad
    private int row, column;

    /**
     * Constructor de la clase TrafficLight.
     * Inicializa el semáforo en una posición específica de la ciudad.
     *
     * @param city  La ciudad a la que pertenece el semáforo.
     * @param row   La fila en la que se ubicará el semáforo.
     * @param column La columna en la que se ubicará el semáforo.
     */
    public TrafficLight(City city, int row, int column) {
        this.city = city;
        this.row = row;
        this.column = column;
        city.setItem(row, column, this);
    }

    /**
     * Método que define la lógica de decisión del semáforo.
     * En esta implementación, no realiza ninguna acción.
     */
    public void decide() {
    }

    /**
     * Método que cambia el estado del semáforo al siguiente color en la secuencia.
     * La secuencia de colores es: rojo, amarillo, verde, amarillo.
     */
    @Override
    public void change() {
        colorIndex = (colorIndex + 1) % COLORS.length;
    }

    /**
     * Método que devuelve el color actual del semáforo.
     *
     * @return El color actual del semáforo.
     */
    @Override
    public Color getColor() {
        return COLORS[colorIndex];
    }

    /**
     * Método que devuelve la forma del semáforo.
     * En esta implementación, el semáforo tiene forma redonda.
     *
     * @return Un entero que representa la forma del semáforo (Item.ROUND).
     */
    @Override
    public int shape() {
        return Item.ROUND;
    }

    /**
     * Método que indica si el semáforo está activo.
     * En esta implementación, el semáforo siempre está activo.
     *
     * @return true, ya que el semáforo siempre está activo.
     */
    @Override
    public boolean isActive() {
        return true; 
    }

    /**
     * Método que indica si el semáforo es un agente.
     * En esta implementación, el semáforo no es un agente.
     *
     * @return false, ya que el semáforo no es un agente.
     */
    @Override
    public boolean isAgent() {
        return false;
    }
}