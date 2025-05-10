package test;

import domain.City;
import domain.CityException;

import java.io.File;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Open01Test {

    @Test
    public void shouldopen01ValidFile() {
        City city = new City();
        File file = new File("validCity.dat");

        // Guardar la ciudad en un archivo válido
        assertDoesNotThrow(() -> city.save(file));

        // Abrir la ciudad desde el archivo
        assertDoesNotThrow(() -> {
            City loadedCity = City.open01(file);
            assertNotNull(loadedCity, "La ciudad cargada no debe ser nula.");
            assertEquals(city.getSize(), loadedCity.getSize(), "El tamaño de la ciudad cargada no coincide.");
        });

        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void shouldNotopen01FileWithInvalidExtension() {
        File file = new File("invalidCity.txt");

        Exception exception = assertThrows(CityException.class, () -> {
            City.open01(file);
        });

        assertTrue(exception.getMessage().contains("Error al abrir la ciudad"), "El mensaje de error no es el esperado.");
    }

    @Test
    public void shouldNotopen01NonExistentFile() {
        File file = new File("nonExistentCity.dat");

        Exception exception = assertThrows(CityException.class, () -> {
            City.open01(file);
        });

        assertTrue(exception.getMessage().contains("Error al abrir la ciudad"), "El mensaje de error no es el esperado.");
    }

    @Test
    public void shouldNotopen01CorruptedFile() {
        File file = new File("corruptedCity.dat");

        // Crear un archivo vacío o con datos no válidos
        assertDoesNotThrow(() -> file.createNewFile());

        Exception exception = assertThrows(CityException.class, () -> {
            City.open01(file);
        });

        assertTrue(exception.getMessage().contains("Error al abrir la ciudad"), "El mensaje de error no es el esperado.");

        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void shouldNotopen01EmptyFile() {
        File file = new File("emptyCity.dat");

        // Crear un archivo vacío
        assertDoesNotThrow(() -> file.createNewFile());

        Exception exception = assertThrows(CityException.class, () -> {
            City.open01(file);
        });

        assertTrue(exception.getMessage().contains("Error al abrir la ciudad"), "El mensaje de error no es el esperado.");

        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void shouldopen01FileWithLargeCity() {
        City city = new City();
        // Simular una ciudad grande llenando la matriz con ítems
        for (int r = 0; r < 25; r++) {
            for (int c = 0; c < 25; c++) {
                city.setItem(r, c, new domain.Person(city, r, c));
            }
        }

        File file = new File("largeCity.dat");

        // Guardar la ciudad grande
        assertDoesNotThrow(() -> city.save(file));

        // Abrir la ciudad grande
        assertDoesNotThrow(() -> {
            City loadedCity = City.open01(file);
            assertNotNull(loadedCity, "La ciudad cargada no debe ser nula.");
            assertEquals(city.getSize(), loadedCity.getSize(), "El tamaño de la ciudad cargada no coincide.");
        });

        file.delete(); // Limpieza después de la prueba
    }
}
