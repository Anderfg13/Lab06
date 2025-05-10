package domain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Constructor;

/**
 * Clase que representa una ciudad en la simulación.
 * La ciudad es una cuadrícula de tamaño fijo (SIZE x SIZE) donde se ubican diferentes ítems,
 * como personas, semáforos, postes de luz y otros agentes.
 */
public class City implements Serializable {
    private static final long serialVersionUID = 1L; // Versión para la serialización

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

    //Diferetentes opens

    public void open00(File file) throws CityException {
        throw new CityException(CityException.OPTION_IN_CONSTRUCTION, "Abrir", file.getName());
    }

    public static City open01(File file) throws CityException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (City) ois.readObject(); // Deserializa el objeto City
        } catch (Exception e) { // Captura cualquier excepción
            throw new CityException("Error al abrir la ciudad."); // Mensaje de error general
        }
    }

    public static City open(File file) throws CityException {
        if (!file.getName().endsWith(".dat")) {
            throw new CityException(CityException.WRONG_FILE_TIPE);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (City) ois.readObject(); // Deserializa el objeto City
        } catch (Exception e) {
            throw new CityException(CityException.OPEN_ERROR + e.getMessage());
        }
    }

    //Diferentes saves
    public void save00(File file) throws CityException {
        throw new CityException(CityException.OPTION_IN_CONSTRUCTION, "Guardar", file.getName());
    }
    
    public void save01(File file) throws CityException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this); // Serializa el objeto City
        } catch (Exception e) { // Captura cualquier excepción
            throw new CityException(CityException.SAVE_ERROR); // Mensaje de error general
        }
    }

    public void save(File file) throws CityException {
        if (!file.getName().endsWith(".dat")) {
            throw new CityException(CityException.WRONG_FILE_TIPE);
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this); // Serializa el objeto City
        } catch (Exception e) {
            throw new CityException(CityException.SAVE_ERROR + e.getMessage());
        }
    }

    //Diferentes imports

    public void importData00(File file) throws CityException {
        throw new CityException(CityException.OPTION_IN_CONSTRUCTION, "Importar", file.getName());
    }

    public void importData(File file) throws CityException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0; // Contador de líneas
            while ((line = reader.readLine()) != null) {
                lineNumber++; // Incrementa el número de línea
                line = line.trim(); // Elimina espacios en blanco al inicio y al final
                if (line.isEmpty()) {
                    continue; // Ignora líneas vacías
                }
                String[] parts = line.split("\\s+"); // Divide la línea por espacios
                if (parts.length == 3) {
                    String className = parts[0];
                    try {
                        int r = Integer.parseInt(parts[1]);
                        int c = Integer.parseInt(parts[2]);
    
                        // Verifica que las coordenadas estén dentro de los límites
                        if (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
                            try {
                                // Usa reflexión para crear una instancia de la clase
                                Class<?> itemClass = Class.forName("domain." + className);
                                Constructor<?> constructor = itemClass.getDeclaredConstructor(City.class, int.class, int.class);
                                Item item = (Item) constructor.newInstance(this, r, c);
    
                                locations[r][c] = item;
                            } catch (ClassNotFoundException e) {
                                throw new CityException("Error en la línea " + lineNumber + ": Clase no encontrada '" + className + "'.");
                            } catch (NoSuchMethodException e) {
                                throw new CityException("Error en la línea " + lineNumber + ": Constructor no encontrado para la clase '" + className + "'.");
                            } catch (Exception e) {
                                throw new CityException("Error en la línea " + lineNumber + ": No se pudo crear una instancia de la clase '" + className + "' - " + e.getMessage());
                            }
                        } else {
                            throw new CityException("Error en la línea " + lineNumber + ": Coordenadas fuera de los límites (r=" + r + ", c=" + c + ").");
                        }
                    } catch (NumberFormatException e) {
                        throw new CityException("Error en la línea " + lineNumber + ": Formato de número inválido en '" + parts[1] + "' o '" + parts[2] + "'.");
                    }
                } else {
                    throw new CityException("Error en la línea " + lineNumber + ": Formato de línea inválido. Se esperaban 3 elementos, pero se encontraron " + parts.length + ".");
                }
            }
        } catch (IOException e) {
            throw new CityException("Error al leer el archivo: " + e.getMessage());
        }
    }

    public void importData01(File file) throws CityException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Elimina espacios en blanco al inicio y al final
                if (line.isEmpty()) {
                    continue; // Ignora líneas vacías
                }
                String[] parts = line.split("\\s+"); // Divide la línea por espacios
                if (parts.length == 3) {
                    String className = parts[0];
                    int r = Integer.parseInt(parts[1]);
                    int c = Integer.parseInt(parts[2]);

                    // Crear el ítem y colocarlo en la ciudad
                    Class<?> itemClass = Class.forName("domain." + className);
                    Constructor<?> constructor = itemClass.getDeclaredConstructor(City.class, int.class, int.class);
                    Item item = (Item) constructor.newInstance(this, r, c);
                    locations[r][c] = item;
                }
            }
        } catch (Exception e) { // Captura cualquier excepción
            throw new CityException(CityException.IMPORT_ERROR); // Mensaje de error general
        }
    }

    public void importData02(File file) throws CityException {
        if (file == null) {
            throw new CityException("El archivo no puede ser nulo.");
        }

        if (file.length() == 0) { // Verifica si el archivo está vacío
            throw new CityException("El archivo está vacío.");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Elimina espacios en blanco al inicio y al final
                if (line.isEmpty()) {
                    continue; // Ignora líneas vacías
                }
                String[] parts = line.split("\\s+"); // Divide la línea por espacios
                if (parts.length == 3) {
                    String className = parts[0];
                    try {
                        int r = Integer.parseInt(parts[1]);
                        int c = Integer.parseInt(parts[2]);

                        // Verifica que las coordenadas estén dentro de los límites
                        if (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
                            try {
                                // Usa reflexión para crear una instancia de la clase
                                Class<?> itemClass = Class.forName("domain." + className);
                                Constructor<?> constructor = itemClass.getDeclaredConstructor(City.class, int.class, int.class);
                                Item item = (Item) constructor.newInstance(this, r, c);

                                locations[r][c] = item;
                            } catch (ClassNotFoundException e) {
                                throw new CityException("Error en la línea: Clase no encontrada '" + className + "'.");
                            } catch (NoSuchMethodException e) {
                                throw new CityException("Error en la línea: Constructor no encontrado para la clase '" + className + "'.");
                            } catch (Exception e) {
                                throw new CityException("Error en la línea: No se pudo crear una instancia de la clase '" + className + "' - " + e.getMessage());
                            }
                        } else {
                            throw new CityException("Error en la línea: Coordenadas fuera de los límites (r=" + r + ", c=" + c + ").");
                        }
                    } catch (NumberFormatException e) {
                        throw new CityException("Error en la línea: Formato de número inválido en '" + parts[1] + "' o '" + parts[2] + "'.");
                    }
                } else {
                    throw new CityException("Error en la línea: Formato de línea inválido. Se esperaban 3 elementos, pero se encontraron " + parts.length + ".");
                }
            }
        } catch (IOException e) {
            throw new CityException("Error al leer el archivo: " + e.getMessage());
        }
    }

    //Diferentes exports

    public void exportData00(File file) throws CityException {
        throw new CityException(CityException.OPTION_IN_CONSTRUCTION, "Exportar", file.getName());
    }

    public void exportData(File file) throws CityException {
        if (file == null) {
            throw new CityException("El archivo no puede ser nulo.");
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Item item = locations[r][c];
                    if (item != null) {
                        writer.write(item.getClass().getSimpleName() + " " + r + " " + c + "\n");
                    }
                }
            }
        } catch (IOException e) {
            throw new CityException("Error al exportar la ciudad: " + e.getMessage());
        }
    }

    public void exportData01(File file) throws CityException {
        try {
            FileWriter writer = new FileWriter(file);
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Item item = locations[r][c];
                    if (item != null) {
                        writer.write(item.getClass().getSimpleName() + " " + r + " " + c + "\n");
                    }
                }
            }
            writer.close();
        } catch (Exception e) { // Captura cualquier excepción
            throw new CityException(CityException.EXPORT_ERROR); // Mensaje de error general
        }
    }

    public void exportData02(File file) throws CityException {
        try {
            FileWriter writer = new FileWriter(file);
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Item item = locations[r][c];
                    if (item != null) {
                        writer.write(item.getClass().getSimpleName() + " " + r + " " + c + "\n");
                    }
                }
            }
            writer.close();
        } catch (Exception e) { // Captura cualquier excepción
            throw new CityException("Error al exportar la ciudad."); // Mensaje de error general
        }
    }


}