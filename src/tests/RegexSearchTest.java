package tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import energymanagement.RegexSearch;

public class RegexSearchTest {

    @Test
    public void testSearchLog_ValidRegex_MatchFound() throws IOException {
        RegexSearch search = new RegexSearch();
        File testFile = new File("test.log");

        // Create a test file with sample content
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Energy Consumption: 300 kWh\nLighting: 100 kWh\n");
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        try {
            search.searchLog(testFile, "\\d+ kWh");
            System.out.println("Test passed: testSearchLog_ValidRegex_MatchFound");
        } finally {
            testFile.delete();
        }
        System.out.println("**********************************");
    }

    @Test
    public void testSearchLog_ValidRegex_NoMatchFound() throws IOException {
        RegexSearch search = new RegexSearch();
        File testFile = new File("test.log");

        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("No numbers here\n");
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        try {
            search.searchLog(testFile, "\\d+ kWh");
            System.out.println("Test passed: testSearchLog_ValidRegex_NoMatchFound");
        } finally {
            testFile.delete();
        }
        System.out.println("**********************************");
    }

    @Test
    public void testSearchLog_FileDoesNotExist() {
        RegexSearch search = new RegexSearch();
        File testFile = new File("nonexistent.log");

        assertThrows(IOException.class, () -> {
            search.searchLog(testFile, "\\d+ kWh");
        });
        System.out.println("Test passed: testSearchLog_FileDoesNotExist");
        System.out.println("**********************************");
    }

    @Test
    public void testSearchLog_NullFile() {
        RegexSearch search = new RegexSearch();

        assertThrows(NullPointerException.class, () -> {
            search.searchLog(null, "\\d+ kWh");
        });
        System.out.println("Test passed: testSearchLog_NullFile");
        System.out.println("**********************************");
    }
}
