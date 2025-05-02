package domain;
import java.awt.Color;

/**
 * Clase que representa a una persona en la simulación de la ciudad.
 * Extiende la clase Agent e implementa la interfaz Item.
 * Una persona tiene una posición (fila, columna) en la ciudad, un color y un estado emocional.
 * Además, puede ser influenciada por un objeto de tipo Cheerful.
 */
public class Person extends Agent implements Item {
    // Ciudad a la que pertenece la persona
    protected City city;

    // Posición de la persona en la ciudad
    protected int row, column;    

    // Color de la persona
    protected Color color;

    // Indica si la persona fue influenciada por un objeto Cheerful
    protected boolean wasMadeHappy = false;

    /**
     * Crea una nueva persona en la posición (<b>row</b>, <b>column</b>) de la ciudad <b>city</b>.
     *
     * @param city  La ciudad a la que pertenece la persona.
     * @param row   La fila en la que se ubicará la persona.
     * @param column La columna en la que se ubicará la persona.
     */
    public Person(City city, int row, int column) {
        this.city = city;
        this.row = row;
        this.column = column;
        this.city.setItem(row, column, (Item) this);    
        color = Color.blue;
    }

    /**
     * Devuelve la fila en la que se encuentra la persona.
     *
     * @return La fila de la persona.
     */
    public final int getRow() {
        return row;
    }

    /**
     * Devuelve la columna en la que se encuentra la persona.
     *
     * @return La columna de la persona.
     */
    public final int getColumn() {
        return column;
    }

    /**
     * Devuelve el color de la persona.
     *
     * @return El color de la persona.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Método que define la lógica de decisión de la persona.
     * Cambia el estado emocional de la persona en función de los pasos transcurridos.
     * Si la persona fue influenciada por un objeto Cheerful, se resetea su estado.
     */
    public void decide() {
        if (wasMadeHappy) {
            wasMadeHappy = false; // Resetea la influencia en el siguiente tick
        } else {
            state = (getSteps() % 3 == 0 ? Agent.HAPPY : 
                     (getSteps() % 3 == 1 ? Agent.INDIFFERENT : Agent.DISSATISFIED));
        }
    }

    /**
     * Método que cambia el estado actual de la persona.
     * Incrementa el contador de pasos.
     */
    public void change() {
        step();
    }
}