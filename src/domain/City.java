package domain;

/**
 * Clase que representa una ciudad en la simulación.
 * La ciudad es una cuadrícula de tamaño fijo (SIZE x SIZE) donde se ubican diferentes ítems,
 * como personas, semáforos, postes de luz y otros agentes.
 */
public class City {
    // Tamaño de la ciudad (número de filas y columnas)
    static private int SIZE = 25;

    // Matriz que representa las ubicaciones de los ítems en la ciudad
    protected Item[][] locations;

    /**
     * Constructor de la clase City.
     * Inicializa la ciudad con una cuadrícula vacía y agrega algunos ítems iniciales.
     */
    public City() {
        locations = new Item[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                locations[r][c] = null;
            }
        }
        someItems();
    }

    /**
     * Devuelve el tamaño de la ciudad.
     *
     * @return El tamaño de la ciudad (número de filas y columnas).
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Devuelve el ítem ubicado en la posición (r, c) de la ciudad.
     *
     * @param r La fila de la ubicación.
     * @param c La columna de la ubicación.
     * @return El ítem en la posición (r, c), o null si no hay ningún ítem.
     */
    public Item getItem(int r, int c) {
        return locations[r][c];
    }

    /**
     * Coloca un ítem en la posición (r, c) de la ciudad.
     *
     * @param r La fila de la ubicación.
     * @param c La columna de la ubicación.
     * @param e El ítem a colocar.
     */
    public void setItem(int r, int c, Item e) {
        locations[r][c] = e;
    }

    /**
     * Método que agrega algunos ítems iniciales a la ciudad.
     * Incluye personas, caminantes, semáforos, objetos Cheerful y postes de luz.
     */
    public void someItems() { 
        Person adan = new Person(this, 10, 10);
        Person eva = new Person(this, 15, 15);
        
        Walker messner = new Walker(this, 20, 20); 
        Walker kukuczka = new Walker(this, 5, 5); 
        
        TrafficLight alarm = new TrafficLight(this, 0, 0);   
        TrafficLight alert = new TrafficLight(this, 0, 24);  
        
        Cheerful garcia = new Cheerful(this, 6, 6);
        Cheerful romero = new Cheerful(this, 15, 14);
        
        StreetLight anderson = new StreetLight(this, 2, 2);
        StreetLight christian = new StreetLight(this, 17, 17);
        
        SchellingPerson schelling1 = new SchellingPerson(this, 3, 3);
        SchellingPerson schelling2 = new SchellingPerson(this, 22, 22);
    }

    /**
     * Cuenta el número de vecinos iguales al ítem en la posición (r, c).
     * Un vecino es igual si es del mismo tipo (clase) que el ítem en (r, c).
     *
     * @param r La fila de la ubicación.
     * @param c La columna de la ubicación.
     * @return El número de vecinos iguales.
     */
    public int neighborsEquals(int r, int c) {
        int num = 0;
        if (inLocations(r, c) && locations[r][c] != null) {
            for (int dr = -1; dr < 2; dr++) {
                for (int dc = -1; dc < 2; dc++) {
                    if ((dr != 0 || dc != 0) && inLocations(r + dr, c + dc) && 
                        (locations[r + dr][c + dc] != null) &&  
                        (locations[r][c].getClass() == locations[r + dr][c + dc].getClass())) {
                        num++;
                    }
                }
            }
        }
        return num;
    }

    /**
     * Verifica si la posición (r, c) está vacía (no contiene ningún ítem).
     *
     * @param r La fila de la ubicación.
     * @param c La columna de la ubicación.
     * @return true si la posición está vacía, false en caso contrario.
     */
    public boolean isEmpty(int r, int c) {
        return (inLocations(r, c) && locations[r][c] == null);
    }    

    /**
     * Verifica si la posición (r, c) está dentro de los límites de la ciudad.
     *
     * @param r La fila de la ubicación.
     * @param c La columna de la ubicación.
     * @return true si la posición es válida, false en caso contrario.
     */
    protected boolean inLocations(int r, int c) {
        return ((0 <= r) && (r < SIZE) && (0 <= c) && (c < SIZE));
    }

    /**
     * Avanza un turno en la simulación.
     * Todos los ítems en la ciudad deciden su próximo estado y realizan cambios.
     */
    public void ticTac() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Item item = locations[r][c];
                if (item != null) {
                    item.decide(); // Decide el estado
                    item.change();  // Incrementa steps
                }
            }
        }
    }


    public void open() throws CityException {
        throw new CityException(CityException.OPTION_IN_CONSTRUCTION);
    }

    public void save() throws CityException {
        throw new CityException(CityException.OPTION_IN_CONSTRUCTION);
    }

    public void import() throws CityException {
        throw new CityException(CityException.OPTION_IN_CONSTRUCTION);
    }

    public void export() throws CityException {
        throw new CityException(CityException.OPTION_IN_CONSTRUCTION);
    }
}