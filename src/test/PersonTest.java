package test;

import domain.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;

/**
 * Clase de pruebas unitarias para la clase Person.
 */
public class PersonTest {

    private City city;
    private Person person;

    @BeforeEach
    public void setUp() {
        city = new City();
        person = new Person(city, 5, 5);
    }

    @AfterEach
    public void tearDown() {
        city = null;
        person = null;
    }

    @Test
    public void shouldHaveInitialStateDissatisfied() {
        assertTrue(person.isDissatisfied(), "El estado inicial debe ser DISSATISFIED");
    }

    @Test
    public void shouldHaveInitialColorBlue() {
        assertEquals(Color.BLUE, person.getColor(), "El color inicial debe ser BLUE");
    }

    @Test
    public void shouldHaveInitialShapeRound() {
        assertEquals(Item.ROUND, person.shape(), "La forma inicial debe ser ROUND");
    }

    @Test
    public void shouldBeHappyAfterZeroSteps() {
        person.decide();
        assertTrue(person.isHappy(), "steps=0 → HAPPY");
    }

    @Test
    public void shouldBeIndifferentAfterOneStep() {
        person.change(); // steps = 1
        person.decide();
        assertTrue(person.isIndifferent(), "steps=1 → INDIFFERENT");
    }

    @Test
    public void shouldBeDissatisfiedAfterTwoSteps() {
        person.change(); // steps = 1
        person.change(); // steps = 2
        person.decide();
        assertTrue(person.isDissatisfied(), "steps=2 → DISSATISFIED");
    }

    @Test
    public void shouldIncrementStepsAfterChange() {
        assertEquals(0, person.getSteps(), "steps inicial debe ser 0");
        person.change();
        assertEquals(1, person.getSteps(), "steps debe ser 1");
        person.change();
        assertEquals(2, person.getSteps(), "steps debe ser 2");
    }

    @Test
    public void shouldRegisterPositionInCity() {
        assertNotNull(city.getItem(5, 5), "La persona debe estar en (5,5)");
        assertSame(person, city.getItem(5, 5), "El ítem en (5,5) debe ser la persona creada");
    }

    @Test
    public void shouldAllowPersonAtBoundaryPosition() {
        Person edgePerson = new Person(city, 0, 0);
        assertNotNull(city.getItem(0, 0), "La persona debe estar en (0,0)");
        assertEquals(0, edgePerson.getRow(), "Fila debe ser 0");
        assertEquals(0, edgePerson.getColumn(), "Columna debe ser 0");
    }

    @Test
    public void shouldIgnoreNeighborsWhenDecidingState() {
        new Person(city, 6, 6); // Vecino en (6,6)
        person.decide();
        assertTrue(person.isHappy(), "El estado debe depender solo de los steps, no de los vecinos");
    }
}