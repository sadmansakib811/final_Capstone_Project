package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import energymanagement.FileReaderUtil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class FileReaderUtilTest {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Test
    public void testShowLogFileContent_FileExists() {
        FileReaderUtil util = new FileReaderUtil();
        File testFile = new File("test.log");

        // Expected content
        String fileContent = "Test content" + LINE_SEPARATOR + "Line 2" + LINE_SEPARATOR + "Line 3";
        String expectedOutput = LINE_SEPARATOR + "Showing content of test.log:" + LINE_SEPARATOR + fileContent + LINE_SEPARATOR;

        // Create a test file with known content
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write(fileContent);
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        // Capture the output printed by the method
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            util.showLogFileContent(testFile);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } finally {
            System.setOut(originalOut);
            testFile.delete();
        }

        // Get the actual output and normalize line separators
        String actualOutput = outContent.toString().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");

        // Normalize expected output
        expectedOutput = expectedOutput.replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");

        // Assert that the actual output matches the expected output
        assertEquals(expectedOutput, actualOutput, "The output should match the expected content.");
    }

    @Test
    public void testShowLogFileContent_FileDoesNotExist() {
        FileReaderUtil util = new FileReaderUtil();
        File testFile = new File("nonexistent.log");

        // Expected output
        String expectedOutput = LINE_SEPARATOR + "Showing content of nonexistent.log:" + LINE_SEPARATOR + "The file nonexistent.log was not found." + LINE_SEPARATOR;

        // Capture the output printed by the method
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Expect an IOException to be thrown
        Exception exception = assertThrows(IOException.class, () -> {
            util.showLogFileContent(testFile);
        });

        System.setOut(originalOut);

        // Get the actual output and normalize line separators
        String actualOutput = outContent.toString().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");

        // Normalize expected output
        expectedOutput = expectedOutput.replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");

        // Assert that the actual output matches the expected output
        assertEquals(expectedOutput, actualOutput, "The output should indicate that the file was not found.");
    }

    @Test
    public void testShowLogFileContent_EmptyFile() {
        FileReaderUtil util = new FileReaderUtil();
        File testFile = new File("empty.log");

        // Expected output
        String expectedOutput = LINE_SEPARATOR + "Showing content of empty.log:" + LINE_SEPARATOR;

        // Create an empty file
        try {
            if (!testFile.createNewFile()) {
                fail("Failed to create empty test file");
            }
        } catch (IOException e) {
            fail("IOException occurred while creating empty test file");
        }

        // Capture the output printed by the method
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            util.showLogFileContent(testFile);
        } catch (IOException e) {
            fail("Exception should not have been thrown for empty file");
        } finally {
            System.setOut(originalOut);
            testFile.delete();
        }

        // Get the actual output and normalize line separators
        String actualOutput = outContent.toString().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");

        // Normalize expected output
        expectedOutput = expectedOutput.replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");

        // Assert that the actual output matches the expected output
        assertEquals(expectedOutput, actualOutput, "The output should indicate that the file is empty.");
    }

    @Test
    public void testShowLogFileContent_NullFile() {
        FileReaderUtil util = new FileReaderUtil();

        // Expect a NullPointerException to be thrown
        assertThrows(NullPointerException.class, () -> {
            util.showLogFileContent(null);
        });
    }

    @Test
    public void testShowLogFileContent_DirectoryInsteadOfFile() {
        FileReaderUtil util = new FileReaderUtil();
        File dir = new File("testDir");
        dir.mkdir();

        // Expected output
        String expectedOutput = LINE_SEPARATOR + "Showing content of testDir:" + LINE_SEPARATOR + "The file testDir was not found." + LINE_SEPARATOR;

        // Capture the output printed by the method
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Expect an IOException to be thrown
        Exception exception = assertThrows(IOException.class, () -> {
            util.showLogFileContent(dir);
        });

        System.setOut(originalOut);
        dir.delete();

        // Get the actual output and normalize line separators
        String actualOutput = outContent.toString().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");

        // Normalize expected output
        expectedOutput = expectedOutput.replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");

        // Assert that the actual output matches the expected output
        assertEquals(expectedOutput, actualOutput, "The output should indicate that the file was not found.");
    }
}
