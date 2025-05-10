package test;

import domain.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;

/**
 * Clase de pruebas unitarias para la clase Walker.
 * Verifica el comportamiento de un caminante (Walker) en la simulación de la ciudad,
 * incluyendo su estado inicial, movimiento y reacciones en situaciones específicas.
 */
public class WalkerTest {
    // Ciudad y caminante utilizados en las pruebas
    private City city;
    private Walker walker;

    /**
     * Configura el entorno de prueba antes de cada método de prueba.
     * Inicializa una ciudad y un caminante en la posición (10, 10).
     */
    @BeforeEach
    public void setUp() {
        city = new City();
        walker = new Walker(city, 10, 10);
    }

    /**
     * Limpia el entorno de prueba después de cada método de prueba.
     * Libera los recursos utilizados en las pruebas.
     */
    @AfterEach
    public void tearDown() {
        city = null;
        walker = null;
    }

    /**
     * Prueba que verifica el estado inicial y las propiedades del caminante.
     */
    @Test
    public void shouldHaveInitialStateIndifferent() {
        assertTrue(walker.isIndifferent(), "Estado inicial debe ser INDIFFERENT");
        assertEquals(Item.SQUARE, walker.shape(), "Forma debe ser SQUARE");
        assertEquals(Color.green, walker.getColor(), "Color debe ser verde");
    }

    /**
     * Prueba que verifica el movimiento exitoso del caminante hacia el norte.
     */
    @Test
    public void shouldMoveWhenSpaceAvailable() {
        city.ticTac();
        assertNull(city.getItem(10, 10), "Posición original debe estar vacía");
        assertNotNull(city.getItem(9, 10), "Debe estar en la nueva posición (9,10)");
        assertTrue(walker.isHappy(), "Debe estar HAPPY al moverse");
    }

    /**
     * Prueba que verifica el comportamiento del caminante en el límite superior de la ciudad.
     */
    @Test
    public void shouldNotMoveAtCityBoundary() {
        Walker edgeWalker = new Walker(city, 0, 5);
        city.ticTac();
        assertNotNull(city.getItem(0, 5), "Debe permanecer en (0,5)");
        assertTrue(edgeWalker.isDissatisfied(), "Debe estar DISSATISFIED");
    }

    /**
     * Prueba que verifica el movimiento múltiple del caminante.
     */
    @Test
    public void shouldMoveConsecutively() {
        for (int i = 0; i < 3; i++) {
            city.ticTac();
        }
        assertNotNull(city.getItem(7, 10), "Debe estar en (7,10)");
        assertTrue(walker.isHappy(), "Debe estar HAPPY");
    }

    /**
     * Prueba que verifica que el caminante se registre correctamente en la ciudad.
     */
    @Test
    public void shouldRegisterPositionInCity() {
        assertNotNull(city.getItem(10, 10), "El caminante debe estar en la posición inicial (10,10)");
        assertSame(walker, city.getItem(10, 10), "El ítem en (10,10) debe ser el caminante creado");
    }
}