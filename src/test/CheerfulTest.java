package test;
import domain.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas unitarias para la clase Cheerful.
 * Verifica el comportamiento de una persona alegre (Cheerful) en la simulación de la ciudad,
 * incluyendo su estado emocional y su capacidad para influenciar a sus vecinos.
 */
public class CheerfulTest {

    /**
     * Prueba que verifica que una persona Cheerful siempre está feliz.
     * Se asegura de que el estado inicial sea HAPPY y que siga siendo HAPPY después de decidir.
     */
    @Test
    public void shouldAlwaysBeHappy() {
        // Crear una ciudad y un Cheerful
        City city = new City();
        Cheerful cheerful = new Cheerful(city, 10, 10);

        // Verificar que el estado inicial es HAPPY
        assertEquals(Agent.HAPPY, cheerful.isHappy() ? Agent.HAPPY : Agent.DISSATISFIED);

        // Llamar a decide() y verificar que sigue feliz
        cheerful.decide();
        assertEquals(Agent.HAPPY, cheerful.isHappy() ? Agent.HAPPY : Agent.DISSATISFIED);
    }

    /**
     * Prueba que verifica que una persona Cheerful hace felices a sus vecinos.
     * Se asegura de que los vecinos dentro del rango de influencia cambien su estado a HAPPY.
     */
    @Test
    public void shouldMakeNeighborsHappy() {
        // Crear una ciudad, un Cheerful y algunos vecinos
        City city = new City();
        Cheerful cheerful = new Cheerful(city, 10, 10);
        Person neighbor1 = new Person(city, 9, 10); // Arriba de Cheerful
        Person neighbor2 = new Person(city, 10, 9); // A la izquierda de Cheerful

        // Verificar que los vecinos no están felices inicialmente
        assertFalse(neighbor1.isHappy());
        assertFalse(neighbor2.isHappy());

        // Cheerful actúa
        cheerful.decide();

        // Verificar que los vecinos ahora están felices
        assertTrue(neighbor1.isHappy());
        assertTrue(neighbor2.isHappy());
    }

    /**
     * Prueba que verifica que una persona Cheerful no afecta a personas fuera de su rango de influencia.
     * Se asegura de que las personas fuera del rango 3x3 no cambien su estado.
     */
    @Test
    public void shouldNotAffectOutOfRangePersons() {
        // Crear una ciudad y un Cheerful
        City city = new City();
        Cheerful cheerful = new Cheerful(city, 10, 10);
    
        // Crear una persona fuera del rango de radiación de Cheerful
        Person outOfRangePerson = new Person(city, 13, 13); // Fuera del rango 3x3
    
        // Verificar que la persona fuera de rango no está feliz inicialmente
        assertFalse(outOfRangePerson.isHappy());
    
        // Cheerful actúa
        cheerful.decide();
    
        // Verificar que la persona fuera de rango sigue sin estar feliz
        assertFalse(outOfRangePerson.isHappy());
    }
}
