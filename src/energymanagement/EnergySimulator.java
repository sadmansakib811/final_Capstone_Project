package energymanagement;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class EnergySimulator {
    private Random random = new Random();
    private AtomicBoolean simulationRunning = new AtomicBoolean(true);
    private static final String PROPERTIES_FILE_PATH = "initial_settings.properties";
    // Declare shared variables as volatile
    private volatile int maxLightingConsumption;
    private volatile int maxHeatingConsumption;
    private volatile int maxHBahnConsumption;
    private volatile int maxCarChargingConsumption;
    private volatile int maxSolarPanelGeneration;
    private volatile int maxWindTurbineGeneration;

    // Variables to track last consumption amounts
    private volatile int lastLightingConsumption = 0;
    private volatile int lastHeatingConsumption = 0;
    private volatile int lastHBahnConsumption = 0;
    private volatile int lastCarChargingConsumption = 0;

    // Variables to track last generation amounts
    private volatile int lastSolarPanelGeneration = 0;
    private volatile int lastWindTurbineGeneration = 0;

    private Thread windTurbineThread;
    private Thread solarPanelThread;
    private Thread lightingThread;
    private Thread heatingThread;
    private Thread hbahnThread;
    private Thread carChargingThread;


    public EnergySimulator() {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(input);

            // Initialize variables with values from properties file or defaults
             String  maxLightingConsumption1 =  properties.getProperty("maxLightingConsumption");
             String maxHeatingConsumption1 = properties.getProperty("maxHeatingConsumption");
             String maxHBahnConsumption1 = properties.getProperty("maxHBahnConsumption");
             String maxCarChargingConsumption1 = properties.getProperty("maxCarChargingConsumption");
             String maxSolarPanelGeneration1 = properties.getProperty("maxSolarPanelGeneration");
             String maxWindTurbineGeneration1 = properties.getProperty("maxWindTurbineGeneration");
             maxLightingConsumption = Integer.parseInt(maxLightingConsumption1);
             maxHeatingConsumption = Integer.parseInt(maxHeatingConsumption1);
             maxHBahnConsumption = Integer.parseInt(maxHBahnConsumption1);
             maxCarChargingConsumption = Integer.parseInt(maxCarChargingConsumption1);
             maxSolarPanelGeneration = Integer.parseInt(maxSolarPanelGeneration1);
             maxWindTurbineGeneration = Integer.parseInt(maxWindTurbineGeneration1);

        } catch (IOException e) {
            System.out.println("Error reading properties file: " + e.getMessage());
            System.out.println("Using default values for simulation parameters.");
            // Assign default values
            maxLightingConsumption = 300;
            maxHeatingConsumption = 400;
            maxHBahnConsumption = 200;
            maxCarChargingConsumption = 500;
            maxSolarPanelGeneration = 800;
            maxWindTurbineGeneration = 1000;
        }
    }
   
    // Setter methods for adjusting parameters
    public void setMaxLightingConsumption(int max) { this.maxLightingConsumption = max; }
    public void setMaxHeatingConsumption(int max) { this.maxHeatingConsumption = max; }
    public void setMaxHBahnConsumption(int max) { this.maxHBahnConsumption = max; }
    public void setMaxCarChargingConsumption(int max) { this.maxCarChargingConsumption = max; }
    public void setSolarPanelGenerationRate(int max) { this.maxSolarPanelGeneration = max; }
    public void setWindTurbineGenerationRate(int max) { this.maxWindTurbineGeneration = max; }

    // **Added Getter methods for max values**
    public int getMaxLightingConsumption() { return maxLightingConsumption; }
    public int getMaxHeatingConsumption() { return maxHeatingConsumption; }
    public int getMaxHBahnConsumption() { return maxHBahnConsumption; }
    public int getMaxCarChargingConsumption() { return maxCarChargingConsumption; }
    public int getMaxSolarPanelGeneration() { return maxSolarPanelGeneration; }
    public int getMaxWindTurbineGeneration() { return maxWindTurbineGeneration; }

    // Getter methods for consumption
    public int getLastLightingConsumption() { return lastLightingConsumption; }
    public int getLastHeatingConsumption() { return lastHeatingConsumption; }
    public int getLastHBahnConsumption() { return lastHBahnConsumption; }
    public int getLastCarChargingConsumption() { return lastCarChargingConsumption; }

    // Getter methods for generation
    public int getLastSolarPanelGeneration() { return lastSolarPanelGeneration; }
    public int getLastWindTurbineGeneration() { return lastWindTurbineGeneration; }

    // Simulation methods
    public int simulateWindTurbine() { return random.nextInt(maxWindTurbineGeneration / 2) + 1; }
    public int simulateSolarPanel() { return random.nextInt(maxSolarPanelGeneration / 2) + 1; }
    public int simulateLighting() { return random.nextInt(maxLightingConsumption / 2) + 1; }
    public int simulateHeating() { return random.nextInt(maxHeatingConsumption / 2) + 1; }
    public int simulateHBahn() { return random.nextInt(maxHBahnConsumption / 2) + 1; }
    public int simulateCarCharging() { return random.nextInt(maxCarChargingConsumption / 2) + 1; }

 
    public void startSimulation(Battery battery) {
        simulationRunning.set(true);

        windTurbineThread = new Thread(new EnergySource(battery, "Wind Turbine"));
        solarPanelThread = new Thread(new EnergySource(battery, "Solar Panel"));

        lightingThread = new Thread(new EnergyConsumer(battery, "Lighting"));
        heatingThread = new Thread(new EnergyConsumer(battery, "Heating"));
        hbahnThread = new Thread(new EnergyConsumer(battery, "H-Bahn"));
        carChargingThread = new Thread(new EnergyConsumer(battery, "Car Charging Station"));

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

    // Inner class for energy sources by Sadman
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
                if ("Wind Turbine".equals(sourceName)) {
                    amount = simulateWindTurbine();
                    lastWindTurbineGeneration = amount;
                } else if ("Solar Panel".equals(sourceName)) {
                    amount = simulateSolarPanel();
                    lastSolarPanelGeneration = amount;
                }

                battery.charge(amount, sourceName);
                // Removed console output

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }




    // Inner class for energy consumers By Farhana
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
                        lastLightingConsumption = amount;
                        break;
                    case "Heating":
                        amount = simulateHeating();
                        lastHeatingConsumption = amount;
                        break;
                    case "H-Bahn":
                        amount = simulateHBahn();
                        lastHBahnConsumption = amount;
                        break;
                    case "Car Charging Station":
                        amount = simulateCarCharging();
                        lastCarChargingConsumption = amount;
                        break;
                }
                battery.discharge(amount, consumerName);
                // Removed console output

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
