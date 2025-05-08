package domain;
import java.awt.Color;
import java.io.Serializable;

/**
 * Clase que representa a un caminante (Walker) en la simulación de la ciudad.
 * Hereda de la clase Person y tiene un comportamiento único: se mueve hacia el norte
 * si no hay ítems cercanos, y su estado emocional depende de su capacidad para moverse
 * o de la presencia de ítems cercanos. Su color es verde y su forma es rectangular.
 */
public class Walker extends Person implements Serializable {
    // Fila deseada hacia la que el caminante intenta moverse
    private int desiredRow;
    private static final long serialVersionUID = 1L; // Versión para la serialización

    /**
     * Constructor de la clase Walker.
     * Inicializa al caminante en una posición específica de la ciudad.
     * Establece su color como verde y su estado como indiferente.
     *
     * @param city  La ciudad a la que pertenece el caminante.
     * @param row   La fila en la que se ubicará el caminante.
     * @param column La columna en la que se ubicará el caminante.
     */
    public Walker(City city, int row, int column) {
        super(city, row, column);
        this.color = Color.green;
        this.state = Agent.INDIFFERENT;
    }

    /**
     * Método que verifica si hay ítems cercanos al caminante.
     *
     * @return true si hay ítems cercanos, false en caso contrario.
     */
    private boolean hasItemNearby() {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue; // Ignorar la posición actual
                int newRow = row + dr;
                int newCol = column + dc;
                // Verificar límites y existencia de ítem
                if (newRow >= 0 && newRow < city.getSize() && 
                    newCol >= 0 && newCol < city.getSize() && 
                    city.getItem(newRow, newCol) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método que define la lógica de decisión del caminante.
     * Si hay ítems cercanos, el caminante se vuelve feliz.
     * Si no hay ítems cercanos, intenta moverse hacia el norte.
     * Su estado emocional depende de si puede moverse o no.
     */
    @Override
    public void decide() {
        if (hasItemNearby()) { // Usa el método de validación
            state = Agent.HAPPY;
        } else {
            // Lógica original de movimiento al norte
            desiredRow = row - 1;
            if (desiredRow < 0) desiredRow = row;

            if (city.isEmpty(desiredRow, column)) {
                state = Agent.HAPPY;
            } else {
                state = Agent.DISSATISFIED;
            }
        }
    }

    /**
     * Método que realiza el cambio de estado del caminante.
     * Si la posición deseada está vacía, el caminante se mueve hacia ella.
     */
    @Override
    public void change() {
        super.change(); // Incrementa steps
        if (city.isEmpty(desiredRow, column)) {
            city.setItem(row, column, null); // Libera posición actual
            row = desiredRow; // Actualiza fila
            city.setItem(row, column, this); // Ocupa nueva posición
        }
    }

    /**
     * Devuelve la forma del caminante.
     * En esta implementación, la forma es rectangular.
     *
     * @return Un entero que representa la forma rectangular (Item.SQUARE).
     */
    @Override
    public int shape() {
        return Item.SQUARE; // Forma rectangular
    }

    /**
     * Devuelve el color del caminante.
     * En esta implementación, el color es verde.
     *
     * @return El color del caminante (Color.green).
     */
    @Override
    public Color getColor() {
        return Color.green;
    }
}

