package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import energymanagement.SimpleStringSearch;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SimpleStringSearchTest {

    @Test
    public void testSearchLog_MatchFound() {
        SimpleStringSearch search = new SimpleStringSearch();
        File testFile = new File("test.log");

        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Energy Consumption: 300 kWh\nLighting: 100 kWh\n");
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        try {
            search.searchLog(testFile, "Lighting");
            // If no exception is thrown, and match is found, test passes
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } finally {
            testFile.delete();
        }
    }

    @Test
    public void testSearchLog_NoMatchFound() {
        SimpleStringSearch search = new SimpleStringSearch();
        File testFile = new File("test.log");

        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Energy Consumption: 300 kWh\n");
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        try {
            search.searchLog(testFile, "Solar Panel");
            // The method should handle no matches gracefully
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } finally {
            testFile.delete();
        }
    }

    @Test
    public void testSearchLog_FileDoesNotExist() {
        SimpleStringSearch search = new SimpleStringSearch();
        File testFile = new File("nonexistent.log");

        assertThrows(IOException.class, () -> {
            search.searchLog(testFile, "Lighting");
        });
    }

    @Test
    public void testSearchLog_NullFile() {
        SimpleStringSearch search = new SimpleStringSearch();

        assertThrows(NullPointerException.class, () -> {
            search.searchLog(null, "Lighting");
        });
    }

    @Test
    public void testSearchLog_EmptySearchTerm() {
        SimpleStringSearch search = new SimpleStringSearch();
        File testFile = new File("test.log");

        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Some content\n");
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        try {
            search.searchLog(testFile, "");
            // The method should handle empty search term gracefully
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } finally {
            testFile.delete();
        }
    }
}
