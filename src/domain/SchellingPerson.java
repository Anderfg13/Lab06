package domain;

import java.awt.Color;
import java.io.Serializable;

/**
 * Clase que representa a una persona Schelling en la simulación de la ciudad.
 * Hereda de la clase Person y aplica las reglas de Schelling para determinar
 * su estado emocional y movimiento en función de sus vecinos.
 * <p>
 * Una persona Schelling está indiferente si no tiene vecinos o si todos sus vecinos son similares.
 * Está insatisfecha si menos de 1/3 de sus vecinos son similares.
 * Está satisfecha si más de 1/3 de sus vecinos son similares, pero no todos.
 * </p>
 */
public class SchellingPerson extends Person implements Serializable {
    private static final long serialVersionUID = 1L; // Versión para la serialización
    /**
     * Constructor de la clase SchellingPerson.
     * Inicializa a la persona Schelling en una posición específica de la ciudad.
     * Establece su color como magenta y su estado inicial como indiferente.
     *
     * @param city  La ciudad a la que pertenece la persona.
     * @param row   La fila en la que se ubicará la persona.
     * @param column La columna en la que se ubicará la persona.
     */
    public SchellingPerson(City city, int row, int column) {
        super(city, row, column);
        this.color = Color.MAGENTA; // Color diferente para distinguirla
        this.state = Agent.INDIFFERENT; // Estado inicial
    }

    /**
     * Decide el estado emocional de la persona Schelling en función de sus vecinos.
     * <p>
     * - Indiferente: Si no hay vecinos o si todos los vecinos son similares.
     * - Insatisfecho: Si menos de 1/3 de los vecinos son similares.
     * - Satisfecho: Si más de 1/3 de los vecinos son similares, pero no todos.
     * </p>
     */
    @Override
    public void decide() {
        int totalNeighbors = 0;
        int similarNeighbors = 0;

        // Contar vecinos y vecinos similares
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue; // Ignorar la posición actual
                int newRow = row + dr;
                int newCol = column + dc;

                if (city.inLocations(newRow, newCol)) {
                    Item neighbor = city.getItem(newRow, newCol);
                    if (neighbor != null) {
                        totalNeighbors++;
                        if (neighbor.getClass() == this.getClass()) {
                            similarNeighbors++;
                        }
                    }
                }
            }
        }

        // Determinar el estado emocional
        if (totalNeighbors == 0) {
            setState(Agent.INDIFFERENT); // Indiferente si no hay vecinos
        } else if (similarNeighbors == totalNeighbors) {
            setState(Agent.INDIFFERENT); // Indiferente si todos los vecinos son similares
        } else if (similarNeighbors < totalNeighbors / 3.0) {
            setState(Agent.DISSATISFIED); // Insatisfecho si menos de 1/3 de los vecinos son similares
        } else {
            setState(Agent.HAPPY); // Satisfecho si más de 1/3 de los vecinos son similares
        }
    }

    /**
     * Cambia el estado de la persona Schelling.
     * Si está insatisfecha, intenta moverse a una posición vacía.
     */
    @Override
    public void change() {
        if (state == Agent.DISSATISFIED) {
            moveToEmptySpot();
        }
    }

    /**
     * Intenta moverse a una posición vacía en la ciudad.
     * Busca una posición vacía en las celdas adyacentes y se mueve a la primera que encuentra.
     */
    private void moveToEmptySpot() {
        // Buscar una posición vacía aleatoria
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue; // Ignorar la posición actual
                int newRow = row + dr;
                int newCol = column + dc;

                if (city.inLocations(newRow, newCol) && city.isEmpty(newRow, newCol)) {
                    // Moverse a la nueva posición
                    city.setItem(row, column, null); // Liberar posición actual
                    row = newRow;
                    column = newCol;
                    city.setItem(row, column, this); // Ocupar nueva posición
                    return; // Salir después de moverse
                }
            }
        }
    }
}