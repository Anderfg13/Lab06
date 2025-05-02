package domain;
import java.awt.Color;

/**
 * Clase que representa a una persona alegre (Cheerful) en la simulación de la ciudad.
 * Hereda de la clase Person y tiene la capacidad de influenciar a sus vecinos,
 * haciéndolos felices temporalmente. Su color es rosa y su forma es triangular.
 */
public class Cheerful extends Person {

    /**
     * Constructor de la clase Cheerful.
     * Inicializa a la persona alegre en una posición específica de la ciudad.
     * Establece su color como rosa y su estado como feliz.
     *
     * @param city  La ciudad a la que pertenece la persona alegre.
     * @param row   La fila en la que se ubicará la persona alegre.
     * @param column La columna en la que se ubicará la persona alegre.
     */
    public Cheerful(City city, int row, int column) {
        super(city, row, column);
        this.color = Color.pink; // Color rosa
        this.state = Agent.HAPPY; // Siempre está feliz
    }

    /**
     * Método que define la lógica de decisión de la persona alegre.
     * Hace felices a todos sus vecinos temporalmente.
     */
    @Override
    public void decide() {
        makeNeighborsHappy();
    }

    /**
     * Devuelve la forma de la persona alegre.
     * En esta implementación, la forma es triangular.
     *
     * @return Un entero que representa la forma triangular (Item.TRIANGLE).
     */
    @Override
    public int shape() {
        return Item.TRIANGLE; // Forma triangular
    }

    /**
     * Devuelve el color de la persona alegre.
     * En esta implementación, el color es rosa.
     *
     * @return El color de la persona alegre (Color.pink).
     */
    @Override
    public Color getColor() {
        return Color.pink; // Color rosa
    }

    /**
     * Método privado que hace felices a los vecinos de la persona alegre.
     * Cambia el estado de los vecinos a feliz y marca que fueron influenciados.
     */
    private void makeNeighborsHappy() {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr != 0 || dc != 0) { // Evita la posición actual
                    int newRow = row + dr;
                    int newColumn = column + dc;

                    if (city.inLocations(newRow, newColumn)) {
                        Item neighbor = city.getItem(newRow, newColumn);

                        if (neighbor instanceof Person) {
                            Person personNeighbor = (Person) neighbor;
                            personNeighbor.setState(Agent.HAPPY);
                            personNeighbor.wasMadeHappy = true; // Marcar que fue influenciada
                        }
                    }
                }
            }
        }
    }
}
