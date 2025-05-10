package test;

import domain.City;
import domain.CityException;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExportData02Test {

    @Test
    public void shouldExportDataToValidFile() {
        City city = new City();
        File file = new File("validCityExport02.txt");

        // Exportar la ciudad a un archivo válido
        assertDoesNotThrow(() -> city.exportData02(file));

        // Verificar que el archivo fue creado
        assertTrue(file.exists(), "El archivo no fue creado correctamente.");

        // Verificar el contenido del archivo
        assertDoesNotThrow(() -> {
            List<String> lines = Files.readAllLines(file.toPath());
            assertFalse(lines.isEmpty(), "El archivo exportado no debe estar vacío.");
        });

        file.delete(); // Limpieza después de la prueba
    }


    @Test
    public void shouldNotExportDataToInvalidPath() {
        City city = new City();
        File file = new File("/invalidPath/exportCity02.txt"); // Ruta inválida

        // Intentar exportar a una ruta inválida
        Exception exception = assertThrows(CityException.class, () -> city.exportData02(file));
        assertTrue(exception.getMessage().contains("Error al exportar la ciudad"), "El mensaje de error no es el esperado.");
    }

    @Test
    public void shouldNotExportDataToFileWithoutPermissions() {
        City city = new City();
        File file = new File("C:/restrictedCityExport02.txt"); // Ruta sin permisos de escritura (en sistemas protegidos)

        // Intentar exportar a un archivo sin permisos
        Exception exception = assertThrows(CityException.class, () -> city.exportData02(file));
        assertTrue(exception.getMessage().contains("Error al exportar la ciudad"), "El mensaje de error no es el esperado.");
    }

    @Test
    public void shouldOverwriteExistingFile() {
        City city = new City();
        File file = new File("existingCityExport02.txt");

        // Crear un archivo vacío antes de exportar
        assertDoesNotThrow(() -> file.createNewFile());

        // Exportar la ciudad al archivo existente
        assertDoesNotThrow(() -> city.exportData02(file));

        // Verificar que el archivo contiene datos
        assertDoesNotThrow(() -> {
            List<String> lines = Files.readAllLines(file.toPath());
            assertFalse(lines.isEmpty(), "El archivo exportado no debe estar vacío.");
        });

        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void shouldExportLargeCity() {
        City city = new City();
        // Simular una ciudad grande llenando la matriz con ítems
        for (int r = 0; r < 25; r++) {
            for (int c = 0; c < 25; c++) {
                city.setItem(r, c, new domain.Person(city, r, c));
            }
        }

        File file = new File("largeCityExport02.txt");

        // Exportar la ciudad grande
        assertDoesNotThrow(() -> city.exportData02(file));

        // Verificar que el archivo contiene datos
        assertDoesNotThrow(() -> {
            List<String> lines = Files.readAllLines(file.toPath());
            assertFalse(lines.isEmpty(), "El archivo exportado no debe estar vacío.");
        });

        file.delete(); // Limpieza después de la prueba
    }

    @Test
    public void shouldExportToFileWithSpecialCharacters() {
        City city = new City();
        File file = new File("cityExport@#$.txt");

        // Exportar la ciudad a un archivo con caracteres especiales en el nombre
        assertDoesNotThrow(() -> city.exportData02(file));

        // Verificar que el archivo fue creado
        assertTrue(file.exists(), "El archivo no fue creado correctamente.");

        // Verificar que el archivo contiene datos
        assertDoesNotThrow(() -> {
            List<String> lines = Files.readAllLines(file.toPath());
            assertFalse(lines.isEmpty(), "El archivo exportado no debe estar vacío.");
        });

        file.delete(); // Limpieza después de la prueba
    }
}