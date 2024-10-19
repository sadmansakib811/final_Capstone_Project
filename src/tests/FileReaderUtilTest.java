package tests;

import energymanagement.FileReaderUtil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderUtilTest {

    @Test
    public void testShowLogFileContent_FileExists() {
        System.out.println("Starting test: testShowLogFileContent_FileExists");
        FileReaderUtil util = new FileReaderUtil();
        File testFile = new File("test.log");

        // Create a test file
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Test content\nLine 2\nLine 3");
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        try {
            util.showLogFileContent(testFile);
            // If no exception is thrown, the test passes
            System.out.println("Test passed: testShowLogFileContent_FileExists");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } finally {
            testFile.delete();
        }

        System.out.println("**********************************");
    }

    @Test
    public void testShowLogFileContent_FileDoesNotExist() {
        System.out.println("Starting test: testShowLogFileContent_FileDoesNotExist");
        FileReaderUtil util = new FileReaderUtil();
        File testFile = new File("nonexistent.log");

        assertThrows(IOException.class, () -> {
            util.showLogFileContent(testFile);
        });
        System.out.println("Test passed: testShowLogFileContent_FileDoesNotExist");
        System.out.println("********************************");
    }

    @Test
    public void testShowLogFileContent_EmptyFile() {
        System.out.println("Starting test: testShowLogFileContent_EmptyFile");
        FileReaderUtil util = new FileReaderUtil();
        File testFile = new File("empty.log");

        // Create an empty file
        try {
            if (!testFile.createNewFile()) {
                fail("Failed to create empty test file");
            }
        } catch (IOException e) {
            fail("IOException occurred while creating empty test file");
        }

        try {
            util.showLogFileContent(testFile);
            // The method should handle empty files gracefully
            System.out.println("Test passed: testShowLogFileContent_EmptyFile");
        } catch (IOException e) {
            fail("Exception should not have been thrown for empty file");
        } finally {
            testFile.delete();
        }
        System.out.println("**********************************");
    }

    @Test
    public void testShowLogFileContent_NullFile() {
        System.out.println("Starting test: testShowLogFileContent_NullFile");
        FileReaderUtil util = new FileReaderUtil();

        assertThrows(NullPointerException.class, () -> {
            util.showLogFileContent(null);
        });
        System.out.println("Test passed: testShowLogFileContent_NullFile");
        System.out.println("**********************************");
    }

    @Test
    public void testShowLogFileContent_DirectoryInsteadOfFile() {
        System.out.println("Starting test: testShowLogFileContent_DirectoryInsteadOfFile");
        FileReaderUtil util = new FileReaderUtil();
        File dir = new File("testDir");
        dir.mkdir();

        assertThrows(IOException.class, () -> {
            util.showLogFileContent(dir);
        });
        System.out.println("Test passed: testShowLogFileContent_DirectoryInsteadOfFile");
        System.out.println("**********************************");

        dir.delete();
    }
}
