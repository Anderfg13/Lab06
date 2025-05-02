package test;

import domain.StreetLight;
import domain.City;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase StreetLight.
 * Verifica el comportamiento de un poste de luz en la simulación de la ciudad,
 * incluyendo su cambio de color cada tres turnos, su posición inicial y su naturaleza no agente.
 */
public class StreetLightTest {

    /**
     * Prueba que verifica que el poste de luz cambia de color cada tres turnos.
     * El color alterna entre gris oscuro (apagado) y naranja (encendido).
     */
    @Test
    public void shouldChangeColorEveryThreeTurns() {
        City city = new City();
        StreetLight streetLight = new StreetLight(city, 10, 10);

        // Verificar el color inicial (debe ser DARK_GRAY)
        assertEquals(Color.DARK_GRAY, streetLight.getColor());

        // Cambiar 2 turnos (el color no debería cambiar)
        streetLight.change();
        streetLight.change();
        assertEquals(Color.DARK_GRAY, streetLight.getColor());

        // Cambiar un tercer turno (el color debería cambiar a ORANGE)
        streetLight.change();
        assertEquals(Color.ORANGE, streetLight.getColor());

        // Cambiar otros 3 turnos (el color debería volver a DARK_GRAY)
        streetLight.change();
        streetLight.change();
        streetLight.change();
        assertEquals(Color.DARK_GRAY, streetLight.getColor());
    }

    /**
     * Prueba que verifica que el poste de luz no es un agente.
     */
    @Test
    public void shouldNotBeAnAgent() {
        City city = new City();
        StreetLight streetLight = new StreetLight(city, 10, 10);

        // Verificar que no es un agente
        assertFalse(streetLight.isAgent());
    }

    /**
     * Prueba que verifica que el poste de luz se inicializa correctamente en la posición especificada
     * y que su estado inicial es "apagado" (gris oscuro).
     */
    @Test
    public void shouldInitializeWithCorrectPositionAndInitialState() {
        City city = new City();
        int row = 10, column = 10;
        StreetLight streetLight = new StreetLight(city, row, column);

        // Verificar la posición
        assertEquals(streetLight, city.getItem(row, column));

        // Verificar el estado inicial (apagado)
        assertEquals(Color.DARK_GRAY, streetLight.getColor());
    }
}