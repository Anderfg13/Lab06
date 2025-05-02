package test;
import domain.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.Person;
import domain.City;
import java.awt.Color;

/**
 * Clase de pruebas unitarias para la clase Person.
 * Valida el comportamiento de una persona en la simulación, incluyendo su estado inicial,
 * cambios de estado, posición en la ciudad y manejo de pasos.
 */
public class PersonTest {

    // Ciudad y persona utilizadas en las pruebas
    private City city;
    private Person person;

    /**
     * Configura el entorno antes de cada prueba.
     * Crea una ciudad y una persona en la posición (5, 5).
     */
    @BeforeEach
    public void setUp() {
        city = new City();
        person = new Person(city, 5, 5);
    }

    /**
     * Limpia el entorno después de cada prueba.
     * Libera las referencias a la ciudad y la persona.
     */
    @AfterEach
    public void tearDown() {
        city = null;
        person = null;
    }

    /**
     * Verifica que el estado inicial de la persona sea DISSATISFIED.
     */
    @Test
    public void testInitialState_Dissatisfied() {
        assertTrue(person.isDissatisfied(), "El estado inicial debe ser DISSATISFIED");
    }

    /**
     * Verifica que el color inicial de la persona sea azul.
     */
    @Test
    public void testInitialColor_Blue() {
        assertEquals(Color.BLUE, person.getColor(), "El color inicial debe ser BLUE");
    }

    /**
     * Verifica que la forma inicial de la persona sea redonda.
     */
    @Test
    public void testInitialShape_Round() {
        assertEquals(Item.ROUND, person.shape(), "La forma inicial debe ser ROUND");
    }

    /**
     * Verifica que después de 0 pasos, el estado de la persona sea HAPPY.
     */
    @Test
    public void testDecide_StateAfterZeroSteps() {
        person.decide();
        assertTrue(person.isHappy(), "steps=0 → HAPPY");
    }

    /**
     * Verifica que después de 1 paso, el estado de la persona sea INDIFFERENT.
     */
    @Test
    public void testDecide_StateAfterOneStep() {
        person.change(); // steps = 1
        person.decide();
        assertTrue(person.isIndifferent(), "steps=1 → INDIFFERENT");
    }

    /**
     * Verifica que después de 2 pasos, el estado de la persona sea DISSATISFIED.
     */
    @Test
    public void testDecide_StateAfterTwoSteps() {
        person.change(); // steps = 1
        person.change(); // steps = 2
        person.decide();
        assertTrue(person.isDissatisfied(), "steps=2 → DISSATISFIED");
    }

    /**
     * Valida que el método change() incremente correctamente los pasos.
     */
    @Test
    public void testStepsIncrement_AfterChange() {
        assertEquals(0, person.getSteps(), "steps inicial debe ser 0");
        person.change();
        assertEquals(1, person.getSteps(), "steps debe ser 1");
        person.change();
        assertEquals(2, person.getSteps(), "steps debe ser 2");
    }

    /**
     * Verifica que la persona se registre correctamente en la posición (5,5) de la ciudad.
     */
    @Test
    public void testPositionRegisteredInCity() {
        assertNotNull(city.getItem(5, 5), "La persona debe estar en (5,5)");
        assertSame(person, city.getItem(5, 5), "El ítem en (5,5) debe ser la persona creada");
    }

    /**
     * Valida que una persona pueda colocarse en el borde de la ciudad (0,0) sin errores.
     */
    @Test
    public void testPersonAtBoundaryPosition() {
        Person edgePerson = new Person(city, 0, 0);
        assertNotNull(city.getItem(0, 0), "La persona debe estar en (0,0)");
        assertEquals(0, edgePerson.getRow(), "Fila debe ser 0");
        assertEquals(0, edgePerson.getColumn(), "Columna debe ser 0");
    }

    /**
     * Verifica que la presencia de vecinos no afecte el estado de la persona.
     */
    @Test
    public void testPersonIgnoresNeighbors() {
        new Person(city, 6, 6); // Vecino en (6,6)
        person.decide();
        assertTrue(person.isHappy(), "El estado debe depender solo de los steps, no de los vecinos");
    }
}