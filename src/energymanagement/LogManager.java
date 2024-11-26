package energymanagement;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LogManager {
    private static final String DAILY_LOG_FILE_NAME = "energy_management_log_" + LocalDate.now() + ".log";
    private static final String FILENAME_PREFIX = "FH_DORTMUND_energy_management_date_";
    private EnergySimulator energySimulator; // Reference to EnergySimulator
    private List<String> logBuffer = new ArrayList<>(); // Stores recent logs for in-memory display

    // Constructor that accepts EnergySimulator as a parameter
    public LogManager(EnergySimulator energySimulator) {
        this.energySimulator = energySimulator;
    }

    // Method to generate historical log files based on EnergySimulator data
    public void generateLogFiles() throws IOException {
        for (int i = 0; i < 5; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            String fileName = FILENAME_PREFIX + date.toString() + ".log";

            try (FileWriter writer = new FileWriter(fileName)) {
                int windTurbine = energySimulator.simulateWindTurbine();
                int solarPanel = energySimulator.simulateSolarPanel();
                int totalGenerated = windTurbine + solarPanel;

                int lighting = energySimulator.simulateLighting();
                int heating = energySimulator.simulateHeating();
                int hbahn = energySimulator.simulateHBahn();
                int totalConsumed = lighting + heating + hbahn;

                writer.write("Energy Generation:\n");
                writer.write("Wind Turbine: " + windTurbine + " kWh\n");
                writer.write("Solar Panel: " + solarPanel + " kWh\n");
                writer.write("Total Generated: " + totalGenerated + " kWh\n\n");

                writer.write("Energy Consumption:\n");
                writer.write("Lighting: " + lighting + " kWh\n");
                writer.write("Heating: " + heating + " kWh\n");
                writer.write("H-Bahn: " + hbahn + " kWh\n");
                writer.write("Total Consumed: " + totalConsumed + " kWh\n\n");

                int energyBalance = totalGenerated - totalConsumed;
                writer.write("Energy Balance: " + energyBalance + " kWh\n");

                // Log creation message for the file
                String logEntry = "Log created for " + date.toString() + " at path: " + Paths.get(fileName).toAbsolutePath();
                logBuffer.add(logEntry);  // Add to in-memory log
                System.out.println(logEntry);

            } catch (IOException e) {
                String errorLog = "Error creating log file for " + date.toString();
                logBuffer.add(errorLog);  // Add error to in-memory log
                System.out.println(errorLog);
                //here i am re throwing the exception
                throw e;
            }
        }
    }

    // Method to log an event, writing to both in-memory log and the daily log file
    public void logEvent(String event) {
        logBuffer.add(event);  // Add event to in-memory log for UI display
        System.out.println(event);  // Optional: Print to console as well

        // Append the event to the daily log file
        try (FileWriter writer = new FileWriter(DAILY_LOG_FILE_NAME, true)) { // Open in append mode
            writer.write(event + "\n");  // Write each event on a new line
        } catch (IOException e) {
            System.out.println("Failed to write to daily log file: " + e.getMessage());
        }
    }

    // Method to retrieve recent logs for UI display
    public List<String> getLogs() {
        return logBuffer;
    }
}
