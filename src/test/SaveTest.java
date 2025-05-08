package test;

import domain.City;
import domain.CityException;

import java.io.File;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SaveTest {

    @Test
    public void testSaveValidFile() {
        City city = new City();
        File file = new File("testCity.dat");

        assertDoesNotThrow(() -> {
            city.save(file);
        });

        assertTrue(file.exists(), "El archivo no fue creado correctamente.");
        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void testSaveInvalidExtension() {
        City city = new City();
        File file = new File("testCity.txt");

        Exception exception = assertThrows(CityException.class, () -> {
            city.save(file);
        });

        assertEquals("El archivo debe tener la extensión .dat", exception.getMessage());
    }

    @Test
    public void testSaveIOException() {
        City city = new City();
        File file = new File("/invalidPath/testCity.dat"); // Ruta inválida

        Exception exception = assertThrows(CityException.class, () -> {
            city.save(file);
        });

        assertTrue(exception.getMessage().contains("Error al guardar la ciudad"));
    }

    @Test
    public void testSaveEmptyCity() {
        City city = new City(); // Ciudad vacía
        File file = new File("emptyCity.dat");

        assertDoesNotThrow(() -> {
            city.save(file);
        });

        assertTrue(file.exists(), "El archivo no fue creado correctamente.");
        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void testSaveFileAlreadyExists() {
        City city = new City();
        File file = new File("existingCity.dat");

        // Crear un archivo vacío antes de guardar
        assertDoesNotThrow(() -> file.createNewFile());

        assertDoesNotThrow(() -> {
            city.save(file);
        });

        assertTrue(file.exists(), "El archivo no fue sobrescrito correctamente.");
        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void testSaveLargeCity() {
        City city = new City();
        // Simular una ciudad grande llenando la matriz con ítems
        for (int r = 0; r < 25; r++) {
            for (int c = 0; c < 25; c++) {
                city.setItem(r, c, new domain.Person(city, r, c));
            }
        }

        File file = new File("largeCity.dat");

        assertDoesNotThrow(() -> {
            city.save(file);
        });

        assertTrue(file.exists(), "El archivo no fue creado correctamente para una ciudad grande.");
        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void testSaveFileWithoutPermissions() {
        City city = new City();
        File file = new File("C:/restrictedCity.dat"); // Ruta donde no se tienen permisos de escritura

        Exception exception = assertThrows(CityException.class, () -> {
            city.save(file);
        });

        assertTrue(exception.getMessage().contains("Error al guardar la ciudad"), "El mensaje de error no es el esperado.");
    }
}
