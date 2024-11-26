package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import energymanagement.LogManager;
import energymanagement.EnergySimulator;
import java.io.File;
import java.time.LocalDate;

public class LogManagerTest {

    @Test
    public void testGenerateLogFiles_CreatesFiles() {
        // Arrange-i have Set up everything needed for the test.
        EnergySimulator simulator = new EnergySimulator();
        LogManager logManager = new LogManager(simulator);

        try {
            // Act-This is typically where I call a method or trigger functionality.
            logManager.generateLogFiles();

            
            for (int i = 0; i < 5; i++) {
                LocalDate date = LocalDate.now().minusDays(i);
                String fileName = "FH_DORTMUND_energy_management_date_" + date + ".log";
                File file = new File(fileName);

                boolean expectedResult = true; // File should exist
                boolean actualResult = file.exists();

                //assert:  Check if the file exists, assert
                assertEquals(expectedResult, actualResult,
                        "Expected the log file to exist, but it does not: " + fileName);

                // Clean up after test
                file.delete();
            }
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }

    @Test
    public void testLogEvent_AddsToLogBuffer() {
        // Arrange
        EnergySimulator simulator = new EnergySimulator();
        LogManager logManager = new LogManager(simulator);
        String eventToLog = "Test Event";

        // Act
        logManager.logEvent(eventToLog);

        // Assert
        boolean expectedResult = true; // Event should be in the log buffer
        boolean actualResult = logManager.getLogs().contains(eventToLog);

        assertEquals(expectedResult, actualResult,
                "Expected the log buffer to contain the event '" + eventToLog + "', but it does not.");
    }

    @Test
    public void testLogEvent_WritesToDailyLogFile() {
        // Arrange
        EnergySimulator simulator = new EnergySimulator();
        LogManager logManager = new LogManager(simulator);
        String dailyLogFileName = "energy_management_log_" + LocalDate.now() + ".log";
        File dailyLogFile = new File(dailyLogFileName);

        // Clean up if the file already exists
        if (dailyLogFile.exists()) {
            dailyLogFile.delete();
        }

        String eventToLog = "Test Event";

        // Act
        logManager.logEvent(eventToLog);

        // Assert
        boolean expectedResult = true; // Daily log file should exist
        boolean actualResult = dailyLogFile.exists();

        assertEquals(expectedResult, actualResult,
                "Expected the daily log file to be created, but it was not: " + dailyLogFileName);

        // Clean up
        dailyLogFile.delete();
    }
}
