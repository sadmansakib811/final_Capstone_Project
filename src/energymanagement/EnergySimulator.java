package energymanagement;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class EnergySimulator {
    private Random random = new Random();
    private AtomicBoolean simulationRunning = new AtomicBoolean(true);

    // Instance variables for threads
    private Thread windTurbineThread;
    private Thread solarPanelThread;
    private Thread lightingThread;
    private Thread heatingThread;
    private Thread hbahnThread;
    private Thread carChargingThread;

    // Simulate wind turbine-energy maker
    public int simulateWindTurbine() {
        return random.nextInt(1000);
    }

    // Simulate solar panel-energy maker
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

    // Simulate energy consumption for car charging station
    public int simulateCarCharging() {
        return random.nextInt(500);
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

    // Start the multithreaded simulation
    public void startSimulation(Battery battery) {
        // Create threads for energy sources (charging)
        windTurbineThread = new Thread(new EnergySource(battery, "Wind Turbine"));
        solarPanelThread = new Thread(new EnergySource(battery, "Solar Panel"));
        
     // Create threads for energy consumers (discharging)
        lightingThread = new Thread(new EnergyConsumer(battery, "Lighting"));
        heatingThread = new Thread(new EnergyConsumer(battery, "Heating"));
        hbahnThread = new Thread(new EnergyConsumer(battery, "H-Bahn"));
        carChargingThread = new Thread(new EnergyConsumer(battery, "Car Charging Station"));

        

        // Start the threads
        windTurbineThread.start();
        solarPanelThread.start();
        lightingThread.start();
        heatingThread.start();
        hbahnThread.start();
        carChargingThread.start();
        
    }

    // Stop the simulation
    public void stopSimulation() {
        simulationRunning.set(false);
        try {
            windTurbineThread.join();
            solarPanelThread.join();
            lightingThread.join();
            heatingThread.join();
            hbahnThread.join();
            carChargingThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Inner class for energy sources
    class EnergySource implements Runnable {
        private Battery battery;
        private String sourceName;

        public EnergySource(Battery battery, String sourceName) {
            this.battery = battery;
            this.sourceName = sourceName;
        }

        @Override
        public void run() {
            while (simulationRunning.get()) {
                int amount = 0;
                switch (sourceName) {
                    case "Wind Turbine":
                        amount = simulateWindTurbine();
                        break;
                    case "Solar Panel":
                        amount = simulateSolarPanel();
                        break;
                }
                battery.charge(amount, sourceName);
                try {
                    Thread.sleep(1000); // Wait before next charge
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    // Inner class for energy consumers
    class EnergyConsumer implements Runnable {
        private Battery battery;
        private String consumerName;

        public EnergyConsumer(Battery battery, String consumerName) {
            this.battery = battery;
            this.consumerName = consumerName;
        }

        @Override
        public void run() {
            while (simulationRunning.get()) {
                int amount = 0;
                switch (consumerName) {
                    case "Lighting":
                        amount = simulateLighting();
                        break;
                    case "Heating":
                        amount = simulateHeating();
                        break;
                    case "H-Bahn":
                        amount = simulateHBahn();
                        break;
                    case "Car Charging Station":
                        amount = simulateCarCharging();
                        break;
                }
                battery.discharge(amount, consumerName);
                try {
                    Thread.sleep(1000); // Wait before next consumption
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
