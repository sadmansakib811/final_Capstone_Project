package energymanagement;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

public class LogManager {
    private static final String FILENAME_PREFIX = "FH_DORTMUND_energy_management_date_";
    private EnergySimulator energySimulator = new EnergySimulator();  // Instantiate simulator

    // Generate log files for 5 days (today and the last 4 days)
    public void generateLogFiles() throws IOException {
        for (int i = 0; i < 5; i++) {
            LocalDate date = LocalDate.now().minusDays(i);  // Get today's date and previous 4 days
            String fileName = FILENAME_PREFIX + date.toString() + ".log";  // File format: FH_DORTMUND_energy_management_date_YYYY-MM-DD.log

            try (FileWriter writer = new FileWriter(fileName)) {
                // Simulate energy generation using EnergySimulator
                int windTurbine = energySimulator.simulateWindTurbine();
                int solarPanel = energySimulator.simulateSolarPanel();
                int totalGenerated = windTurbine + solarPanel;

                // Simulate energy consumption using EnergySimulator
                int lighting = energySimulator.simulateLighting();
                int heating = energySimulator.simulateHeating();
                int hbahn = energySimulator.simulateHBahn();
                int totalConsumed = lighting + heating + hbahn;

                // Write the simulated data to the log file
                writer.write("Energy Generation:\n");
                writer.write("Wind Turbine: " + windTurbine + " kWh\n");
                writer.write("Solar Panel: " + solarPanel + " kWh\n");
                writer.write("Total Generated: " + totalGenerated + " kWh\n\n");

                writer.write("Energy Consumption:\n");
                writer.write("Lighting: " + lighting + " kWh\n");
                writer.write("Heating: " + heating + " kWh\n");
                writer.write("H-Bahn: " + hbahn + " kWh\n");
                writer.write("Total Consumed: " + totalConsumed + " kWh\n\n");

                int energyBalance = totalGenerated - totalConsumed;  // Calculate energy balance
                writer.write("Energy Balance: " + energyBalance + " kWh\n");

                // Simulate data exchange
                energySimulator.simulateDataExchange(fileName);
                System.out.println("Log created for " + date.toString() + " at path: " + Paths.get(fileName).toAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error creating log file for " + date.toString());
                throw e; // Re-throwing Exception
            }
        }
    }
}
