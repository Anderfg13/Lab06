package test;

import domain.City;
import domain.CityException;

import java.io.File;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SaveTest {

    @Test
    public void shouldSaveToValidFile() {
        City city = new City();
        File file = new File("testCity.dat");

        assertDoesNotThrow(() -> {
            city.save(file);
        });

        assertTrue(file.exists(), "El archivo no fue creado correctamente.");
        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void shouldNotSaveToInvalidPath() {
        City city = new City();
        File file = new File("/invalidPath/testCity.dat"); // Ruta inválida

        Exception exception = assertThrows(CityException.class, () -> {
            city.save(file);
        });

        assertTrue(exception.getMessage().contains("Error al guardar la ciudad"), "El mensaje de error no es el esperado.");
    }

    @Test
    public void shouldOverwriteExistingFile() {
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
    public void shouldNotSaveToFileWithoutPermissions() {
        City city = new City();
        File file = new File("C:/restrictedCity.dat"); // Ruta sin permisos de escritura (en sistemas protegidos)

        Exception exception = assertThrows(CityException.class, () -> {
            city.save(file);
        });

        assertTrue(exception.getMessage().contains("Error al guardar la ciudad"), "El mensaje de error no es el esperado.");
    }
}