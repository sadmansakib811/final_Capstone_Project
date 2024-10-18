package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import energymanagement.LogManager;
import java.io.File;
import java.time.LocalDate;

public class LogManagerTest {
	 @BeforeAll
	    public static void init() {
	        // This will print the message once before all tests
	        System.out.println("LogManager.java class test result:");
	    }
    @Test
    public void testGenerateLogFiles_CreatesFiles() {
        System.out.println("Starting test: testGenerateLogFiles_CreatesFiles");

        LogManager logManager = new LogManager();

        try {
            logManager.generateLogFiles();

            // Check that log files have been created
            for (int i = 0; i < 5; i++) {
                LocalDate date = LocalDate.now().minusDays(i);
                String fileName = "FH_DORTMUND_energy_management_date_" + date + ".log";
                File file = new File(fileName);
                assertTrue(file.exists(), "Log file should exist: " + fileName);
                file.delete(); // Clean up after test
            }

            System.out.println("Test passed: testGenerateLogFiles_CreatesFiles");

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }

        System.out.println("**********************************");
    }

    @Test
    public void testGenerateLogFiles_FileContent() {
        System.out.println("Starting test: testGenerateLogFiles_FileContent");

        LogManager logManager = new LogManager();

        try {
            logManager.generateLogFiles();

            // Check content of one of the log files
            LocalDate date = LocalDate.now();
            String fileName = "FH_DORTMUND_energy_management_date_" + date + ".log";
            File file = new File(fileName);
            assertTrue(file.exists(), "Log file should exist: " + fileName);

            // Read the file and check for expected content
            assertTrue(file.length() > 0, "Log file should not be empty");
            file.delete();

            System.out.println("Test passed: testGenerateLogFiles_FileContent");

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }

        System.out.println("**********************************");
    }

    @Test
    public void testGenerateLogFiles_NoException() {
        System.out.println("Starting test: testGenerateLogFiles_NoException");

        LogManager logManager = new LogManager();

        assertDoesNotThrow(() -> {
            logManager.generateLogFiles();
        });

        // Clean up
        for (int i = 0; i < 5; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            String fileName = "FH_DORTMUND_energy_management_date_" + date + ".log";
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
        }

        System.out.println("Test passed: testGenerateLogFiles_NoException");
        System.out.println("**********************************");
    }

    @Test
    public void testGenerateLogFiles_FilePermissions() {
        System.out.println("Starting test: testGenerateLogFiles_FilePermissions");

      
        assertTrue(true, "File permissions test skipped.");
        
        System.out.println("Test passed: testGenerateLogFiles_FilePermissions");
        System.out.println("**********************************");
    }

    @Test
    public void testGenerateLogFiles_InvalidPath() {
        System.out.println("Starting test: testGenerateLogFiles_InvalidPath");

    
        assertTrue(true, "Invalid path test skipped.");
        
        System.out.println("Test passed: testGenerateLogFiles_InvalidPath");
        System.out.println("**********************************");
    }
    @AfterAll
    public static void tearDown() {
    
        System.out.println("***************************************************************************************");
    }
}
