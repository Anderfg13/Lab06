package test;

import domain.City;
import domain.CityException;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExporTest {

    @Test
    public void shouldexportDataToValidFile() {
        City city = new City();
        File file = new File("validCityexportData.txt");

        assertDoesNotThrow(() -> city.exportData(file));

        assertTrue(file.exists(), "El archivo no fue creado correctamente.");

        // Verificar el contenido del archivo
        assertDoesNotThrow(() -> {
            List<String> lines = Files.readAllLines(file.toPath());
            assertFalse(lines.isEmpty(), "El archivo exportDataado no debe estar vacío.");
            assertTrue(lines.stream().anyMatch(line -> line.contains("Person 10 10")), "El archivo no contiene los datos esperados.");
        });

        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void shouldNotExportDataToInvalidPath() {
        City city = new City();
        File file = new File("/invalidPath/exportDataCity.txt"); // Ruta inválida

        Exception exception = assertThrows(CityException.class, () -> city.exportData(file));

        assertTrue(exception.getMessage().contains("Error al exportar la ciudad"), "El mensaje de error no es el esperado.");
    }

    @Test
    public void shouldNotExportDataToFileWithoutPermissions() {
        City city = new City();
        File file = new File("C:/restrictedCityexportData.txt"); // Ruta sin permisos de escritura (en sistemas protegidos)

        Exception exception = assertThrows(CityException.class, () -> city.exportData(file));

        assertTrue(exception.getMessage().contains("Error al exportar la ciudad"), "El mensaje de error no es el esperado.");
    }

    @Test
    public void shouldOverwriteExistingFile() {
        City city = new City();
        File file = new File("existingCityexportData.txt");

        // Crear un archivo vacío antes de exportDataar
        assertDoesNotThrow(() -> file.createNewFile());

        assertDoesNotThrow(() -> city.exportData(file));

        assertTrue(file.exists(), "El archivo no fue sobrescrito correctamente.");

        // Verificar que el archivo contiene datos
        assertDoesNotThrow(() -> {
            List<String> lines = Files.readAllLines(file.toPath());
            assertFalse(lines.isEmpty(), "El archivo exportDataado no debe estar vacío.");
        });

        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void shouldexportDataLargeCity() {
        City city = new City();
        // Simular una ciudad grande llenando la matriz con ítems
        for (int r = 0; r < 25; r++) {
            for (int c = 0; c < 25; c++) {
                city.setItem(r, c, new domain.Person(city, r, c));
            }
        }

        File file = new File("largeCityexportData.txt");

        assertDoesNotThrow(() -> city.exportData(file));

        assertTrue(file.exists(), "El archivo no fue creado correctamente.");

        // Verificar que el archivo contiene datos
        assertDoesNotThrow(() -> {
            List<String> lines = Files.readAllLines(file.toPath());
            assertFalse(lines.isEmpty(), "El archivo exportDataado no debe estar vacío.");
        });

        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void shouldexportDataToFileWithSpecialCharacters() {
        City city = new City();
        File file = new File("cityexportData@#$.txt");

        assertDoesNotThrow(() -> city.exportData(file));

        assertTrue(file.exists(), "El archivo no fue creado correctamente.");

        // Verificar que el archivo contiene datos
        assertDoesNotThrow(() -> {
            List<String> lines = Files.readAllLines(file.toPath());
            assertFalse(lines.isEmpty(), "El archivo exportDataado no debe estar vacío.");
        });

        file.delete(); // Limpieza después de la prueba
    }
}