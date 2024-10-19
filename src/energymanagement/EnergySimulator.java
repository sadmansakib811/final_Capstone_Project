package energymanagement;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class EnergySimulator {
    private Random random = new Random();

    // Simulate wind turbine
    public int simulateWindTurbine() {
        return random.nextInt(1000);  
    }

    // Simulate solar panel
    public int simulateSolarPanel() {
        return random.nextInt(800);  
    }

    // Simulate energy consumption for lighting
    public int simulateLighting() {
        return random.nextInt(300);  
    }

    // Simulate energy consumption for heating
    public int simulateHeating() {
        return random.nextInt(400); 
    }

    // Simulate energy consumption for H-Bahn
    public int simulateHBahn() {
        return random.nextInt(200);  
    }

    // Simulate data exchange using byte and character streams
    public void simulateDataExchange(String fileName) throws IOException {
        // Use character stream to write log data
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write("Simulating energy data exchange using character streams...\n");
        }

        // Use byte stream to simulate byte-level data exchange
        try (FileOutputStream fos = new FileOutputStream(fileName, true)) {
            String byteData = "Simulating energy data exchange using byte streams...\n";
            fos.write(byteData.getBytes());
        }
    }
}
