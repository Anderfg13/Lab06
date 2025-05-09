package test;

import domain.City;
import domain.CityException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ImportTest {

    @Test
    public void shouldImportValidFile() {
        City city = new City();
        File file = new File("validCityImport.txt");

        // Crear un archivo válido
        assertDoesNotThrow(() -> {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Person 10 10\n");
                writer.write("Walker 20 20\n");
                writer.write("TrafficLight 0 0\n");
            }
        });

        // Importar el archivo
        assertDoesNotThrow(() -> city.importData(file));

        // Verificar que los ítems se hayan importado correctamente
        assertNotNull(city.getItem(10, 10), "El ítem en (10, 10) no fue importado correctamente.");
        assertNotNull(city.getItem(20, 20), "El ítem en (20, 20) no fue importado correctamente.");
        assertNotNull(city.getItem(0, 0), "El ítem en (0, 0) no fue importado correctamente.");

        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void shouldNotImportFileWithInvalidFormat() {
        City city = new City();
        File file = new File("invalidFormatImport.txt");

        // Crear un archivo con formato incorrecto
        assertDoesNotThrow(() -> {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("InvalidLine\n"); // Línea mal formateada
            }
        });

        // Intentar importar el archivo
        Exception exception = assertThrows(CityException.class, () -> city.importData(file));
        assertTrue(exception.getMessage().contains("Formato de línea inválido"), "El mensaje de error no es el esperado.");

        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void shouldNotImportFileWithOutOfBoundsCoordinates() {
        City city = new City();
        File file = new File("outOfBoundsImport.txt");

        // Crear un archivo con coordenadas fuera de los límites
        assertDoesNotThrow(() -> {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Person 100 100\n");
            }
        });

        // Intentar importar el archivo
        Exception exception = assertThrows(CityException.class, () -> city.importData(file));
        assertTrue(exception.getMessage().contains("Row or column out of bounds"), "El mensaje de error no es el esperado.");

        file.delete(); // Limpieza después de la prueba
    }

}
