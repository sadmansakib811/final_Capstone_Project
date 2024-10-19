package energymanagement;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

public class LogManager {
    private static final String FILENAME_PREFIX = "FH_DORTMUND_energy_management_date_";
    private EnergySimulator energySimulator = new EnergySimulator();  

 
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

           
                energySimulator.simulateDataExchange(fileName);
                System.out.println("Log created for " + date.toString() + " at path: " + Paths.get(fileName).toAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error creating log file for " + date.toString());
                throw e; 
            }
        }
    }
}
