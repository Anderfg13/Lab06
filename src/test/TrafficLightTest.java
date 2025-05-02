package test;
import domain.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;

/**
 * Clase de pruebas unitarias para la clase TrafficLight.
 * Verifica el comportamiento del semáforo en la simulación de la ciudad,
 * incluyendo su ciclo de colores y su posición en la ciudad.
 */
public class TrafficLightTest {
    // Ciudad y semáforo utilizados en las pruebas
    private City city;
    private TrafficLight light;

    /**
     * Constructor por defecto de la clase TrafficLightTest.
     */
    public TrafficLightTest() {
        // No se requiere inicialización adicional
    }

    /**
     * Configura el entorno de prueba antes de cada método de prueba.
     * Inicializa una ciudad y un semáforo en la posición (0, 0).
     */
    @BeforeEach
    public void setUp() {
        city = new City();
        light = new TrafficLight(city, 0, 0);
    }

    /**
     * Limpia el entorno de prueba después de cada método de prueba.
     * Libera los recursos utilizados en las pruebas.
     */
    @AfterEach
    public void tearDown() {
        city = null;
        light = null;
    }

    /**
     * Prueba el ciclo de colores del semáforo.
     * Verifica que después de 4 cambios de estado, el semáforo vuelva al color rojo.
     */
    @Test
    public void testColorCycle() {
        // Ejecutar 4 cambios de estado (rojo → amarillo → verde → amarillo → rojo)
        for (int i = 0; i < 4; i++) {
            light.change();
        }
        assertEquals(Color.RED, light.getColor(), "Debe volver a ROJO después de 4 cambios");
    }

    /**
     * Prueba la posición del semáforo en la ciudad.
     * Verifica que el semáforo se coloque correctamente en la posición especificada.
     */
    @Test
    public void testPositionInCity() {
        TrafficLight testLight = new TrafficLight(city, 5, 5);
        assertNotNull(city.getItem(5, 5), "El semáforo debe estar en la posición (5,5)");
    }
}
