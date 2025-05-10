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
    public void shouldCycleColorsCorrectly() {
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
    public void shouldBePlacedCorrectlyInCity() {
        TrafficLight testLight = new TrafficLight(city, 5, 5);
        assertNotNull(city.getItem(5, 5), "El semáforo debe estar en la posición (5,5)");
        assertSame(testLight, city.getItem(5, 5), "El ítem en (5,5) debe ser el semáforo creado");
    }

    /**
     * Prueba que el semáforo tenga el color inicial correcto.
     */
    @Test
    public void shouldHaveInitialColorRed() {
        assertEquals(Color.RED, light.getColor(), "El color inicial del semáforo debe ser ROJO");
    }

    /**
     * Prueba que el semáforo cambie al color amarillo después de un cambio.
     */
    @Test
    public void shouldChangeToYellowAfterOneChange() {
        light.change(); // Cambiar de ROJO a AMARILLO
        assertEquals(Color.YELLOW, light.getColor(), "El color debe ser AMARILLO después de un cambio");
    }

    /**
     * Prueba que el semáforo cambie al color verde después de dos cambios.
     */
    @Test
    public void shouldChangeToGreenAfterTwoChanges() {
        light.change(); // Cambiar de ROJO a AMARILLO
        light.change(); // Cambiar de AMARILLO a VERDE
        assertEquals(Color.GREEN, light.getColor(), "El color debe ser VERDE después de dos cambios");
    }

    /**
     * Prueba que el semáforo cambie al color amarillo después de tres cambios.
     */
    @Test
    public void shouldChangeToYellowAfterThreeChanges() {
        light.change(); // Cambiar de ROJO a AMARILLO
        light.change(); // Cambiar de AMARILLO a VERDE
        light.change(); // Cambiar de VERDE a AMARILLO
        assertEquals(Color.YELLOW, light.getColor(), "El color debe ser AMARILLO después de tres cambios");
    }

    /**
     * Prueba que el semáforo vuelva al color rojo después de cuatro cambios.
     */
    @Test
    public void shouldReturnToRedAfterFourChanges() {
        for (int i = 0; i < 4; i++) {
            light.change();
        }
        assertEquals(Color.RED, light.getColor(), "El color debe ser ROJO después de cuatro cambios");
    }
}