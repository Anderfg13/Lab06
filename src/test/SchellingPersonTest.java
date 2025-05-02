package test;

import domain.SchellingPerson;
import domain.Person;
import domain.City;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SchellingPersonTest {


    @Test
    public void shouldBeDissatisfiedWhenFewSimilarNeighbors() {
        City city = new City();
        SchellingPerson person1 = new SchellingPerson(city, 5, 5);
        new Person(city, 4, 5); // Vecino diferente (no similar)

        person1.decide();
        assertTrue(person1.isDissatisfied(), "Debe estar DISSATISFIED con pocos vecinos similares");
    }

    @Test
    public void shouldBeHappyWhenEnoughSimilarNeighbors() {
        City city = new City();
        SchellingPerson person1 = new SchellingPerson(city, 5, 5);
        new SchellingPerson(city, 4, 5); // Vecino similar
        new SchellingPerson(city, 6, 5); // Vecino similar
        new Person(city, 5, 4); // Vecino diferente

        person1.decide();
        assertTrue(person1.isHappy(), "Debe estar HAPPY con suficientes vecinos similares");
    }
}